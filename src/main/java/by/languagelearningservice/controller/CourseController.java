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
                        @RequestParam("userId") Long userId,
                        @RequestParam Optional<Integer> page,
                        @RequestParam Optional<Integer> size,
                        @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("courseId"));
        User userById = userService.getUserById(userId);
        model.addAttribute("coursesList", courseService.getTeacherListCourse(userId, pageable));
        model.addAttribute("teacher", userById);
        return "/teach/courses/index";
    }

    @GetMapping("{courseId}/info")
    public String info(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/info";
    }

    @GetMapping("{courseId}/setting")
    public String status(@PathVariable("courseId") Long courseId, CourseStatus status, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        course.setCourseStatus(status);
        courseService.update(course);
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/info";
    }


    @GetMapping("/new")
    public String newCourse(Model model,
                            Long teacherId) {
        User userById = userService.getUserById(teacherId);
        model.addAttribute("teacher", userById);
        model.addAttribute("courseNull", new Course());
        return "teach/courses/new";
    }

    @PostMapping("/new{teacherId}")
    public String newCourse(@ModelAttribute("courseNull") @Valid CourseDto courseDto,
                            BindingResult result,
                            Model model,
                            Long teacherId,
                            @RequestParam Optional<Integer> page,
                            @RequestParam Optional<Integer> size,
                            @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("courseId"));
        {
            if (result.hasErrors()) {
                Map<String, String> errorsMap = ExController.getErrors(result);
                model.mergeAttributes(errorsMap);
                return "teach/courses/new";
            } else {
                Course c = courseService.save(mapper.map(courseDto, Course.class));
                User userById = userService.getUserById(teacherId);
                userById.getCourses().add(c);
                userService.update(userById);
                model.addAttribute("coursesList", courseService.getTeacherListCourse(teacherId, pageable));
                model.addAttribute("teacher", userById);
            }
            return "teach/courses/index";
        }
    }

    @GetMapping("/filter{teacherId}")
    public String filterByStatus(CourseStatus status, Model model,
                                 @PathVariable("teacherId") Long teacherId,
                                 @RequestParam Optional<Integer> page,
                                 @RequestParam Optional<Integer> size,
                                 @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            List<Course> coursesList = courseService.getTeacherListCourseByStatus(status, pageable, teacherId);
            User userById = userService.getUserById(teacherId);
            model.addAttribute("teacher", userById);
            model.addAttribute("coursesList", coursesList);
            return "teach/courses/index";
        }
    }

    @GetMapping("{courseId}/promo")
    public String promo(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/promo";
    }

    @GetMapping("{courseId}/syllabus")
    public String syllabus(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("teacher", userById);
        model.addAttribute("course", course);
        return "teach/courses/syllabus";
    }

    @GetMapping("{courseId}/edit{module}")
    public String edit(@PathVariable("courseId") Long courseId, String module, Model model) {
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        if (module != null && module.equals("add")) {
            model.addAttribute("newModule", new ModuleDto());
            model.addAttribute("course", course);
            model.addAttribute("teacher", userById);
            return "teach/courses/modules/add";
        }
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/edit";
    }

    @GetMapping("{courseId}/delete{teacherId}")
    public String delete(@PathVariable("courseId") Long courseId,
                         Long teacherId,
                         @RequestParam Optional<Integer> page,
                         @RequestParam Optional<Integer> size,
                         @RequestParam Optional<String> sortBy,
                         Model model) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            courseService.deleteById(courseId);
            User userById = userService.getUserById(teacherId);
            List<Course> coursesList = courseService.getTeacherListCourse(teacherId, pageable);
            model.addAttribute("teacher", userById);
            model.addAttribute("coursesList", coursesList);
            return "teach/courses/index";
        }
    }
}
