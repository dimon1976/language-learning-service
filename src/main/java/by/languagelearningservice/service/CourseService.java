package by.languagelearningservice.service;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course save(Course course) {
        course.setCourseStatus(CourseStatus.DRAFT);
        return courseRepository.save(course);
    }

    public List<Course> getAllListCourse() {
        return courseRepository.findAll();
    }

    public Optional<List<Course>> getListCourseByStatus(CourseStatus status) {
        if (status.toString().equals("ALL")) {
            return courseRepository.findAllBy();
        } else {
            Optional<List<Course>> byCourseStatus = courseRepository.findByCourseStatus(status);
            return byCourseStatus;
        }
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
}
