package ru.tsedrik.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.MySQLException;

import java.util.*;

public class CourseDAOImpl implements CourseDAO {

    private static final Logger log = LogManager.getLogger(CourseDAOImpl.class.getName());

    private SessionFactory sessionFactory;

    public CourseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Course create(Course course) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
            log.info("Course with id = " + course.getId() + " was added.");
            return course;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при создании курса: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Course getById(UUID id) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            transaction.commit();
            return course;
        } catch (Exception e){
            throw new MySQLException("Ошибка при получении курса: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Collection<Course> getAll() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<Course> courses = session.createQuery("from Course").getResultList();
            transaction.commit();
            log.info("Was founded " + courses.size() + " courses.");
            return courses;
        } catch (Exception e){
            throw new MySQLException("Ошибка при получении списка курсов: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Course update(Course course) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Course updatedCourse = session.get(Course.class, course.getId());
            if (updatedCourse == null){
                throw new IllegalArgumentException("Wasn't found needed course.");
            }
            updatedCourse.setBeginDate(course.getBeginDate());
            updatedCourse.setEndDate(course.getEndDate());
            updatedCourse.setMaxStudentsCount(course.getMaxStudentsCount());
            updatedCourse.setCourseStatus(course.getCourseStatus());

            session.update(updatedCourse);
            transaction.commit();

            log.info("Course with id = " + course.getId() + " was updated.");
            return updatedCourse;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при получении курса: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Course course) {
        return deleteById(course.getId());
    }

    @Override
    public boolean deleteById(UUID id) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            session.delete(course);
            transaction.commit();
            log.info("Course with id = " + course.getId() + " was deleted.");
            return true;
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка при удалении курса: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Collection<Course> getByCourseType(CourseType type) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List<Course> courses = session.createQuery("from Course as c where c.courseType = '" + type + "'").getResultList();
            transaction.commit();
            log.info("Was founded " + courses.size() + " courses with type " + type + ".");
            return courses;
        } catch (Exception e){
            throw new MySQLException("Ошибка при получении списка курсов: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean enroll(UUID courseId, Student student) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, courseId);

            Optional<UUID> existedStudentId = course.getStudents().stream()
                    .map(s -> s.getId())
                    .filter(id -> id.equals(student.getId()))
                    .findAny();
            if (existedStudentId.isPresent()){
                transaction.commit();
                log.info("Enroll wasn't successful.");
                return false;
            } else {
                course.getStudents().add(student);
                session.update(course);
                transaction.commit();
                log.info("Enroll was successful.");
                return true;
            }
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new MySQLException("Ошибка во время записи на курс: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
