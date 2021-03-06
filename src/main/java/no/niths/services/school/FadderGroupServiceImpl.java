package no.niths.services.school;

import java.util.List;

import no.niths.application.rest.helper.Status;
import no.niths.common.helpers.MessageProvider;
import no.niths.common.helpers.ValidationHelper;
import no.niths.domain.school.FadderGroup;
import no.niths.domain.school.Role;
import no.niths.domain.school.Student;
import no.niths.infrastructure.interfaces.GenericRepository;
import no.niths.infrastructure.school.interfaces.FadderGroupRepository;
import no.niths.infrastructure.school.interfaces.RoleRepository;
import no.niths.infrastructure.school.interfaces.StudentRepository;
import no.niths.services.AbstractGenericService;
import no.niths.services.school.interfaces.FadderGroupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Service Class for FadderGroup
 *
 * <p>
 * Inherits the basic CRUD actions and has methods
 * for addLeader, removeLeader,
 * removeAllLeaders, addChild,
 * removeChild, removeChildren,
 * addChildren, removeAllChildren
 * and getStudentsNotInAGroup
 * </p>
 */
@Service
public class FadderGroupServiceImpl extends AbstractGenericService<FadderGroup>
        implements FadderGroupService {

    private Logger logger = LoggerFactory
            .getLogger(FadderGroupServiceImpl.class);

    @Autowired
    private FadderGroupRepository fadderGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public GenericRepository<FadderGroup> getRepository() {
        return fadderGroupRepository;
    }

    @Override
    public List<FadderGroup> getAll(FadderGroup domain) {
        List<FadderGroup> list = fadderGroupRepository.getAll(domain);
        for (FadderGroup fg : list) {
            fg.getLeaders().size();
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLeader(Long groupId, Long studentId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);
        checkIfObjectIsInCollection(group.getLeaders(), studentId,
                Student.class);

        Student leader = studentRepository.getById(studentId);
        ValidationHelper.isObjectNull(leader, Student.class);

        // add role FADDER_LEADER To student;
        Role r = new Role();
        r.setRoleName("ROLE_FADDER_LEADER");
        List<Role> roles = roleRepo.getAll(r);
        if (roles.size() > 0) {
            if(!leader.getRoles().contains(roles.get(0))){
                leader.getRoles().add(roles.get(0));
            }
        }
        
        group.getLeaders().add(leader);

        logger.debug(MessageProvider.buildStatusMsg(Student.class,
                Status.UPDATED));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLeader(Long groupId, Long studentId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);
        
        Student student = studentRepository.getById(studentId);

        checkIfIsRemoved(group.getLeaders().remove(student),
                Student.class);
        
        // remove role
        for(Role r:student.getRoles()){
            if(r.getRoleName().equals("ROLE_FADDER_LEADER")){
                student.getRoles().remove(r);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChild(Long groupId, Long studentId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);
        checkIfObjectIsInCollection(group.getFadderChildren(), studentId,
                Student.class);

        Student child = studentRepository.getById(studentId);
        ValidationHelper.isObjectNull(child, Student.class);

        group.getFadderChildren().add(child);
        logger.debug(MessageProvider.buildStatusMsg(Student.class,
                Status.UPDATED));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChildren(Long groupId, Long[] studentIds) {
        for (Long studentId : studentIds) {
            addChild(groupId, studentId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeChild(Long groupId, Long studentId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);
        checkIfIsRemoved(
                group.getFadderChildren().remove(new Student(studentId)),
                Student.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeChildren(Long groupId, Long[] studentIds) {
        for (Long studentId : studentIds) {
            removeChild(groupId, studentId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllChildren(Long groupId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);

        boolean isRemoved = false;

        if (group.getFadderChildren() != null) {
            group.getFadderChildren().clear();
            isRemoved = true;
        }

        checkIfIsRemoved(isRemoved, Student.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllLeaders(Long groupId) {
        FadderGroup group = validate(fadderGroupRepository.getById(groupId),
                FadderGroup.class);

        boolean isRemoved = false;

        if (group.getLeaders() != null) {
            group.getLeaders().clear();
            isRemoved = true;
        }

        checkIfIsRemoved(isRemoved, Student.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Student> getStudentsNotInAGroup() {
        return fadderGroupRepository.getStudentsNotInAGroup();
    }

}
