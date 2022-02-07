package by.languagelearningservice.controller;

import by.languagelearningservice.dto.LessonDto;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
        Course c = courseService.findById(courseid);
        model.addAttribute("lessonList", lessonList);
        model.addAttribute("module", m);
        model.addAttribute("course", c);
        model.addAttribute("newLesson", new LessonDto());
        return "teach/courses/modules/lessons/add";
    }

    @PostMapping("/{courseid}/modules/{moduleid}/lessons/add")
    public String addLessons(@PathVariable("courseid") Long courseid,
                             @PathVariable("moduleid") Long moduleid,
                             @ModelAttribute("newLesson") @Valid LessonDto lessonDto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            return "teach/courses/modules/lessons/add";
        } else {
            Lesson lesson = lessonService.save(mapper.map(lessonDto, Lesson.class));
            Module m = moduleService.getByModuleId(moduleid);
            Course c = courseService.findById(courseid);
            m.getLessons().add(lesson);
            moduleService.save(m);
            model.addAttribute("course", c);
            model.addAttribute("module", m);
            return "teach/courses/syllabus";
        }
    }
}
