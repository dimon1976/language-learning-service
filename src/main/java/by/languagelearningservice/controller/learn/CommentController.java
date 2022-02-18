package by.languagelearningservice.controller.learn;

import by.languagelearningservice.controller.teach.CourseController;
import by.languagelearningservice.entity.Comment;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.service.CommentsService;
import by.languagelearningservice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/learn/course")
public class CommentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/{courseId}/comments")
    public String comments(@PathVariable("courseId") Course course, Model model) {
        Iterable<Comment> commentList = course.getComments();
        model.addAttribute("comments", commentList);
        model.addAttribute("comment", new Comment());
        model.addAttribute("course", course);
        return "/learn/course/comments";
    }

    @PostMapping("/{courseId}/comments/add")
    public String comments(@PathVariable("courseId") Course course, @RequestParam String text, Model model, HttpSession httpSession) {
        {
            CourseController.getUserSession(course, text, model, httpSession, commentsService, courseService);
            return "/learn/course/comments";
        }
    }

}
