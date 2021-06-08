package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import ru.tsedrik.entity.Student;
import ru.tsedrik.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
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
                .orElseThrow(() -> new RuntimeException("There is no student with id = " + id));

        return student;
    }

    @Override
    public boolean deleteStudentById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Student's id can't be null.");
        }
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteStudent(Student student) {
        if (student == null || student.getId() == null){
            throw new IllegalArgumentException("Deleted student and its id can't be null.");
        }
        studentRepository.delete(student);
        return true;
    }

}
