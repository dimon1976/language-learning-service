package by.languagelearningservice.controller;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String home(Model model,
                       @PageableDefault(sort = {"courseId"}, direction = Sort.Direction.DESC,size = 4) Pageable pageable) {
        Page<Course> courseList = courseService.getAllListCourse(pageable);
        model.addAttribute("url", "/");
        model.addAttribute("page", courseList);
        return "index";
    }

    @GetMapping("search")
    public String search(Model model,
                         @RequestParam(value = "search", required = false) String search,
                         @PageableDefault(sort = {"courseId"}, direction = Sort.Direction.DESC,size = 4) Pageable pageable) {
        Page<Course> courseList = courseService.findAllByNameCourseLike(search, pageable);
        model.addAttribute("url", "/");
        model.addAttribute("page", courseList);
        return "index";
    }
}
