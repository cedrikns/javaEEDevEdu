package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.exception.StudentNotFoundException;
import ru.tsedrik.repository.StudentRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Student addStudent(String email) {
        if (email == null){
            throw new IllegalArgumentException("Email can't be null.");
        }
        Student student = new Student(UUID.randomUUID(), email);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Student's id can't be null.");
        }
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("There is no student with id = " + id));

        return student;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteStudentById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Student's id can't be null.");
        }
        studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("There is no student with id = " + id));
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteStudent(Student student) {
        if (student == null || student.getId() == null){
            throw new IllegalArgumentException("Deleted student and its id can't be null.");
        }
        studentRepository.findById(student.getId())
                .orElseThrow(() -> new StudentNotFoundException("There is no student with id = " + student.getId()));
        studentRepository.delete(student);
        return true;
    }

}
