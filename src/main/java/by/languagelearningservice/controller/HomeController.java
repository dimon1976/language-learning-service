package by.languagelearningservice.controller;

import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String home(Model model,
                       @RequestParam Optional<Integer> page,
                       @RequestParam Optional<Integer> size,
                       @RequestParam Optional<String> sortBy,
                       HttpSession httpSession) {
        Pageable pageable = PageRequest.of(page.orElse(0),
                size.orElse(5),
                Sort.Direction.DESC,
                sortBy.orElse("courseId"));
        User user = (User) httpSession.getAttribute("user");
        Page<Course> courseList = courseService.getAllListCourse(pageable);
        model.addAttribute("courseList", courseList);
        return "index";
    }
}
