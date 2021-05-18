package ru.tsedrik.dao;

import ru.tsedrik.entity.Student;

import java.util.UUID;

public interface StudentDAO extends GenericDAO<Student, UUID> {

    /**
     * Возвращает участника по его электронному адресу
     *
     * @param email электронный адрес участника
     * @return  найденный участник
     */
    Student getStudentByEmail(String email);

}
