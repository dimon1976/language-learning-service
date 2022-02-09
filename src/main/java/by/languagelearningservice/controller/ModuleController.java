package by.languagelearningservice.controller;


import by.languagelearningservice.dto.ModuleDto;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.ModuleService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/teach/courses")
public class ModuleController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/{courseId}/modules/add")
    public String add(@ModelAttribute("newModule") @Valid ModuleDto moduleDto,
                      BindingResult result,
                      Model model,
                      @PathVariable("courseId") Long courseId,
                      @RequestParam Optional<Integer> page,
                      @RequestParam Optional<Integer> size,
                      @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            if (result.hasErrors()) {
                Map<String, String> errorsMap = ExController.getErrors(result);
                model.mergeAttributes(errorsMap);
                return "teach/courses/modules/add";
            } else {
                Course course = courseService.findById(courseId);
                Module module = moduleService.save(mapper.map(moduleDto, Module.class));
                course.getModules().add(module);
                courseService.update(course);
                User userById = userService.getUserById(course.getTeacherId());
                model.addAttribute("coursesList",
                        courseService.getTeacherListCourse(course.getTeacherId(), pageable));
                model.addAttribute("teacher", userById);
                model.addAttribute("course", course);
                return "teach/courses/edit";
            }
        }
    }
}
