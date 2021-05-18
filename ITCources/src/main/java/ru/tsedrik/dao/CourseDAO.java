package ru.tsedrik.dao;

import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.entity.Student;

import java.util.Collection;
import java.util.UUID;

public interface CourseDAO extends GenericDAO<Course, UUID>{

    /**
     * Возвращает записи о курсах указанного типа.
     * @param type  тип курса
     * @return  список найденных курсов
     */
    Collection<Course> getByCourseType(CourseType type);

    /**
     * Записывает участника на курс.
     * @param courseId  идентификатор курса
     * @param student  записываемый участник
     * @return  успешность записи
     */
    boolean enroll(UUID courseId, Student student);
}
