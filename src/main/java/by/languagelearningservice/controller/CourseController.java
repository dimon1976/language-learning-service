package by.languagelearningservice.controller;


import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}/syllabus")
    public String syllabus(@PathVariable("id") Long id, Model model) {
        Optional<Course> course = courseService.findById(id);
        model.addAttribute("course", course);
        return "teach/courses/syllabus";
    }

    @GetMapping("/{id}/edit{module}")
    public String edit(@PathVariable("id") Long id, String module, Model model) {
        Optional<Course> course = courseService.findById(id);
        if (module != null && module.equals("add")) {
            model.addAttribute("newModule", new Module());
            model.addAttribute("course", course);
            return "teach/courses/modules/add";
        }
//        model.addAttribute("newModule", module);
        model.addAttribute("course", course);
        return "teach/courses/edit";
    }

}
