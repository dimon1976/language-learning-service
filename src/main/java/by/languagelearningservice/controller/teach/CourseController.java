package by.languagelearningservice.controller.teach;


import by.languagelearningservice.controller.ExController;
import by.languagelearningservice.dto.CourseDto;
import by.languagelearningservice.dto.ModuleDto;
import by.languagelearningservice.entity.Comment;
import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.service.CommentsService;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/teach/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/learn")
    private String learn(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Set<Course> courseList = user.getCourses();
        model.addAttribute("courseList", courseList);
        return "user/learn";
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam("userId") User user,
                        @RequestParam Optional<Integer> page,
                        @RequestParam Optional<Integer> size,
                        @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.DESC, sortBy.orElse("courseId"));
        List<Course> coursesList = courseService.getTeacherListCourse(user.getUserId(), pageable);
        model.addAttribute("coursesListIndex", coursesList);
        model.addAttribute("teacher", user);
        return "/teach/courses/index";
    }

    @GetMapping("{courseId}/info")
    public String info(@PathVariable("courseId") Course course, Model model) {
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/info";
    }

    @GetMapping("{courseId}/setting")
    public String status(@PathVariable("courseId") Course course, CourseStatus status, Model model) {
        User userById = userService.getUserById(course.getTeacherId());
        course.setCourseStatus(status);
        courseService.update(course);
        model.addAttribute("course", course);
        model.addAttribute("teacher", userById);
        return "teach/courses/info";
    }

    @GetMapping("{courseId}/update")
    public String update(@PathVariable("courseId") Course course, Model model) {
        User userById = userService.getUserById(course.getTeacherId());
        Map<String, String> languages = Stream.of(Language.values()).collect(Collectors.toMap(Language::name, Language::getTranslation));
        model.addAttribute("course", course);
        model.addAttribute("languages", languages);
        model.addAttribute("teacher", userById);
        return "teach/courses/info_edit";
    }

    @PostMapping("{courseId}/update")
    public String update(@RequestParam("file") MultipartFile file, @PathVariable("courseId") Course courseByid, @ModelAttribute("course") @Valid CourseDto courseDto,
                         BindingResult result,
                         Model model) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            model.addAttribute("teacher", userService.getUserById(courseByid.getTeacherId()));
            model.addAttribute("course", courseDto);
            return "teach/courses/new";
        } else {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                courseDto.setFilename(resultFileName);
            }
            Course course = courseService.update(mapper.map(courseDto, Course.class));
            User userById = userService.getUserById(course.getTeacherId());
            Map<String, String> languages = Stream.of(Language.values()).collect(Collectors.toMap(Language::name, Language::getTranslation));
            model.addAttribute("languages", languages);
            model.addAttribute("course", course);
            model.addAttribute("teacher", userById);
            return "teach/courses/info_edit";
        }
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
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User userById = userService.getUserById(teacherId);

            if (result.hasErrors()) {
                Map<String, String> errorsMap = ExController.getErrors(result);
                model.mergeAttributes(errorsMap);
                model.addAttribute("teacher", userById);
                return "teach/courses/new";
            } else {
                Course c = courseService.save(mapper.map(courseDto, Course.class));
                userById.getCourses().add(c);
                userService.update(userById);
                List<Course> coursesList = courseService.getTeacherListCourse(teacherId, pageable);
                model.addAttribute("coursesListIndex", coursesList);
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
            model.addAttribute("coursesListIndex", coursesList);
            return "teach/courses/index";
        }
    }

    @GetMapping("{courseId}/promo")
    public String promo(@PathVariable("courseId") Course course, Model model) {
        Iterable<Comment> commentList = course.getComments();
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("course", course);
        model.addAttribute("comments", commentList);
        model.addAttribute("teacher", userById);
        return "teach/courses/promo";
    }

    @GetMapping("/{courseId}/comments")
    public String comments(@PathVariable("courseId") Course course, Model model) {
        Iterable<Comment> commentList = course.getComments();
        model.addAttribute("comments", commentList);
        model.addAttribute("course", course);
        return "/teach/courses/comments";
    }

    @GetMapping("{courseId}/syllabus")
    public String syllabus(@PathVariable("courseId") Course course, Model model) {
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("teacher", userById);
        model.addAttribute("course", course);
        return "teach/courses/syllabus";
    }

    @GetMapping("{courseId}/edit{module}")
    public String edit(@PathVariable("courseId") Course course, String module, Model model) {
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
    public String delete(@PathVariable("courseId") Course course,
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
            courseService.deleteById(course.getCourseId());
            User userById = userService.getUserById(teacherId);
            List<Course> coursesList = courseService.getTeacherListCourse(teacherId, pageable);
            model.addAttribute("teacher", userById);
            model.addAttribute("coursesListIndex", coursesList);
            return "teach/courses/index";
        }
    }


    public static void getUserSession(@PathVariable("courseId") Course course, @RequestParam String text, Model model, HttpSession httpSession, CommentsService commentsService, CourseService courseService) {
        User user = (User) httpSession.getAttribute("user");
        Comment comment = new Comment(text, user, course);
        commentsService.save(comment);
        courseService.addComment(course, comment);
        Iterable<Comment> commentList = course.getComments();
        model.addAttribute("comments", commentList);
        model.addAttribute("comment", new Comment());
        model.addAttribute("course", course);
    }
}
