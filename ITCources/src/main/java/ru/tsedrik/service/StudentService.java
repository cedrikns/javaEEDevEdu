package ru.tsedrik.service;

import ru.tsedrik.entity.Student;

import java.util.UUID;

public interface StudentService {

    /**
     * Добавляет нового участника курсов.
     *
     * @param email    электронный адрес нового участника
     * @return  добавленный участник
     */
    Student addStudent(String email);

    /**
     * Удаляет существующего участника курса.
     *
     * @param student    существующий участник курса, который будет удален
     * @return  успешность удаления
     */
    boolean deleteStudent(Student student);

    /**
     * Удаляет существующего участника курса по его идентификатору.
     *
     * @param id    идентификатор удаляемого участника
     * @return  успешность удаления
     */
    boolean deleteStudentById(UUID id);

    /**
     * Запрашивает участника курса по его идентификатору.
     *
     * @param id    идентификатор запрашиваемого участника
     * @return  найденный участник
     */
    Student getStudentById(UUID id);

}
