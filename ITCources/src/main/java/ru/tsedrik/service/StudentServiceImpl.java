package ru.tsedrik.service;

import org.springframework.stereotype.Service;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Student;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO){
        this.studentDAO = studentDAO;
    }

    @Override
    public Student addStudent(String email) {
        if (email == null){
            throw new IllegalArgumentException("Email can't be null.");
        }
        Student student = new Student(UUID.randomUUID(), email);
        return studentDAO.create(student);
    }

    @Override
    public Student getStudentById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Student's id can't be null.");
        }
        Student student = studentDAO.getById(id);
        if (student == null){
            throw new RuntimeException("There is no student with id = " + id);
        }
        return student;
    }

    @Override
    public boolean deleteStudentById(UUID id) {
        if (id == null){
            throw new IllegalArgumentException("Student's id can't be null.");
        }
        return studentDAO.deleteById(id);
    }

    @Override
    public boolean deleteStudent(Student student) {
        if (student == null || student.getId() == null){
            throw new IllegalArgumentException("Deleted student and its id can't be null.");
        }
        return studentDAO.delete(student);
    }

}
