package ru.tsedrik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    CourseDAO courseDAO;
    @Mock
    StudentDAO studentDAO;

    CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseDAO, studentDAO);
    }

    @Test
    void addCourse() {
        Course course = new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseDAO.create(course)).thenReturn(course);

        Course returnedCourse = courseService.addCourse(course);

        assertNotNull(returnedCourse);
        assertEquals(course, returnedCourse);
        Mockito.verify(courseDAO).create(course);
    }

    @Test
    void addCourseWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> courseService.addCourse(null));
        Mockito.verify(courseDAO, Mockito.never()).create(Mockito.any());
    }

    @Test
    void deleteCourse() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseDAO.delete(course)).thenReturn(true);

        assertEquals(true, courseService.deleteCourse(course));
        Mockito.verify(courseDAO).delete(course);
    }

    @Test
    void deleteCourseWithNullCourse() {
        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(null));
        Mockito.verify(courseDAO, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteCourseWithNullIdInCourse() {
        Course course = new Course(null, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(course));
        Mockito.verify(courseDAO, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteCourseById() {
        UUID id = UUID.randomUUID();
        Mockito.when(courseDAO.deleteById(id)).thenReturn(true);

        assertEquals(true, courseService.deleteCourseById(id));
        Mockito.verify(courseDAO).deleteById(id);
    }

    @Test
    void deleteCourseByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourseById(null));
        Mockito.verify(courseDAO, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void getCourseByType() {
        CourseType courseType = CourseType.CI;
        Collection<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), courseType, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(courseDAO.getByCourseType(courseType)).thenReturn(list);

        assertIterableEquals(list, courseService.getCourseByType(courseType));
        Mockito.verify(courseDAO).getByCourseType(courseType);

    }

    @Test
    void getCourseByTypeWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> courseService.getCourseByType(null));
        Mockito.verify(courseDAO, Mockito.never()).getByCourseType(Mockito.any());
    }

    @Test
    void getCourseById() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseDAO.getById(id)).thenReturn(course);

        assertEquals(course, courseService.getCourseById(id));
        Mockito.verify(courseDAO).getById(id);
    }

    @Test
    void getCourseByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> courseService.getCourseById(null));
        Mockito.verify(courseDAO, Mockito.never()).getById(Mockito.any());
    }

    @Test
    void getCourseByIdWithNotExistingId() {
        UUID id = UUID.randomUUID();

        Mockito.when(courseDAO.getById(id)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> courseService.getCourseById(id));
        Mockito.verify(courseDAO).getById(id);
    }

    @Test
    void getAll() {
        Collection<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(courseDAO.getAll()).thenReturn(list);

        assertIterableEquals(list, courseService.getAll());
        Mockito.verify(courseDAO).getAll();
    }

    @Test
    void enrollWithExistingStudent() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);

        Mockito.when(studentDAO.getStudentByEmail(email)).thenReturn(student);
        Mockito.when(courseDAO.enroll(id, student)).thenReturn(true);
        Mockito.when(courseDAO.getById(id)).thenReturn(course);

        assertEquals(true, courseService.enroll(id, email));
        Mockito.verify(courseDAO).getById(id);
        Mockito.verify(studentDAO).getStudentByEmail(email);
        Mockito.verify(courseDAO).enroll(id, student);
        Mockito.verify(studentDAO, Mockito.never()).create(Mockito.any(Student.class));
    }

    @Test
    void enrollWithNotExistingStudent() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);

        Mockito.when(studentDAO.getStudentByEmail(email)).thenReturn(null);
        Mockito.when(studentDAO.create(Mockito.any(Student.class))).thenReturn(student);
        Mockito.when(courseDAO.enroll(Mockito.eq(id), Mockito.any(Student.class))).thenReturn(true);
        Mockito.when(courseDAO.getById(id)).thenReturn(course);

        assertEquals(true, courseService.enroll(id, email));
        Mockito.verify(courseDAO).getById(id);
        Mockito.verify(studentDAO).getStudentByEmail(email);
        Mockito.verify(courseDAO).enroll(Mockito.eq(id), Mockito.any(Student.class));
        Mockito.verify(studentDAO).create(Mockito.any(Student.class));
    }

    @Test
    void enrollWithNullArgs() {
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(null, "test@mail.ru"));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(UUID.randomUUID(), ""));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(UUID.randomUUID(), null));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(null, null));
        Mockito.verify(courseDAO, Mockito.never()).enroll(Mockito.any(), Mockito.any());
    }

    @Test
    void enrollWithNotExistingCourse() {
        UUID id = UUID.randomUUID();

        Mockito.when(courseDAO.getById(id)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> courseService.enroll(id, "test@mail.ru"));
        Mockito.verify(courseDAO).getById(id);
        Mockito.verify(courseDAO, Mockito.never()).enroll(Mockito.any(), Mockito.any());
        Mockito.verify(studentDAO, Mockito.never()).create(Mockito.any(Student.class));
    }
}