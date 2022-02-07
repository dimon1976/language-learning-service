package by.languagelearningservice.controller;


import by.languagelearningservice.dto.ModuleDto;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.ModuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/teach/courses/modules")
public class ModuleController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add{id}")
    public String add(@ModelAttribute("newModule") @Valid ModuleDto moduleDto, BindingResult result, Model model, Long id,
                      @RequestParam Optional<Integer> page,
                      @RequestParam Optional<Integer> size,
                      @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("id"));
        {
            if (result.hasErrors()) {
                Map<String, String> errorsMap = ExController.getErrors(result);
                model.mergeAttributes(errorsMap);
                return "teach/courses/add";
            } else {
                Course course = courseService.findById(id);
                Module module = moduleService.save(mapper.map(moduleDto, Module.class));
                course.getModules().add(module);
                courseService.update(course);
                model.addAttribute("coursesList", courseService.getAllListCourse(pageable));
                model.addAttribute("course", course);
                return "teach/courses/index";
            }
        }
    }
}
