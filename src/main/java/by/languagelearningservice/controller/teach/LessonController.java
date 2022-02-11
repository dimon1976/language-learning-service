package by.languagelearningservice.controller.teach;

import by.languagelearningservice.controller.ExController;
import by.languagelearningservice.dto.LessonDto;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Lesson;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.LessonService;
import by.languagelearningservice.service.ModuleService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/teach/courses")
public class LessonController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ModelMapper mapper;



    @GetMapping("/{courseId}/modules/{moduleId}/lessons/add")
    public String addLesson(@PathVariable("courseId") Long courseId, @PathVariable("moduleId") Long moduleId, Model model) {
        Collection<Lesson> lessonList = lessonService.findByModuleId(moduleId);
        Module module = moduleService.getByModuleId(moduleId);
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        model.addAttribute("teacher", userById);
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("module", module);
        model.addAttribute("course", course);
        model.addAttribute("newLesson", new LessonDto());
        return "teach/courses/modules/lessons/add";
    }

    @PostMapping("/{courseId}/modules/{moduleId}/lessons/add")
    public String addLessons(@PathVariable("courseId") Long courseId,
                             @PathVariable("moduleId") Long moduleId,
                             @ModelAttribute("newLesson") @Valid LessonDto lessonDto,
                             BindingResult result,
                             Model model) {
        Module module = moduleService.getByModuleId(moduleId);
        Course course = courseService.findById(courseId);
        User userById = userService.getUserById(course.getTeacherId());
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            model.addAttribute("teacher", userById);
            model.addAttribute("course", course);
            model.addAttribute("module", module);
            return "teach/courses/modules/lessons/add";
        } else {
            Lesson lesson = lessonService.save(mapper.map(lessonDto, Lesson.class));
            module.getLessons().add(lesson);
            moduleService.save(module);
            User userById1 = userService.getUserById(course.getTeacherId());
            model.addAttribute("teacher", userById1);
            model.addAttribute("course", course);
            model.addAttribute("module", module);
            return "teach/courses/edit";
        }
    }
}
