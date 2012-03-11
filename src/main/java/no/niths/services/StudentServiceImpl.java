package no.niths.services;

import java.util.List;

import no.niths.domain.Student;
import no.niths.infrastructure.interfaces.StudentRepository;
import no.niths.services.interfaces.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository repo;

	public Long create(Student student) {
		return repo.create(student);
	}

	/**
	 * Finds and returns a student with a given id. Returns the student with
	 * courses and committees
	 * 
	 * @param id
	 *            ID of student to find
	 * @return the student
	 */
	public Student getById(long id) {
		Student s = repo.getById(id);
		if (s != null) {
			s.getCommittees().size();
			s.getCourses().size();
			s.getRoles().size();
			//s.getFadderGroup().size();
		}
		return s;
	}

	public List<Student> getAll(Student s) {
		return repo.getAll(s);
	}

	public void update(Student student) {
		repo.update(student);
	}

	public boolean delete(long id) {
		return repo.delete(id);
	}

	public List<Student> getStudentsWithNamedCourse(String name) {

		List<Student> temp = repo.getStudentsWithNamedCourse(name);

		return temp;
	}

	@Override
	public void hibernateDelete(long id) {
		repo.hibernateDelete(id);
	}

	@Override
	public List<Student> getStudentsAndRoles(Student s) {
		List <Student> list = repo.getAll(s);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).getRoles().size();
		}
		return list;
	}

	@Override
	public List<Student> getStudentByColumn(String column, String criteria) {
		List <Student> list = repo.getStudentByColumn(column, criteria);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).getRoles().size();
		}
		
		return list;
	}
}
