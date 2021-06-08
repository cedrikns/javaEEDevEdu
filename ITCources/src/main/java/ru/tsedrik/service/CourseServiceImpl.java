package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Course addCourse(Course course) {
        if (course == null){
            throw new IllegalArgumentException("Added course can't be null.");
        }
        course.setId(UUID.randomUUID());
        course.setCourseStatus(CourseStatus.OPEN);
        return courseRepository.save(course);
    }

    @Override
    public boolean deleteCourse(Course course) {
        if (course == null || course.getId() == null){
            throw new IllegalArgumentException("Deleted course and its id can't be null.");
        }
        courseRepository.delete(course);
        return true;
    }

    @Override
    public boolean deleteCourseById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Deleted course's id can't be null.");
        }
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
                .orElseThrow(() -> new RuntimeException("There wasn't found course with id = " + id + "."));

        return course;
    }

    @Override
    public Collection<Course> getAll() {
        return courseRepository.findAll();
    }

    public boolean enroll(UUID courseId, String email){

        if (courseId == null || email == null || email.isEmpty()){
            throw new IllegalArgumentException("Course's id and email can't be null or empty.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("There wasn't found course with id = " + courseId + "."));

        if (course.getCourseStatus() == CourseStatus.CLOSE){
            throw new IllegalArgumentException("This course is closed. Please, choose another one.");
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
