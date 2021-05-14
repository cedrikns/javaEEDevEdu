package ru.tsedrik.service;

import org.hibernate.SessionFactory;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.dao.jdbc.CourseDAOImpl;
import ru.tsedrik.dao.jdbc.StudentDAOImpl;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService{

    private CourseDAO courseDAO;

    private StudentDAO studentDAO;

    public CourseServiceImpl(SessionFactory sessionFactory) {
        this.courseDAO = new CourseDAOImpl(sessionFactory);
        this.studentDAO = new StudentDAOImpl(sessionFactory);
    }

    @Override
    public Course addCourse(Course course) {

       return courseDAO.create(course);
    }

    @Override
    public boolean deleteCourse(Course course) {
        return courseDAO.delete(course);
    }

    @Override
    public boolean deleteCourseById(UUID id) {
        return courseDAO.deleteById(id);
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        return courseDAO.getByCourseType(type);
    }

    @Override
    public Course getCourseById(UUID id) {
        Course course = courseDAO.getById(id);

        if (course == null){
            throw new RuntimeException("There wasn't found course with id = " + id + ".");
        }
        return course;
    }

    @Override
    public Collection<Course> getAll() {
        return courseDAO.getAll();
    }

    public boolean enroll(UUID courseId, String email){
        Course course = courseDAO.getById(courseId);

        if (course == null){
            throw new RuntimeException("There wasn't found course with id = " + courseId + ".");
        }

        Student enrolledStudent = studentDAO.getStudentByEmail(email);
        if (enrolledStudent == null){
            enrolledStudent = new Student(UUID.randomUUID(), email);
            studentDAO.create(enrolledStudent);
        }

        boolean isEnrolled = courseDAO.enroll(courseId, enrolledStudent);

        return isEnrolled;
    }
}
