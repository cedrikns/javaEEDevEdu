package ru.tsedrik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.service.CourseService;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Controller
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class.getName());

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home() {
        logger.debug("enter to home page");
        return "home";
    }

    @GetMapping("/search")
    public String courseSearch(Model model) {
        logger.debug("courseSearch - start ");
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        logger.debug("courseSearch end with result {}", model);
        return "course-search-form";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String courseCreate(Model model) {
        logger.debug("courseCreate - start ", model);
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        logger.debug("courseCreate end with result {}", model);
        return "course-create-form";
    }

    @GetMapping("/courses-list")
    public String coursesList(@RequestParam String courseType, Model model) {
        logger.debug("coursesList with {} - start ", courseType);
        Collection<Course> foundedCourses;
        if (courseType == null || courseType.isEmpty()){
            foundedCourses = courseService.getAll();
        } else {
            CourseType chosenCourseType = CourseType.valueOf(courseType);
            foundedCourses = courseService.getCourseByType(chosenCourseType);
        }

        model.addAttribute("foundedCourses", foundedCourses);
        logger.debug("coursesList end with result {}", model);
        return "course-list";
    }

    @GetMapping(value = "/info/{id}")
    public String getCourseInfo(@PathVariable @NonNull UUID id, Model model) {
        logger.debug("getCourseInfo with {} - start ", id);
        Course course = courseService.getCourseById(id);

        if (course != null) {
            model.addAttribute("course", course);
            model.addAttribute("message", "Информация о выбранном курсе:");
        } else {
            throw new CourseNotFoundException("Course with id = " + id + " wasn't found.");
        }
        logger.debug("getCourseInfo end with result {}", model);
        return "course-info";
    }

    @PostMapping("/save-course")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String saveCourse(@ModelAttribute("course") Course course, Model model){
        logger.debug("saveCourse with {} - start ", course);
        model.addAttribute("message", "Информация о созданном курсе:");
        course = courseService.addCourse(course);
        model.addAttribute("course", course);
        logger.debug("saveCourse end with result {}", model);
        return "course-info";
    }

    @GetMapping("/enroll/{id}")
    public String enroll(@PathVariable @NonNull UUID id, Model model) {
        logger.debug("enroll with {} - start ", id);
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("courseId", id);
            model.addAttribute("course", course);
            logger.debug("enroll end with result {}", model);
            return "course-enroll";
        } else {
            throw new CourseNotFoundException("Course with id = " + id + " wasn't found.");
        }
    }

    @GetMapping("/enroll-confirm/{id}")
    public String confirmEnroll(@PathVariable @NonNull UUID id, @RequestParam String studentEmail, Model model) {
        logger.debug("confirmEnroll with {} and {} - start ", id, studentEmail);
        boolean isEnrolled = courseService.enroll(id, studentEmail);
        if (isEnrolled){
            model.addAttribute("message", "Запись прошла успешно.");
        } else {
            model.addAttribute("message", "Запись не удалась.");
        }
        logger.debug("confirmEnroll end with result {}", model);
        return "enroll-result";
    }

}
