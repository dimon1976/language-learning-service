package by.languagelearningservice.service;


import by.languagelearningservice.entity.courses.Lesson;
import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.repository.LessonRepository;
import by.languagelearningservice.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public List<Lesson> findByModuleId(long id) {
        Optional<Module> module = moduleRepository.findById(id);
        if (module.isPresent()) {
            return module.get().getLessons();
        }
        return null;
    }
}
