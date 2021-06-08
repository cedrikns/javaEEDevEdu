package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.CourseNotAvailableForChangeException;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Course addCourse(Course course) {
        if (course == null){
            throw new IllegalArgumentException("Added course can't be null.");
        }
        course.setId(UUID.randomUUID());
        course.setCourseStatus(CourseStatus.OPEN);
        return courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteCourse(Course course) {
        if (course == null || course.getId() == null){
            throw new IllegalArgumentException("Deleted course and its id can't be null.");
        }
        courseRepository.findById(course.getId())
                .orElseThrow(() -> new CourseNotFoundException("There wasn't found course with id = " + course.getId() + "."));
        courseRepository.delete(course);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteCourseById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Deleted course's id can't be null.");
        }
        courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("There wasn't found course with id = " + id + "."));
        courseRepository.deleteById(id);
        return true;
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        if (type == null){
            throw new IllegalArgumentException("Type can't be null.");
        }
        return courseRepository.findAllByCourseType(type);
    }

    @Override
    public Course getCourseById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Course's id can't be null.");
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("There wasn't found course with id = " + id + "."));

        return course;
    }

    @Override
    public Collection<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean enroll(UUID courseId, String email){

        if (courseId == null || email == null || email.isEmpty()){
            throw new IllegalArgumentException("Course's id and email can't be null or empty.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("There wasn't found course with id = " + courseId + "."));

        if (course.getCourseStatus() == CourseStatus.CLOSE){
            throw new CourseNotAvailableForChangeException("This course is closed. Please, choose another one.");
        }

        Student enrolledStudent = studentRepository.findByEmail(email)
                .orElseGet(() -> {
                    Student student = new Student(UUID.randomUUID(), email);
                    studentRepository.save(student);
                    return student;
                });

        Optional<UUID> existedStudentId = course.getStudents().stream()
                    .map(s -> s.getId())
                    .filter(id -> id.equals(enrolledStudent.getId()))
                    .findAny();
        if (existedStudentId.isPresent()){
            return false;
        } else {
            course.getStudents().add(enrolledStudent);
            courseRepository.save(course);
            return true;
        }

    }
}
