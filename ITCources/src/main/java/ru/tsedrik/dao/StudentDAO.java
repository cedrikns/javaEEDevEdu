package ru.tsedrik.dao;

import ru.tsedrik.entity.Student;

import java.util.Collection;
import java.util.UUID;

public interface StudentDAO extends GenericDAO<Student, UUID> {

    /**
     * Возвращает участника по его электронному адресу
     *
     * @param email электронный адрес участника
     * @return  найденный участник
     */
    Student getStudentByEmail(String email);

    /**
     * Возвращает всех участников курса
     * @param courseId  идентификатор курса
     * @return  список найденных участников
     */
    Collection<Student> getAllByCourseId(UUID courseId);

}
