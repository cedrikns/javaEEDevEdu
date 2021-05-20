package ru.tsedrik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Student;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentDAO studentDAO;

    StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentDAO);
    }

    @Test
    void addStudent() {
        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);
        Mockito.when(studentDAO.create(Mockito.any(Student.class))).thenReturn(student);

        Student returnedStudent = studentService.addStudent(email);

        assertNotNull(returnedStudent);
        assertEquals(student, returnedStudent);
        Mockito.verify(studentDAO, Mockito.atMostOnce()).create(Mockito.any(Student.class));
    }

    @Test
    void addStudentWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(null));
        Mockito.verify(studentDAO, Mockito.never()).create(Mockito.any());
    }

    @Test
    void getStudentById() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(studentDAO.getById(id)).thenReturn(student);

        assertEquals(student, studentService.getStudentById(id));
        Mockito.verify(studentDAO).getById(id);
    }

    @Test
    void getStudentByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentById(null));
        Mockito.verify(studentDAO, Mockito.never()).getById(Mockito.any());
    }

    @Test
    void getStudentByIdWithNotExistingId() {
        UUID id = UUID.randomUUID();

        Mockito.when(studentDAO.getById(id)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> studentService.getStudentById(id));
        Mockito.verify(studentDAO).getById(id);
    }

    @Test
    void deleteStudentById() {
        UUID id = UUID.randomUUID();
        Mockito.when(studentDAO.deleteById(id)).thenReturn(true);

        assertEquals(true, studentService.deleteStudentById(id));
        Mockito.verify(studentDAO).deleteById(id);
    }

    @Test
    void deleteStudentByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudentById(null));
        Mockito.verify(studentDAO, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void deleteStudent() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(studentDAO.delete(student)).thenReturn(true);

        assertEquals(true, studentService.deleteStudent(student));
        Mockito.verify(studentDAO).delete(student);
    }

    @Test
    void deleteStudentWithNullCourse() {
        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(null));
        Mockito.verify(studentDAO, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteStudentWithNullIdInCourse() {
        Student student = new Student(null, "test@mail.ru");

        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(student));
        Mockito.verify(studentDAO, Mockito.never()).delete(Mockito.any());
    }
}