package ru.tsedrik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.StudentRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    CourseRepository courseRepository;
    @Mock
    StudentRepository studentRepository;

    CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository, studentRepository);
    }

    @Test
    void addCourse() {
        Course course = new Course(null, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, null);
        Mockito.when(courseRepository.save(course)).thenReturn(course);

        Course returnedCourse = courseService.addCourse(course);

        assertNotNull(course.getId());
        assertNotNull(course.getCourseStatus());
        assertNotNull(returnedCourse);
        assertEquals(course, returnedCourse);
        Mockito.verify(courseRepository).save(course);
    }

    @Test
    void addCourseWithNullArg() {
        assertThrows(IllegalArgumentException.class, () -> courseService.addCourse(null));
        Mockito.verify(courseRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteCourse() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertEquals(true, courseService.deleteCourse(course));
        Mockito.verify(courseRepository).delete(course);
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void deleteCourseWithNullCourse() {
        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(null));
        Mockito.verify(courseRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteCourseWithNullIdInCourse() {
        Course course = new Course(null, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(course));
        Mockito.verify(courseRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void deleteCourseWithNotExistingCourse() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(course));
        Mockito.verify(courseRepository, Mockito.never()).delete(course);
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void deleteCourseById() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertEquals(true, courseService.deleteCourseById(id));
        Mockito.verify(courseRepository).deleteById(id);
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void deleteCourseByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourseById(null));
        Mockito.verify(courseRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void deleteCourseByIdWithNotExistingCourse() {
        UUID id = UUID.randomUUID();
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourseById(id));
        Mockito.verify(courseRepository, Mockito.never()).deleteById(id);
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void getCourseByType() {
        CourseType courseType = CourseType.CI;
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), courseType, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(courseRepository.findAllByCourseType(courseType)).thenReturn(list);

        assertIterableEquals(list, courseService.getCourseByType(courseType));
        Mockito.verify(courseRepository).findAllByCourseType(courseType);

    }

    @Test
    void getCourseByTypeWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> courseService.getCourseByType(null));
        Mockito.verify(courseRepository, Mockito.never()).findAllByCourseType(Mockito.any());
    }

    @Test
    void getCourseById() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertEquals(course, courseService.getCourseById(id));
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void getCourseByIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> courseService.getCourseById(null));
        Mockito.verify(courseRepository, Mockito.never()).findById(Mockito.any());
    }

    @Test
    void getCourseByIdWithNotExistingId() {
        UUID id = UUID.randomUUID();

        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> courseService.getCourseById(id));
        Mockito.verify(courseRepository).findById(id);
    }

    @Test
    void getAll() {
        List<Course> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Course(UUID.randomUUID(), CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN));
        }

        Mockito.when(courseRepository.findAll()).thenReturn(list);

        assertIterableEquals(list, courseService.getAll());
        Mockito.verify(courseRepository).findAll();
    }

    @Test
    void enrollWithExistingStudent() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);

        Mockito.when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertEquals(true, courseService.enroll(id, email));
        Mockito.verify(courseRepository).findById(id);
        Mockito.verify(studentRepository).findByEmail(email);
        Mockito.verify(courseRepository).save(course);
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    }

    @Test
    void enrollWithNotExistingStudent() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, CourseType.CI, LocalDate.now(), LocalDate.now().plusDays(3), 8, CourseStatus.OPEN);

        String email = "test@mail.ru";
        Student student = new Student(UUID.randomUUID(), email);

        Mockito.when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertEquals(true, courseService.enroll(id, email));
        Mockito.verify(courseRepository).findById(id);
        Mockito.verify(studentRepository).findByEmail(email);
        Mockito.verify(courseRepository).save(course);
        Mockito.verify(studentRepository).save(Mockito.any(Student.class));
    }

    @Test
    void enrollWithNullArgs() {
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(null, "test@mail.ru"));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(UUID.randomUUID(), ""));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(UUID.randomUUID(), null));
        assertThrows(IllegalArgumentException.class, () -> courseService.enroll(null, null));
        Mockito.verify(courseRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void enrollWithNotExistingCourse() {
        UUID id = UUID.randomUUID();

        Mockito.when(courseRepository.findById(id)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> courseService.enroll(id, "test@mail.ru"));
        Mockito.verify(courseRepository).findById(id);
        Mockito.verify(courseRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    }
}