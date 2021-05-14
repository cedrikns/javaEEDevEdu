package ru.tsedrik.service;

import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;

import java.util.Collection;
import java.util.UUID;

public interface CourseService {

    /**
     * Добавляет новый курс.
     *
     * @param courseDto    новый курс, который будет добавлен
     * @return  добавленный курс
     */
    Course addCourse(Course courseDto);

    /**
     * Удаляет существующий курс.
     *
     * @param course    существующий курс, который будет удален
     * @return  успешность удаления
     */
    boolean deleteCourse(Course course);

    /**
     * Удаляет существующий курс по его идентификатору.
     *
     * @param id    идентификатор удаляемого курса
     * @return  успешность удаления
     */
    boolean deleteCourseById(UUID id);

    /**
     * Запрашивает курс по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого курса
     * @return  найденный курс
     */
    Course getCourseById(UUID id);

    /**
     * Запрашивает набор всех курсов указанного типа CourseType.
     *
     * @param type  тип курсов для поиска
     * @return  список всех найденных курсов указанного типа
     */
    Collection<Course> getCourseByType(CourseType type);

    /**
     * Возвращает все курсы.
     *
     * @return  список всех найденных курсов
     */
    Collection<Course> getAll();

    /**
     * Записывает участника на конкретный курс
     * @param courseId  идентификатор курса, на который будет записан участник
     * @param email  элекстронный адрес участника
     * @return  успешность записи
     */
    boolean enroll(UUID courseId, String email);

}
