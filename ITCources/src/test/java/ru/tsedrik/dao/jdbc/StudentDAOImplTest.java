package ru.tsedrik.dao.jdbc;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.MySQLException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentDAOImplTest {

    @Mock
    SessionFactory sessionFactory;

    @Mock
    Session session;

    @Mock
    Transaction transaction;

    @Mock
    Query query;

    StudentDAOImpl studentDAO;

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(sessionFactory.getCurrentSession()).thenReturn(session);
        Mockito.lenient().when(session.beginTransaction()).thenReturn(transaction);

        studentDAO = new StudentDAOImpl(sessionFactory);
    }

    @Test
    void create() {

        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Mockito.when(session.save(student)).thenReturn(student);

        Student createdStudent = studentDAO.create(student);

        assertNotNull(createdStudent);
        assertEquals(student, createdStudent);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).save(student);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();

    }

    @Test
    void createWithException() {

        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Mockito.when(session.save(student)).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> studentDAO.create(student));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).save(student);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction, Mockito.atMostOnce()).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void getById() {

        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(session.get(Student.class, id)).thenReturn(student);

        Student foundedStudent = studentDAO.getById(id);

        assertNotNull(foundedStudent);
        assertEquals(student, foundedStudent);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, id);
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getByIdWithException() {

        UUID id = UUID.randomUUID();
        Mockito.when(session.get(Student.class, id)).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> studentDAO.getById(id));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, id);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void getAll() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Student(UUID.randomUUID(), "test" + i + "@mail.ru"));
        }

        Mockito.when(session.createQuery("from Student")).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        Collection<Student> foundedStudents = studentDAO.getAll();

        assertNotNull(foundedStudents);
        assertIterableEquals(list, foundedStudents);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery("from Student");
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getAllWithException() {

        Mockito.when(session.createQuery("from Student")).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> studentDAO.getAll());
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery("from Student");
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void update() {
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Mockito.when(session.get(Student.class, student.getId())).thenReturn(student);

        Student updatedStudent = studentDAO.update(student);

        assertNotNull(updatedStudent);
        assertEquals(student, updatedStudent);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, student.getId());
        Mockito.verify(session).update(student);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();
    }

    @Test
    void updateNotExistingCourse() {
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Mockito.when(session.get(Student.class, student.getId())).thenReturn(null);

        assertThrows(MySQLException.class, () -> studentDAO.update(student));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, student.getId());
        Mockito.verify(session, Mockito.never()).update(student);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void updateWithException() {
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");
        Mockito.when(session.get(Student.class, student.getId())).thenReturn(student);
        Mockito.doThrow(new HibernateException("Exception")).when(session).update(student);

        assertThrows(MySQLException.class, () -> studentDAO.update(student));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, student.getId());
        Mockito.verify(session).update(student);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void delete() {
        Student student = new Student(UUID.randomUUID(), "test@mail.ru");

        StudentDAOImpl mockedStudentDao = Mockito.mock(StudentDAOImpl.class);
        Mockito.when(mockedStudentDao.delete(student)).thenCallRealMethod();
        Mockito.when(mockedStudentDao.deleteById(student.getId())).thenReturn(true);

        assertTrue(mockedStudentDao.delete(student));
        Mockito.verify(mockedStudentDao).deleteById(student.getId());
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(session.get(Student.class, id)).thenReturn(student);

        boolean isDeleted = studentDAO.deleteById(id);

        assertEquals(true, isDeleted);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, student.getId());
        Mockito.verify(session).delete(student);
        Mockito.verify(transaction).commit();
        Mockito.verify(transaction, Mockito.never()).rollback();
        Mockito.verify(session).close();

    }

    @Test
    void deleteByIdWithException() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(session.get(Student.class, id)).thenReturn(student);
        Mockito.doThrow(new HibernateException("Exception")).when(session).delete(student);

        assertThrows(MySQLException.class, () -> studentDAO.deleteById(id));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).get(Student.class, student.getId());
        Mockito.verify(session).delete(student);
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(transaction).rollback();
        Mockito.verify(session, Mockito.atMostOnce()).close();

    }

    @Test
    void getStudentByEmail() {
        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);
        List<Student> list = new ArrayList<>();
        list.add(student);

        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(list);

        Student foundedStudent = studentDAO.getStudentByEmail(email);

        assertNotNull(foundedStudent);
        assertEquals(student, foundedStudent);
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery(Mockito.anyString());
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }

    @Test
    void getStudentByEmailWithException() {

        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);
        List<Student> list = new ArrayList<>();
        list.add(student);

        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.getResultList()).thenThrow(new HibernateException("Exception"));

        assertThrows(MySQLException.class, () -> studentDAO.getStudentByEmail(email));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery(Mockito.anyString());
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction, Mockito.never()).commit();
        Mockito.verify(session, Mockito.atMostOnce()).close();
    }

    @Test
    void getStudentByEmailWithZeroResult() {
        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);
        List<Student> list = new ArrayList<>();
        list.add(student);

        Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(Collections.EMPTY_LIST);

        assertNull(studentDAO.getStudentByEmail(email));
        Mockito.verify(sessionFactory).getCurrentSession();
        Mockito.verify(session).beginTransaction();
        Mockito.verify(session).createQuery(Mockito.anyString());
        Mockito.verify(query).getResultList();
        Mockito.verify(transaction).commit();
        Mockito.verify(session).close();
    }
}