package by.languagelearningservice.controller;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Lesson;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.LessonService;
import by.languagelearningservice.service.ModuleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teach/courses")
public class LessonController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{courseid}/modules/{moduleid}/lessons/add")
    public String addLesson(@PathVariable("courseid") Long courseid, @PathVariable("moduleid") Long moduleid, Model model) {
        List<Lesson> lessonList = lessonService.findByModuleId(moduleid);
        Module m = moduleService.getByModuleId(moduleid);
        Optional<Course> c = courseService.findById(courseid);
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("module", m);
        model.addAttribute("course", c);
        model.addAttribute("newLesson", new Lesson());
        return "teach/courses/modules/lessons/add";
    }

    @PostMapping("/{courseid}/modules/{moduleid}/lessons/add")
    public String addLessons(@ModelAttribute("newLesson") Lesson lesson,@PathVariable("courseid") Long courseid, @PathVariable("moduleid") Long moduleid, Model model) {
        Module m = moduleService.getByModuleId(moduleid);
        Optional<Course> c = courseService.findById(courseid);
        m.getLessons().add(lesson);
        moduleService.save(m);
        model.addAttribute("course", c);
        model.addAttribute("module", m);
        return "teach/courses/syllabus";
    }
}
