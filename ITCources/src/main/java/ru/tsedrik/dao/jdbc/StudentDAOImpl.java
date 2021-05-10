package ru.tsedrik.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.entity.Student;

import java.sql.*;
import java.util.*;

public class StudentDAOImpl implements StudentDAO {

    private static final Logger log = LogManager.getLogger(StudentDAOImpl.class.getName());

    private static final String INSERT_STUDENT = "insert into student (id, email, first_name, last_name) values(?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "update student set first_name = ?, last_name = ? where student_id = ?";
    private static final String SELECT_STUDENT_BY_ID = "select * from student where student_id = ?";
    private static final String SELECT_STUDENT_BY_EMAIL = "select * from student where email = ?";
    private static final String SELECT_STUDENT_BY_COURSE = "select * from student s join course_student cs on s.id = cs.student_id where course_id = ?";
    private static final String SELECT_STUDENT_ALL = "select * from student";
    private static final String DELETE_STUDENT_BY_ID = "delete from student where id = ?";
    private static final String DELETE_STUDENT_LINK = "delete from course_student where student_id = ?";

    public StudentDAOImpl() {
    }

    @Override
    public Student create(Student student) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_STUDENT)){

            int i = 1;
            statement.setObject(i++, student.getId());
            statement.setString(i++, student.getEmail());
            statement.setString(i++, student.getFirstName());
            statement.setString(i++, student.getLastName());

            int result = statement.executeUpdate();

            if (result < 1) {
                log.info("Student with id = " + student.getId() + " wasn't added.");
                return null;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        log.info("Student with id = " + student.getId() + " was added.");
        return student;
    }

    @Override
    public Student getById(UUID id) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_ID)){

            statement.setObject(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return getStudentFromResultSet(resultSet);
                } else {
                    log.info("There wan't found student with id = " + id);
                    return null;
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Student> getAll() {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_ALL);
            ResultSet resultSet = statement.executeQuery()){

            List<Student> students = new ArrayList<>();

            while (resultSet.next()){
                students.add(getStudentFromResultSet(resultSet));
            }

            log.info("Was founded " + students.size() + " students.");

            return students;

        } catch (SQLException e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Student update(Student student) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT)){

            int i = 1;
            statement.setString(i++, student.getFirstName());
            statement.setString(i++, student.getLastName());
            statement.setObject(i++, student.getId());

            int result = statement.executeUpdate();

            if (result < 1) {
                log.info("Student with id = " + student.getId() + " wasn't updated.");
                return null;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        log.info("Student with id = " + student.getId() + " was updated.");
        return student;
    }

    @Override
    public boolean delete(Student student) {
        return deleteById(student.getId());
    }

    @Override
    public boolean deleteById(UUID id) {

        try(Connection connection = ConnectorDB.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID);
            PreparedStatement statementForLink = connection.prepareStatement(DELETE_STUDENT_LINK)) {

                connection.setAutoCommit(false);
                statementForLink.setObject(1, id);
                int result = statementForLink.executeUpdate();
                log.info("Was deleted " + result + " links.");

                statement.setObject(1, id);
                result = statement.executeUpdate();
                connection.commit();
                log.info("Was deleted " + result + " students.");
                connection.setAutoCommit(true);
                return result > 0 ? true : false;

            }catch (SQLException e){
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_EMAIL)){

            statement.setObject(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return getStudentFromResultSet(resultSet);
                } else {
                    log.info("There wan't found student with email = " + email);
                    return null;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Student> getAllByCourseId(UUID courseId) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_BY_COURSE)){

            statement.setObject(1, courseId);

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Student> students = new ArrayList<>();

                while (resultSet.next()) {
                    students.add(getStudentFromResultSet(resultSet));
                }

                log.info("Was founded " + students.size() + " students on course with id = " + courseId + ".");

                return students;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    private Student getStudentFromResultSet(ResultSet resultSet) throws SQLException{
        Student student = new Student();
        int i = 1;
        student.setId(((UUID)resultSet.getObject(i++)));
        student.setEmail(resultSet.getString(i++));
        student.setLastName(resultSet.getString(i++));
        student.setFirstName(resultSet.getString(i++));
        return student;
    }
}
