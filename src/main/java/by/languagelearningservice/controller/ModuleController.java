package by.languagelearningservice.controller;


import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/teach/courses/modules")
public class ModuleController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add{id}")
    public String edit(@ModelAttribute("newModule") Module module, Long id, Model model) {
        Optional<Course> course = courseService.findById(id);
        course.get().getModules().add(module);
        model.addAttribute("course",course);
        return "teach/courses/modules/edit";
    }

}
