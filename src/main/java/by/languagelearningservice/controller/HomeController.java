package by.languagelearningservice.controller;

import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                       @PageableDefault(sort = {"courseId"}, direction = Sort.Direction.DESC) Pageable pageable,
                       HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Page<Course> courseList = courseService.getAllListCourse(pageable);
        model.addAttribute("url", "/");
        model.addAttribute("page", courseList);
        return "index";
    }
}
