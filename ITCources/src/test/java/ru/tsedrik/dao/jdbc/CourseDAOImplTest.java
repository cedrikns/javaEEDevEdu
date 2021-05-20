package ru.tsedrik.dao.jdbc;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.MySQLException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseDAOImplTest {

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Transaction transaction;

    @Mock
    Query query;

    CourseDAOImpl courseDAO;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(sessionFactory.getCurrentSession()).thenReturn(session);
        Mockito.lenient().when(session.beginTransaction()).thenReturn(transaction);

        courseDAO = new CourseDAOImpl(sessionFactory);
    }

    @Test
    void create() {

        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.save(course)).thenReturn(course);

        Course createdCourse = courseDAO.create(course);

        assertNotNull(createdCourse);
        assertEquals(course, createdCourse);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).save(course);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();

    }

    @Test
    void createWithException() {

        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.save(course)).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> courseDAO.create(course));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).save(course);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction, Mockito.atMostOnce()).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void getById() {

        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, id)).thenReturn(course);

        Course foundedCourse = courseDAO.getById(id);

        assertNotNull(foundedCourse);
        assertEquals(course, foundedCourse);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, id);
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getByIdWithException() {

        UUID id = UUID.randomUUID();
        Mockito.when(session.get(Course.class, id)).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> courseDAO.getById(id));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, id);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void getAll() {
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(session.createQuery("from Course")).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        Collection<Course> foundedCourses = courseDAO.getAll();

        assertNotNull(foundedCourses);
        assertIterableEquals(list, foundedCourses);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery("from Course");
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getAllWithException() {

        Mockito.when(session.createQuery("from Course")).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> courseDAO.getAll());
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery("from Course");
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void update() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, course.getId())).thenReturn(course);

        Course updatedCourse = courseDAO.update(course);

        assertNotNull(updatedCourse);
        assertEquals(course, updatedCourse);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(session).update(course);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();
    }

    @Test
    void updateNotExistingCourse() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, course.getId())).thenReturn(null);

        assertThrows(MySQLException.class, () -> courseDAO.update(course));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(session, Mockito.never()).update(course);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void updateWithException() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, course.getId())).thenReturn(course);
        Mockito.doThrow(new HibernateException("Exception")).when(session).update(course);

        assertThrows(MySQLException.class, () -> courseDAO.update(course));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(session).update(course);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void delete() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        CourseDAOImpl mockedCourseDao = Mockito.mock(CourseDAOImpl.class);
        Mockito.when(mockedCourseDao.delete(course)).thenCallRealMethod();
        Mockito.when(mockedCourseDao.deleteById(course.getId())).thenReturn(true);
        assertTrue(mockedCourseDao.delete(course));
        Mockito.verify(mockedCourseDao).deleteById(course.getId());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, id)).thenReturn(course);

        boolean isDeleted = courseDAO.deleteById(id);

        assertEquals(true, isDeleted);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(session).delete(course);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();

    }

    @Test
    void deleteByIdWithException() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(session.get(Course.class, id)).thenReturn(course);
        Mockito.doThrow(new HibernateException("Exception")).when(session).delete(course);

        assertThrows(MySQLException.class, () -> courseDAO.deleteById(id));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(session).delete(course);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();

    }

    @Test
    void getByCourseType() {

        CourseType courseType = CourseType.CI;
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), courseType, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        Collection<Course> foundedCourses = courseDAO.getByCourseType(courseType);

        assertNotNull(foundedCourses);
        assertIterableEquals(list, foundedCourses);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery(Mockito.anyString());
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getByCourseTypeWithException() {

        CourseType courseType = CourseType.CI;

        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> courseDAO.getByCourseType(courseType));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery(Mockito.anyString());
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void enroll() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Student enrolledStudent = new Student(UUID.randomUUID(), "enroll@mail.ru");

        Set<Student> students = new HashSet<>();
        students.add(student);
        course.setStudents(students);

        Mockito.when(session.get(Course.class, course.getId())).thenReturn(course);

        assertTrue(courseDAO.enroll(course.getId(), enrolledStudent));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).update(course);
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();

    }

    @Test
    void enrollWithAlreadyEnrolledStudent() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Student enrolledStudent = new Student(UUID.randomUUID(), "enroll@mail.ru");

        Set<Student> students = new HashSet<>();
        students.add(student);
        students.add(enrolledStudent);
        course.setStudents(students);

        Mockito.when(session.get(Course.class, course.getId())).thenReturn(course);

        assertFalse(courseDAO.enroll(course.getId(), enrolledStudent));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session, Mockito.never()).update(course);
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();

    }

    @Test
    void enrollWithException() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Student enrolledStudent = new Student(UUID.randomUUID(), "enroll@mail.ru");

        Mockito.when(session.get(Course.class, course.getId())).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> courseDAO.enroll(course.getId(), enrolledStudent));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session, Mockito.never()).update(course);
        Mockito.verify(session).get(Course.class, course.getId());
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session).close();

    }
}