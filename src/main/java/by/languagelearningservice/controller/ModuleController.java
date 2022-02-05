package by.languagelearningservice.controller;


import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teach/courses/modules")
public class ModuleController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add{id}")
    public String add(@ModelAttribute("newModule") Module module, Long id, Model model) {
        Optional<Course> course = courseService.findById(id);
        List<Course> allListCourse = courseService.getAllListCourse();
        course.get().getModules().add(module);
        courseService.save(course.get());
        model.addAttribute("coursesList",allListCourse);
        model.addAttribute("course",course);
        return "teach/courses/index";
    }

}
