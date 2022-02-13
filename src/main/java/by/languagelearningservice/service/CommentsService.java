package by.languagelearningservice.service;


import by.languagelearningservice.entity.Comment;
import by.languagelearningservice.repository.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;


    public Comment save(Comment comment) {
        commentsRepository.save(comment);
        return comment;
    }

    public List<Comment> findAllByCourseId() {
        return commentsRepository.findAll();
    }
}
