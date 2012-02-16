package no.niths.infrastructure;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.Course;
import no.niths.domain.Topic;
import no.niths.infrastructure.interfaces.CoursesRepository;
import no.niths.infrastructure.interfaces.TopicsRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= { TestAppConfig.class, HibernateConfig.class})
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager") 
public class CourseRepositoryTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CourseRepositoryTest.class);
	
	@Autowired
	private CoursesRepository repo;
	
	@Autowired
	private TopicsRepository topicRepo;
	
	@Test
	public void testCreateATimeSchedule(){
		Course c = new Course("Programmering", "Java and the like");
		c.setGrade(1);
		c.setTerm("Fall");
		
		Topic t1 = new Topic("Java innføring", "PG111", "Lær java", "10:00", "12:00");
		t1.setWeekday("Monday");
		topicRepo.create(t1);
		Topic t2 = new Topic("Java viderekomne", "PG211", "Lær java", "12:00", "14:00");
		t2.setWeekday("Monday");
		topicRepo.create(t2);
		
		c.getTopics().add(t1);
		c.getTopics().add(t2);
		repo.create(c);
		
		assertEquals(2, repo.getById(c.getId()).getTopics().size());
	}
	
	@Test
	public void testGetCourseWithTopics_whenParametersAreGradeTermName(){
		Course c = new Course("Programmering", "Java and the like");
		c.setGrade(1);
		c.setTerm("Fall");
		
		Topic t1 = new Topic("Java innføring", "PG111", "Lær java", "10:00", "12:00");
		t1.setWeekday("Monday");
		topicRepo.create(t1);
		Topic t2 = new Topic("Java viderekomne", "PG211", "Lær java", "12:00", "14:00");
		t2.setWeekday("Monday");
		topicRepo.create(t2);
		
		c.getTopics().add(t1);
		c.getTopics().add(t2);
		repo.create(c);
		
		assertEquals(c, repo.getCourse("Programmering", 1, "Fall"));
		assertEquals(null, repo.getCourse("Programmering", 1, "XXX"));
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testWhenAddCourseWithSameName_gradeCanNotBeEqual(){
		int size = repo.getAll(null).size();
		Course c1 = new Course("XXX", "XXX");
		c1.setGrade(1);
		repo.create(c1);
		assertEquals(size + 1, repo.getAll(null).size());
		//Course with same name and grade
		//Should throw ConstraintViolationEx
		Course c2 = new Course("XXX", "XXX");
		c2.setGrade(1);
		repo.create(c2);
	}
	
	@Test
	public void testWhenAddCourseWithSameNameButDifferentGrade(){
		int size = repo.getAll(null).size();
		Course c1 = new Course("XXX", "XXX");
		c1.setGrade(1);
		repo.create(c1);
		assertEquals(size + 1, repo.getAll(null).size());
		//Course with same name and grade
		//Should throw ConstraintViolationEx
		Course c2 = new Course("XXX", "XXX");
		c2.setGrade(2);
		repo.create(c2);
		assertEquals(size + 2, repo.getAll(null).size());
	}
	
	@Test
	public void whenAddedTopics_CourseShouldHaveThem() {
		int numTopics = topicRepo.getAll(null).size();
		int numCourses = repo.getAll(null).size();

		Course c1 = new Course("Programmering", "Java, c++");
		Topic t1 = new Topic();
		t1.setTopicCode("PG111");
		c1.getTopics().add(t1);
		repo.create(c1);
		
		
		assertEquals(numCourses + 1, repo.getAll(null).size());
		assertEquals(numTopics + 1, topicRepo.getAll(null).size());
		
		Course res = repo.getById(c1.getId());
		int numOfTopics = res.getTopics().size();
		if(numOfTopics > 0){
			res.getTopics().remove(0);
			repo.update(res);
			
			assertEquals(numOfTopics - 1, repo.getById(res.getId()).getTopics().size() );
			assertEquals(numTopics + 1, topicRepo.getAll(null).size());
		}
		
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void whenInsertNull_persistenceShouldFail() {
		repo.create(null);
	}
	
	@Test
	public void whenInsertCourse_CourseShouldBePersisted(){
		int size = repo.getAll(null).size();
		
		Course c = new Course("Name", "Desc");
		repo.create(c);
		
		assertEquals(size + 1, repo.getAll(null).size());
	}
	
	
	
	@Test
	public void getCoursesByName_shouldReturnListWithCourses(){
		int  size = repo.getAll(null).size();
		Course c1 = new Course("One", "oneDesc");
		Course c2 = new Course("Two", "oneDesc");// equal desc
		Course c3 = new Course("Three", "threeDesc");
		Course c4 = new Course("Four", "fourDesc");
		repo.create(c1);
		repo.create(c2);
		repo.create(c3);
		repo.create(c4);
		assertEquals(size + 4, repo.getAll(null).size());
		
		Course mockCourse= new Course();
		mockCourse.setName("One");
		
		assertEquals(1, repo.getAll(mockCourse).size());
		mockCourse.setName("Two");
		assertEquals(1, repo.getAll(mockCourse).size());
		mockCourse.setName("*AASDASD");
		assertEquals(0, repo.getAll(mockCourse).size());
	}
	
	@Test
	public void getCoursesByAttributes_shouldReturnListWithCourses(){
		int  size = repo.getAll(null).size();
		Course c1 = new Course("One", "oneDesc");
		Course c2 = new Course("Two", "oneDesc");// equal desc
		Course c3 = new Course("Three", "threeDesc");
		Course c4 = new Course("Four", "fourDesc");
		repo.create(c1);
		repo.create(c2);
		repo.create(c3);
		repo.create(c4);
		
		assertEquals(size + 4, repo.getAll(null).size());
		
		Course toFind = new Course();
		toFind.setName("One");
		
		List<Course> results = repo.getAll(toFind);
		assertEquals(1, results.size());
		
		toFind = new Course();
		toFind.setDescription("oneDesc");
		results = repo.getAll(toFind);
		assertEquals(2, results.size());

		toFind = new Course();
		toFind.setName("x");
		results = repo.getAll(toFind);
		assertEquals(0, results.size());
	}
	
	@Test
	public void testCRUD() {
		int size = repo.getAll(null).size();
		Course c = new Course("Name222", "Desc");
		repo.create(c);
		assertEquals(size + 1, repo.getAll(null).size());
		
		Course c1 = repo.getById(c.getId());
		assertEquals(c1.getId(), c.getId());
		long id = c1.getId();
		
		Course testCourse= new Course();
		testCourse.setName("name222");
		assertEquals(c1, repo.getAll(testCourse).get(0));
		
		boolean isDeleted = repo.delete(id);
		assertTrue(isDeleted);
		assertEquals(size, repo.getAll(null).size());
		
		assertEquals(0,repo.getAll(testCourse).size());
		
	}
	
	
	@Test
	public void testRead(){
		
		Course courseProg = new Course("Programmering", "programmering er kult");

		// adding course
		courseProg.setId(repo.create(courseProg));
	
		assertNotSame(0, repo.getAll(null).size());
		
		// deleting courses
		repo.delete(courseProg.getId());
	}
}
