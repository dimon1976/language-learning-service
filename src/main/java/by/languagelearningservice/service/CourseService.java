package by.languagelearningservice.service;

import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseService {

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

    public List<Course> getListCourseByStatus(CourseStatus status, Pageable pageable) {
        if (status.toString().equals("ALL")) {
            return courseRepository.findAllBy(pageable).get();
        } else {
            List<Course> byCourseStatus = courseRepository.findByCourseStatus(status, pageable).orElseThrow(() -> new RuntimeException(String.format("Course by status %s null", status)));
            return byCourseStatus;
        }
    }

    public Course findById(Long id) {
        log.info(String.format("Request Course findById %s exist", id));
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Course %s not found", id)));
        return course;
    }

    public Course update(Course course) {
        return courseRepository.save(course);
    }

}
