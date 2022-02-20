package by.languagelearningservice.service;

import by.languagelearningservice.entity.Comment;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course save(Course course) {
        log.info(String.format("Request save Course Id %s ", course.getCourseId()));
        course.setCourseStatus(CourseStatus.DRAFT);
        return courseRepository.save(course);
    }

    public Page<Course> getAllListCourse(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<Course> getTeacherListCourse(Long userId, Pageable pageable) {
        log.info(String.format("Request Teacher list Course by id %s", userId));
        return courseRepository.findAllByTeacherId(userId, pageable).orElseThrow(() -> new RuntimeException(String.format("Course by status %s null", userId)));
    }

    public Course findById(Long id) {
        log.info(String.format("Request Course findById %s exist", id));
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Course %s not found", id)));
    }

    @Modifying
    @Transactional
    public Course update(Course courseUpdate) {
        log.info(String.format("Request  update Course %s", courseUpdate));
        Course course = courseRepository.findById(courseUpdate.getCourseId()).get();
        course.setNameCourse(courseUpdate.getNameCourse());
        course.setTheTargetAudience(courseUpdate.getTheTargetAudience());
        course.setDescription(courseUpdate.getDescription());
        course.setShortDescription(courseUpdate.getShortDescription());
        course.setDateLaunch(courseUpdate.getDateLaunch());
        course.setCourseStatus(courseUpdate.getCourseStatus());
        course.setLanguage(courseUpdate.getLanguage());
        course.setLevel(courseUpdate.getLevel());
        course.setFilename(courseUpdate.getFilename());
        course.setRequirements(courseUpdate.getRequirements());
        return courseRepository.save(course);
    }

    public List<Course> getTeacherListCourseByStatus(CourseStatus status, Pageable pageable, Long userId) {
        log.info(String.format("Request list by status Course %s ", status));
        if (status.toString().equals("ALL")) {
            return courseRepository.findAllByTeacherId(userId, pageable).get();
        } else {
            return courseRepository.findAllByTeacherIdAndCourseStatus(userId, status, pageable).orElseThrow(() -> new RuntimeException(String.format("Course by status %s null", status)));
        }
    }

    @Transactional
    public void deleteById(Long courseId) {
        log.info("Request delete {}", courseId);
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException(String.format("Course %s not found", courseId)));
        courseRepository.deleteAllByCourseId(courseId);
    }

    public User findTeacherCourse(Course course) {
        log.info("Request find Teachers course id $s", course.getCourseId());
        for (User u : course.getUser()) {
            if (u.getTeacher()) {
                return u;
            }
        }
        return null;
    }

    public Course addComment(Course course, Comment comment) {
        log.info(String.format("Request Add comment %s in course %s", comment, course));
        course.getComments().add(comment);
        return courseRepository.save(course);
    }

    public Page<Course> findAllByNameCourseLike(String search, Pageable pageable) {
        log.info(String.format("Request find course by search %s", search));
        return courseRepository.findAllByNameCourseContains(search, pageable);
    }
}
