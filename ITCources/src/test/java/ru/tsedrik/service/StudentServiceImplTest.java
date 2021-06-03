package ru.tsedrik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.entity.Student;
import ru.tsedrik.repository.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepository);
    }

    @Test
    void addStudent() {
        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        Student returnedStudent = studentService.addStudent(email);

        assertNotNull(returnedStudent);
        assertEquals(student, returnedStudent);
        Mockito.verify(studentRepository, Mockito.atMostOnce()).save(Mockito.any(Student.class));
    }

    @Test
    void addStudentWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(null));
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getStudentById() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");
        Mockito.when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        assertEquals(student, studentService.getStudentById(id));
        Mockito.verify(studentRepository).findById(id);
    }

    @Test
    void getStudentByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentById(null));
        Mockito.verify(studentRepository, Mockito.never()).findById(Mockito.any());
    }

    @Test
    void getStudentByIdWithNotExistingId() {
        UUID id = UUID.randomUUID();

        Mockito.when(studentRepository.findById(id)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> studentService.getStudentById(id));
        Mockito.verify(studentRepository).findById(id);
    }

    @Test
    void deleteStudentById() {
        UUID id = UUID.randomUUID();

        assertEquals(true, studentService.deleteStudentById(id));
        Mockito.verify(studentRepository).deleteById(id);
    }

    @Test
    void deleteStudentByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudentById(null));
        Mockito.verify(studentRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void deleteStudent() {
        UUID id = UUID.randomUUID();
        Student student = new Student(id, "test@mail.ru");

        assertEquals(true, studentService.deleteStudent(student));
        Mockito.verify(studentRepository).delete(student);
    }

    @Test
    void deleteStudentWithNullCourse() {
        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(null));
        Mockito.verify(studentRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteStudentWithNullIdInCourse() {
        Student student = new Student(null, "test@mail.ru");

        assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(student));
        Mockito.verify(studentRepository, Mockito.never()).delete(Mockito.any());
    }
}