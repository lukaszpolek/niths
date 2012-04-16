package no.niths.application.rest.interfaces;

import java.util.List;

import no.niths.domain.Course;
import no.niths.domain.Student;

/**
 * Controller for student
 *
 */
public interface StudentController extends GenericRESTController<Student> {

	/**
	 * HOW TO USE SECURITY ANNOTATIONS:
	 * 
	 * One role allowed:
	 * @PreAuthorize("hasRole('ROLE_ADMIN')")
	 * Multiple roles allowed:
	 * @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEST')")
	 * 
	 */
//  EXAMPLE OF HOW TO USE:	
//	@Override
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEST')")
//	public ArrayList<Student> getAll(Student domain);
	
	/**
	 * Returns a list of all students in a given course
	 * @param course the course to get students from
	 * @return list of students
	 */
	List<Student> getStudentsWithNamedCourse(Course course);

    /**
     * Adds a course too a student
     *
     * @param studentId id of the student
     * @param courseId id of the course
     */
    void addCourse(Long studentId, Long courseId);

    /**
     * Removes a course from a student
     *
     * @param studentId id of the student
     * @param courseId if of the course
     */
    void removeCourse(Long studentId, Long courseId);

    /**
     * Adds a committee too a student
     *
     * @param studentId id of the student
     * @param committeeId id of the committee
     */
    void addCommittee(Long studentId, Long committeeId);

    /**
     * Removes a committee from a student
     *
     * @param studentId id of the student
     * @param committeeId if of the committee
     */
    void removeCommittee(Long studentId, Long committeeId);

    /**
     * Adds a feed too a student
     *
     * @param studentId id of the student
     * @param feedId id of the feed
     */
    void addFeed(Long studentId, Long feedId);

    /**
     * Removes a feed from a student
     *
     * @param studentId id of the student
     * @param feedId if of the feed
     */
    void removeFeed(Long studentId, Long feedId);

    /**
     * Adds a loan too a student
     *
     * @param studentId id of the student
     * @param loanId id of the loan
     */
    void addLoan(Long studentId, Long loanId);

    /**
     * Removes a loan from a student
     *
     * @param studentId id of the student
     * @param loanId if of the loan
     */
    void removeLoan(Long studentId, Long loanId);
    
    /**
     * 
     * @param studentId
     * @param roleId
     */
    void addRole(Long studentId, Long roleId);
    
    /**
     * 
     * @param studentId
     * @param roleId
     */
    void removeRole(Long studentId, Long roleId);
    
    /**
     * 
     * @param studId
     */
    void removeAllRolesFromStudent(Long studId);
}
