package ru.tsedrik.service;

import org.hibernate.SessionFactory;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.dao.jdbc.StudentDAOImpl;
import ru.tsedrik.entity.Student;

import java.util.UUID;

public class StudentServiceImpl implements StudentService{

    private StudentDAO studentDAO;

    public StudentServiceImpl(SessionFactory sessionFactory){
        this.studentDAO = new StudentDAOImpl(sessionFactory);
    }

    @Override
    public Student addStudent(String email) {
        Student student = new Student(UUID.randomUUID(), email);
        studentDAO.create(student);
        return student;
    }

    @Override
    public Student getStudentById(UUID id) {
        Student student = studentDAO.getById(id);
        if (student == null){
            throw new RuntimeException("There is no student with id = " + id);
        }
        return student;
    }

    @Override
    public boolean deleteStudentById(UUID id) {
        return studentDAO.deleteById(id);
    }

    @Override
    public boolean deleteStudent(Student student) {
        return studentDAO.delete(student);
    }

}
