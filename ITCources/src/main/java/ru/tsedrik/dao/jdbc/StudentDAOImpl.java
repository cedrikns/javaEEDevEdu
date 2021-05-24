package ru.tsedrik.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.MySQLException;

import java.util.*;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private static final Logger log = LogManager.getLogger(StudentDAOImpl.class.getName());

    private SessionFactory sessionFactory;

    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Student create(Student student) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            log.info("Student with id = " + student.getId() + " was added.");
            return student;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при создании студента: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Student getById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            transaction.commit();
            return student;
        } catch (Exception e){
            throw new MySQLException("Ошибка при получении студента: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Collection<Student> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<Student> students = session.createQuery("from Student").getResultList();
            transaction.commit();
            log.info("Was founded " + students.size() + " students.");
            return students;
        } catch (Exception e){
            throw new MySQLException("Ошибка при получении списка студентов: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Student update(Student student) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Student updatedStudent = session.get(Student.class, student.getId());
            if (updatedStudent == null){
                throw new IllegalArgumentException("Wasn't found needed student.");
            }
            updatedStudent.setFirstName(student.getFirstName());
            updatedStudent.setLastName(student.getLastName());

            session.update(updatedStudent);
            transaction.commit();

            log.info("Student with id = " + student.getId() + " was updated.");
            return updatedStudent;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при обновлении студента: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Student student) {
        return deleteById(student.getId());
    }

    @Override
    public boolean deleteById(UUID id) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            session.delete(student);
            transaction.commit();
            log.info("Student with id = " + student.getId() + " was deleted.");
            return true;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при удалении студента: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<Student> students = session.createQuery("from Student as s where s.email = '" + email + "'").getResultList();
            transaction.commit();
            if (students.size() > 0) {
                return students.get(0);
            } else {
                log.info("There wan't found student with email = " + email);
                return null;
            }
        } catch (Exception e){
            throw new MySQLException("Ошибка по получению студента: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
