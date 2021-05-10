package ru.tsedrik.dao.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CourseDAOImpl implements CourseDAO {

    private static final Logger log = LogManager.getLogger(CourseDAOImpl.class.getName());

    private static final String INSERT_COURSE = "insert into course (id, course_type, begin_date, end_date, max_students_count, course_status) values(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_COURSE = "update course set begin_date = ?, end_date = ?, max_students_count = ?, course_status = ? where course_id = ?";
    private static final String SELECT_COURSE_BY_ID = "select * from course where id = ?";
    private static final String SELECT_COURSE_BY_TYPE = "select * from course where course_type = ?";
    private static final String SELECT_COURSE_ALL = "select * from course";
    private static final String DELETE_COURSE_BY_ID = "delete from course where id = ?";
    private static final String DELETE_COURSE_LINK = "delete from course_student where course_id = ?";
    private static final String ENROLL = "insert into course_student(course_id, student_id) values (?, ?)";

    public CourseDAOImpl() {
    }

    @Override
    public Course create(Course course) {

        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_COURSE)){

            int i = 1;
            statement.setObject(i++, course.getId());
            statement.setString(i++, course.getCourseType().toString());
            statement.setDate(i++, Date.valueOf(course.getBeginDate()));
            statement.setDate(i++, Date.valueOf(course.getEndDate()));
            statement.setInt(i++, course.getMaxStudentsCount());
            statement.setString(i++, course.getCourseStatus().toString());

            int result = statement.executeUpdate();

            if (result < 1) {
                log.info("Course with id = " + course.getId() + " wasn't added.");
                return null;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        log.info("Course with id = " + course.getId() + " was added.");
        return course;
    }

    @Override
    public Course getById(UUID id) {

        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_ID)){

            statement.setObject(1, id);

            try(ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return getCourseFromResultSet(resultSet);
                } else {
                    log.info("There wan't found course with id = " + id);
                    return null;
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Course> getAll() {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_ALL);
            ResultSet resultSet = statement.executeQuery()){

            List<Course> courses = new ArrayList<>();

            while (resultSet.next()){
                courses.add(getCourseFromResultSet(resultSet));
            }

            log.info("Was founded " + courses.size() + " courses.");

            return courses;

        } catch (SQLException e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Course update(Course course) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)){

            int i = 1;
            statement.setDate(i++, Date.valueOf(course.getBeginDate()));
            statement.setDate(i++, Date.valueOf(course.getEndDate()));
            statement.setInt(i++, course.getMaxStudentsCount());
            statement.setString(i++, course.getCourseStatus().toString());
            statement.setObject(i++, course.getId());

            int result = statement.executeUpdate();

            if (result < 1) {
                log.info("Course with id = " + course.getId() + " wasn't updated.");
                return null;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        log.info("Course with id = " + course.getId() + " was updated.");
        return course;
    }

    @Override
    public boolean delete(Course course) {
        return deleteById(course.getId());
    }

    @Override
    public boolean deleteById(UUID id) {
        try(Connection connection = ConnectorDB.getConnection()){

            try(PreparedStatement statement = connection.prepareStatement(DELETE_COURSE_BY_ID);
                PreparedStatement statementForLink = connection.prepareStatement(DELETE_COURSE_LINK)){

                connection.setAutoCommit(false);
                statementForLink.setObject(1, id);
                int result = statementForLink.executeUpdate();
                log.info("Was deleted " + result + " links.");

                statement.setObject(1, id);
                result = statement.executeUpdate();

                connection.commit();
                log.info("Was deleted " + result + " courses.");
                connection.setAutoCommit(true);
                return result > 0 ? true : false;

            } catch (SQLException e){
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
    public Collection<Course> getByCourseType(CourseType type) {

        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_COURSE_BY_TYPE)){

            statement.setObject(1, type.toString());
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (resultSet.next()){
                    courses.add(getCourseFromResultSet(resultSet));
                }

                log.info("Was founded " + courses.size() + " courses with type " + type + ".");
                return courses;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean enroll(UUID courseId, UUID studentId) {
        try(Connection connection = ConnectorDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(ENROLL)){

            int i = 1;
            statement.setObject(i++, courseId);
            statement.setObject(i++, studentId);

            int result = statement.executeUpdate();

            if (result < 1) {
                log.info("Enroll wasn't successful.");
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

        log.info("Enroll wasn successful.");
        return true;
    }

    private Course getCourseFromResultSet(ResultSet resultSet) throws SQLException{
        Course course = new Course();
        int i = 1;
        course.setId(((UUID)resultSet.getObject(i++)));
        course.setCourseType(CourseType.valueOf(resultSet.getString(i++)));
        course.setBeginDate(resultSet.getDate(i++).toLocalDate());
        course.setEndDate(resultSet.getDate(i++).toLocalDate());
        course.setMaxStudentsCount(resultSet.getInt(i++));
        course.setCourseStatus(CourseStatus.valueOf(resultSet.getString(i++)));
        return course;
    }
}
