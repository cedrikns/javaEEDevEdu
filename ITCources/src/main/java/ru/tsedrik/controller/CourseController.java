package ru.tsedrik.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.service.CourseService;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Controller
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/search")
    public String courseSearch(Model model) {
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        return "course-search-form";
    }

    @GetMapping("/create")
    public String courseCreate(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        return "course-create-form";
    }

    @GetMapping("/courses-list")
    public String coursesList(@RequestParam String courseType, Model model) {
        Collection<Course> foundedCourses;
        if (courseType == null || courseType.isEmpty()){
            foundedCourses = courseService.getAll();
        } else {
            CourseType chosenCourseType = CourseType.valueOf(courseType);
            foundedCourses = courseService.getCourseByType(chosenCourseType);
        }

        model.addAttribute("foundedCourses", foundedCourses);
        return "course-list";
    }

    @GetMapping(value = "/info/{id}")
    public String getCourseInfo(@PathVariable @NonNull UUID id, Model model) {
        Course course = courseService.getCourseById(id);

        if (course != null) {
            model.addAttribute("course", course);
            model.addAttribute("message", "Информация о выбранном курсе:");
        } else {
            throw new IllegalArgumentException("Course with id = " + id + " wasn't found.");
        }
        return "../course-info";
    }

    @PostMapping("/save-course")
    public String saveCourse(@ModelAttribute("course") Course course, Model model){
        model.addAttribute("message", "Информация о созданном курсе:");
        course = courseService.addCourse(course);
        model.addAttribute("course", course);
        return "course-info";
    }

    @GetMapping("/enroll/{id}")
    public String enroll(@PathVariable @NonNull UUID id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("courseId", id);
            model.addAttribute("course", course);
            return "../course-enroll";
        } else {
            throw new IllegalArgumentException("Course with id = " + id + " wasn't found.");
        }
    }

    @GetMapping("/enroll-confirm/{id}")
    public String confirmEnroll(@PathVariable @NonNull UUID id, @RequestParam String studentEmail, Model model) {
        boolean isEnrolled = courseService.enroll(id, studentEmail);
        if (isEnrolled){
            model.addAttribute("message", "Запись прошла успешно.");
        } else {
            model.addAttribute("message", "Запись не удалась.");
        }
        return "../enroll-result";
    }

}
