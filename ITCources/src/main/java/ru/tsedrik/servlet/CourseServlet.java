package ru.tsedrik.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.StudentDAO;
import ru.tsedrik.dao.jdbc.CourseDAOImpl;
import ru.tsedrik.dao.jdbc.StudentDAOImpl;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.exception.MySQLException;
import ru.tsedrik.service.CourseService;
import ru.tsedrik.service.CourseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@WebServlet("/")
public class CourseServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CourseServlet.class.getName());

    private CourseService courseService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getServletPath();
        try {
            switch (option) {
                case "/search":
                    openCourseForm(req, resp, "course-search-form.jsp");
                    break;
                case "/create":
                    openCourseForm(req, resp, "course-create-form.jsp");
                    break;
                case "/courses-list":
                    coursesList(req, resp);
                    break;
                case "/show":
                    show(req, resp);
                    break;
                case "/enroll":
                    enroll(req, resp);
                    break;
                case "/enroll-confirm":
                    confirmEnroll(req, resp);
                    break;
            }
        } catch (MySQLException e){
            log.error(e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private void confirmEnroll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID enrolledCourseId = UUID.fromString(req.getParameter("id"));
        String email = req.getParameter("studentEmail").toLowerCase();
        boolean isEnrolled = courseService.enroll(enrolledCourseId, email);
        if (isEnrolled){
            req.setAttribute("message", "Запись прошла успешно.");
        } else {
            req.setAttribute("message", "Запись не удалась.");
        }
        req.getRequestDispatcher("enroll-result.jsp").forward(req, resp);
    }

    private void enroll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID courseId = UUID.fromString(req.getParameter("id"));
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            req.setAttribute("courseId", courseId);
            req.setAttribute("course", course);
            req.getRequestDispatcher("course-enroll.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if(id != null) {
            UUID uuid = UUID.fromString(req.getParameter("id"));
            Course course = courseService.getCourseById(uuid);
            if (course != null) {
                req.setAttribute("course", course);
                req.setAttribute("message", "Информация о выбранном курсе:");
            } else {
                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("message", "Информация о созданном курсе:");
            CourseType courseType = CourseType.valueOf(req.getParameter("courseType"));
            LocalDate beginDate = LocalDate.parse(req.getParameter("beginDate"));
            LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));
            int count = Integer.valueOf(req.getParameter("maxStudentsCount"));
            Course newCourse = courseService.addCourse(new Course(UUID.randomUUID(),
                    courseType, beginDate, endDate, count, CourseStatus.OPEN));
            req.setAttribute("course", newCourse);

        }
        req.getRequestDispatcher("course-info.jsp").forward(req, resp);
    }

    private void coursesList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseTypeValue = req.getParameter("courseType");
        Collection<Course> foundedCourses;
        if (courseTypeValue.isEmpty() || courseTypeValue == null){
            foundedCourses = courseService.getAll();
        } else {
            CourseType courseType = CourseType.valueOf(courseTypeValue);
            foundedCourses = courseService.getCourseByType(courseType);
        }

        req.setAttribute("foundedCourses", foundedCourses);
        req.getRequestDispatcher("course-list.jsp").forward(req, resp);
    }

    private void openCourseForm(HttpServletRequest req, HttpServletResponse resp, String jspForm) throws ServletException, IOException {
        req.setAttribute("courseTypes", Arrays.asList(CourseType.values()));
        req.getRequestDispatcher(jspForm).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = (SessionFactory)getServletContext().getAttribute("sessionFactory");
        CourseDAO courseDAO = new CourseDAOImpl(sessionFactory);
        StudentDAO studentDAO = new StudentDAOImpl(sessionFactory);
        courseService = new CourseServiceImpl(courseDAO, studentDAO);
        super.init();
    }
}
