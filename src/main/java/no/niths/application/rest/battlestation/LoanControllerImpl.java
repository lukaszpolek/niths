package no.niths.application.rest.battlestation;

import java.util.List;

import no.niths.application.rest.AbstractRESTControllerImpl;
import no.niths.application.rest.RESTConstants;
import no.niths.application.rest.battlestation.interfaces.LoanController;
import no.niths.application.rest.helper.TimeDTO;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.application.rest.lists.battlestation.LoanList;
import no.niths.common.constants.DomainConstantNames;
import no.niths.common.helpers.ValidationHelper;
import no.niths.domain.battlestation.Loan;
import no.niths.services.battlestation.interfaces.LoanService;
import no.niths.services.interfaces.GenericService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller for loan
 * has the basic CRUD methods and
 * methods too add and remove console
 * and student
 * in addition too method for getLoansBetweenDates,
 *
 * For the URL too get Loan add /loans
 * after the {@value no.niths.common.constants.MiscConstants#NITHS_BASE_DOMAIN}
 */
@Controller
@RequestMapping(DomainConstantNames.LOANS)
public class LoanControllerImpl extends AbstractRESTControllerImpl<Loan> implements LoanController{

    private static final Logger logger = LoggerFactory
            .getLogger(LoanControllerImpl.class);

    @Autowired
    private LoanService loanService;

    private LoanList loanList = new LoanList();

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/console/{consoleId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Added")
    public void addConsole(@PathVariable Long loanId, @PathVariable Long consoleId) {
        loanService.addConsole(loanId, consoleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/console/{consoleId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Removed")
    public void removeConsole(@PathVariable Long loanId, @PathVariable Long consoleId) {
        loanService.removeConsole(loanId, consoleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/student/{studentId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK, reason = "Student Added")
    public void addStudent(@PathVariable Long loanId, @PathVariable Long studentId) {
        loanService.addStudent(loanId, studentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "{loanId}/student", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK, reason = "Student Removed")
    public void removeStudent(@PathVariable Long loanId) {
        loanService.removeStudent(loanId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "dates", method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
    @ResponseBody
    public List<Loan> getLoansBetweenDates(TimeDTO timeDTO) {
        logger.debug(timeDTO +"");
        ValidationHelper.isObjectNull(timeDTO.getStartTime());

        if(timeDTO.getEndTime() != null){
            renewList(loanService.getLoansBetweenDates(timeDTO.getStartTimeCal(), timeDTO.getEndTimeCal()));
        }else{
            renewList(loanService.getLoansBetweenDates(timeDTO.getStartTimeCal(), null));
        }
        return loanList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericService<Loan> getService() {
        return loanService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListAdapter<Loan> getList() {
        return loanList;
    }
}
