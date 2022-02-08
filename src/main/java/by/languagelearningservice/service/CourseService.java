package by.languagelearningservice.service;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.repository.CourseRepository;
import by.languagelearningservice.repository.LessonRepository;
import by.languagelearningservice.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserService userService;

    public Course save(Course course) {
        course.setCourseStatus(CourseStatus.DRAFT);
        return courseRepository.save(course);
    }

    public Page<Course> getAllListCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<Course> getTeacherListCourse(Long userId, Pageable pageable) {
        List<Course> allCourseByUserId = courseRepository.findAllByTeacherId(userId, pageable).orElseThrow(() -> new RuntimeException(String.format("Course by status %s null", userId)));
        return allCourseByUserId;
    }

    public Course findById(Long id) {
        log.info(String.format("Request Course findById %s exist", id));
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Course %s not found", id)));
        return course;
    }

    public Course update(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getTeacherListCourseByStatus(CourseStatus status, Pageable pageable, Long userId) {
        if (status.toString().equals("ALL")) {
            return courseRepository.findAllByTeacherId(userId, pageable).get();
        } else {
            List<Course> byCourseTeacherId = courseRepository.findAllByTeacherIdAndCourseStatus(userId, status, pageable).orElseThrow(() -> new RuntimeException(String.format("Course by status %s null", status)));
            return byCourseTeacherId;
        }
    }

    @Transactional
    public Boolean deleteById(Long courseId) {
        log.info("Request delete {}", courseId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course %s not found", courseId)));
        for (Module m : course.getModules()) {
            moduleService.deleteById(m.getModuleId());
        }
        courseRepository.deleteById(courseId);
        return true;
    }
}
