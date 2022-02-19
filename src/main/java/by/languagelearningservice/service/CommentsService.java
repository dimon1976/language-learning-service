package by.languagelearningservice.service;


import by.languagelearningservice.entity.Comment;
import by.languagelearningservice.repository.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;


    public Comment save(Comment comment) {
        log.info(String.format("Request save comment id %s ", comment.getId()));
        commentsRepository.save(comment);
        return comment;
    }
}
