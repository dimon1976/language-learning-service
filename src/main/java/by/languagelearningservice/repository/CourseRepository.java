package by.languagelearningservice.repository;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.CourseStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<List<Course>> findByCourseStatus(CourseStatus status, Pageable pageable);

    Optional<List<Course>> findAllByTeacherId(Long teacherId, Pageable pageable);

    void deleteAllByCourseId(Long courseId);

    Optional<List<Course>> findAllByTeacherIdAndCourseStatus(Long teacherId, CourseStatus status, Pageable pageable);


}
