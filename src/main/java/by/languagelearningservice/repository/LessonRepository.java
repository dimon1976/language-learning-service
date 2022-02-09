package by.languagelearningservice.repository;

import by.languagelearningservice.entity.courses.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
}
