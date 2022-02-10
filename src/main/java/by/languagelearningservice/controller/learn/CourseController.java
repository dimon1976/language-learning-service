package by.languagelearningservice.controller.learn;

import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Set;

public class CourseController {

    @GetMapping("/learn")
    private String learn(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Set<Course> courseList = user.getCourses();
        model.addAttribute("courseList",courseList);
        return "user/learn";
    }
}
