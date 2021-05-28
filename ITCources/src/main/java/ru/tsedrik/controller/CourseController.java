package ru.tsedrik.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tsedrik.entity.Course;
import ru.tsedrik.entity.CourseStatus;
import ru.tsedrik.entity.CourseType;
import ru.tsedrik.service.CourseService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Controller
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/search")
    public String courseSearch(Model model) {
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        return "course-search-form";
    }

    @RequestMapping("/create")
    public String courseCreate(Model model) {
        model.addAttribute("courseTypes", Arrays.asList(CourseType.values()));
        return "course-create-form";
    }

    @RequestMapping("/courses-list")
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

    @RequestMapping(value = "/show")
    public String show(@RequestParam @Nullable UUID id, String courseType, String beginDate, String endDate, Integer maxStudentsCount, Model model) {
        if(id != null) {
            Course course = courseService.getCourseById(id);
            if (course != null) {
                model.addAttribute("course", course);
                model.addAttribute("message", "Информация о выбранном курсе:");
            } else {
                return "error";
            }
        } else {
            model.addAttribute("message", "Информация о созданном курсе:");
            Course newCourse = courseService.addCourse(new Course(UUID.randomUUID(),
                    CourseType.valueOf(courseType), LocalDate.parse(beginDate),
                    LocalDate.parse(endDate), maxStudentsCount, CourseStatus.OPEN));
            model.addAttribute("course", newCourse);

        }
        return "course-info";
    }

    @RequestMapping("/enroll")
    public String enroll(@RequestParam UUID id, Model model) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            model.addAttribute("courseId", id);
            model.addAttribute("course", course);
            return "course-enroll";
        } else {
            return "error";
        }
    }

    @RequestMapping("/enroll-confirm")
    public String confirmEnroll(@RequestParam UUID id, String studentEmail, Model model) {
        boolean isEnrolled = courseService.enroll(id, studentEmail);
        if (isEnrolled){
            model.addAttribute("message", "Запись прошла успешно.");
        } else {
            model.addAttribute("message", "Запись не удалась.");
        }
        return "enroll-result";
    }

}
