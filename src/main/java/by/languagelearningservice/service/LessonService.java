package by.languagelearningservice.service;


import by.languagelearningservice.entity.courses.Lesson;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.repository.LessonRepository;
import by.languagelearningservice.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public Collection<Lesson> findByModuleId(long id) {
        log.info(String.format("Get Lesson list by %s module", id));
        Optional<Module> module = moduleRepository.findById(id);
        if (module.isPresent()) {
            return module.get().getLessons();
        }
        return null;
    }

    public Lesson save(Lesson lesson) {
        log.info(String.format("Lesson %s update", lesson));
        return lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteById(Long lessonId) {
        log.info(String.format("Lesson %s delete",lessonId));
        lessonRepository.deleteById(lessonId);
    }
}
