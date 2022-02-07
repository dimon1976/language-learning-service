package by.languagelearningservice.controller;


import by.languagelearningservice.dto.CourseDto;
import by.languagelearningservice.dto.ModuleDto;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/teach/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public String index(Model model,
                        @RequestParam Optional<Integer> page,
                        @RequestParam Optional<Integer> size,
                        @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("id"));
        Page<Course> coursesList = courseService.getAllListCourse(pageable);
        model.addAttribute("coursesList", coursesList);
        return "/teach/courses/index";
    }

    @GetMapping("{id}/info")
    public String info(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/info";
    }

    @GetMapping("{id}/setting")
    public String status(@PathVariable("id") Long courseId, CourseStatus status, Model model) {
        Course course = courseService.findById(courseId);
        course.setCourseStatus(status);
        courseService.update(course);
        model.addAttribute("course", course);
        return "teach/courses/info";
    }


    @GetMapping("/new")
    public String newCourse(Model model) {
        model.addAttribute("courseNull", new Course());
        return "teach/courses/new";
    }

    @PostMapping("/new{teacherId}")
    public String newCourse(@ModelAttribute("courseNull") @Valid CourseDto courseDto, BindingResult result, Model model, Long teacherId,
                            @RequestParam Optional<Integer> page,
                            @RequestParam Optional<Integer> size,
                            @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("id"));
        {
            if (result.hasErrors()) {
                Map<String, String> errorsMap = ExController.getErrors(result);
                model.mergeAttributes(errorsMap);
                return "teach/courses/new";
            } else {
                Course c = courseService.save(mapper.map(courseDto, Course.class));
                User u = userService.getUserById(teacherId);
                u.getCourses().add(c);
                userService.update(u);
                model.addAttribute("coursesList", courseService.getAllListCourse(pageable));
                model.addAttribute("user", u);
            }
            return "teach/courses/index";
        }
    }

    @GetMapping("/filter")
    public String filterByStatus(CourseStatus status, Model model,
                                 @RequestParam Optional<Integer> page,
                                 @RequestParam Optional<Integer> size,
                                 @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("id"));
        {
            List<Course> coursesList = courseService.getListCourseByStatus(status, pageable);
            model.addAttribute("coursesList", coursesList);
            return "teach/courses/index";
        }
    }

    @GetMapping("/{id}/promo")
    public String promo(@PathVariable ("id") Long courseId, Model model){
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/promo";
    }

    @GetMapping("/{id}/syllabus")
    public String syllabus(@PathVariable("id") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        return "teach/courses/syllabus";
    }

    @GetMapping("/{id}/edit{module}")
    public String edit(@PathVariable("id") Long courseId, String module, Model model) {
        Course course = courseService.findById(courseId);
        if (module != null && module.equals("add")) {
            model.addAttribute("newModule", new ModuleDto());
            model.addAttribute("course", course);
            return "teach/courses/modules/add";
        }
        model.addAttribute("course", course);
        return "teach/courses/edit";
    }
}
