package by.languagelearningservice.controller.learn;

import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/learn")
public class LearnCourseController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @GetMapping
    private String learn(Model model, HttpSession httpSession,
                         @RequestParam Optional<Integer> page,
                         @RequestParam Optional<Integer> size,
                         @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/index";
        }
    }

    @GetMapping("/courses/info")
    private String courses(Model model, HttpSession httpSession,
                           @RequestParam Optional<Integer> page,
                           @RequestParam Optional<Integer> size,
                           @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/courses";
        }
    }


    @GetMapping("/courses/favorites")
    private String info(Model model, HttpSession httpSession,
                        @RequestParam Optional<Integer> page,
                        @RequestParam Optional<Integer> size,
                        @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/favorites";
        }
    }

    @GetMapping("/classes")
    private String classes(Model model, HttpSession httpSession,
                           @RequestParam Optional<Integer> page,
                           @RequestParam Optional<Integer> size,
                           @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/classes";
        }
    }

    @GetMapping("/notification")
    private String notification(Model model, HttpSession httpSession,
                                @RequestParam Optional<Integer> page,
                                @RequestParam Optional<Integer> size,
                                @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/notification";
        }
    }

    @GetMapping("/courses/wishlist")
    private String wishlist(Model model, HttpSession httpSession,
                            @RequestParam Optional<Integer> page,
                            @RequestParam Optional<Integer> size,
                            @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/wishlist";
        }
    }

    @GetMapping("/courses/archive")
    private String archives(Model model, HttpSession httpSession,
                            @RequestParam Optional<Integer> page,
                            @RequestParam Optional<Integer> size,
                            @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        {
            User user = (User) httpSession.getAttribute("user");
            Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
            model.addAttribute("courseList", courseList);
            return "/learn/archive";
        }
    }


    @GetMapping("/course/{courseId}/enroll")
    public String enrollCourse(@PathVariable("courseId") Course course, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        userService.addLearnCourse(user.getUserId(), course);
        model.addAttribute("course", course);
        return "/learn/course/syllabus";
    }


    @GetMapping("/course/{courseId}/leave")
    public String leaveCourse(@PathVariable("courseId") Course course, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        userService.removeCourse(user.getUserId(), course);
        Set<Course> courseList = userService.getUserById(user.getUserId()).getCourses();
        model.addAttribute("courseList", courseList);
        return "/learn/index";
    }

    @GetMapping("/course/{courseId}/info")
    public String info(@PathVariable("courseId") Course course, Model model, HttpSession httpSession) {
        User teacher = courseService.findTeacherCourse(course);
        model.addAttribute("teacher", teacher);
        model.addAttribute("course", course);
        return "/learn/course/info";
    }

    @GetMapping("/course/{courseId}/news")
    public String news(@PathVariable("courseId") Course course, Model model, HttpSession httpSession) {
        User teacher = courseService.findTeacherCourse(course);
        model.addAttribute("teacher", teacher);
        model.addAttribute("course", course);
        return "/learn/course/news";
    }


    @GetMapping("/course/{courseId}/reviews")
    public String reviews(@PathVariable("courseId") Course course, Model model, HttpSession httpSession) {
        User teacher = courseService.findTeacherCourse(course);
        model.addAttribute("teacher", teacher);
        model.addAttribute("course", course);
        return "/learn/course/reviews";
    }

}
