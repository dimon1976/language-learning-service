package by.languagelearningservice.repository;

import by.languagelearningservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentsRepository extends JpaRepository<Comment, Long> {


}
