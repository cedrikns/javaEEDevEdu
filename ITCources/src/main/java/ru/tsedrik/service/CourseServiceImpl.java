package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;

import java.util.Collection;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseDAO courseDAO;

    private StudentDAO studentDAO;

    public CourseServiceImpl(CourseDAO courseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
    }

    @Override
    public Course addCourse(Course course) {
        if (course == null){
            throw new IllegalArgumentException("Added course can't be null.");
        }
        return courseDAO.create(course);
    }

    @Override
    public boolean deleteCourse(Course course) {
        if (course == null || course.getId() == null){
            throw new IllegalArgumentException("Deleted course and its id can't be null.");
        }
        return courseDAO.delete(course);
    }

    @Override
    public boolean deleteCourseById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Deleted course's id can't be null.");
        }
        return courseDAO.deleteById(id);
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        if (type == null){
            throw new IllegalArgumentException("Type can't be null.");
        }
        return courseDAO.getByCourseType(type);
    }

    @Override
    public Course getCourseById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Course's id can't be null.");
        }

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

        if (courseId == null || email == null || email.isEmpty()){
            throw new IllegalArgumentException("Course's id and email can't be null or empty.");
        }

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
