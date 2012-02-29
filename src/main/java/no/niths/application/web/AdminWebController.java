package no.niths.application.web;

import java.util.List;

import no.niths.application.rest.CommitteeControllerImpl;
import no.niths.domain.Student;
import no.niths.services.interfaces.StudentService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminWebController {

	
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(CommitteeControllerImpl.class);
	
	@Autowired
	private StudentService serice;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllStudents(
			@RequestParam(value = "query", required = false, defaultValue="") String query) {
		ModelAndView view = new ModelAndView("index");
		List<Student> students = null;

		logger.debug(query+ "  _--------------------------------------------------------");

		if (!(query.equals(""))) {
			Student s = new Student();
			s.setFirstName(query);
			students = serice.getAll(s);
		} else{
			students = serice.getAll(null);
		}

		view.addObject("studentList", students);

		return view;
	}

}
