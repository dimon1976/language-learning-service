package by.languagelearningservice.controller;


import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teach/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public String index(Model model) {
        List<Course> coursesList = courseService.getAllListCourse();
        model.addAttribute("coursesList", coursesList);
        return "/teach/courses/index";
    }

    @GetMapping("/new")
    public String newCourse(Model model) {
        model.addAttribute("courseNull", new Course());
        return "teach/courses/new";
    }

    @PostMapping("/new")
    public String newCourse(@ModelAttribute("courseNull") Course course, BindingResult bindingResult, Model model) {
        Course c = courseService.save(mapper.map(course, Course.class));
        List<Course> coursesList = courseService.getAllListCourse();
        model.addAttribute("course", null);
        model.addAttribute("coursesList", coursesList);
        return "teach/courses/index";
    }

    @GetMapping("/filter")
    public String filterByStatus(CourseStatus status, Model model) {
            List<Course> coursesList = courseService.getListCourseByStatus(status).orElse(new ArrayList<>());
            model.addAttribute("coursesList", coursesList);
        return "/teach/courses/index";
    }
}
