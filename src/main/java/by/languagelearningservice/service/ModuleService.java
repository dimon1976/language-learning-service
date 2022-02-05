package by.languagelearningservice.service;

import by.languagelearningservice.entity.courses.Module;
import by.languagelearningservice.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module getByModuleId(long id){
        Optional<Module> module = moduleRepository.findById(id);
        if(module.isPresent()){
            return module.get();
        }
        return null;
    }

    public void save(Module module){
        moduleRepository.save(module);
    }
}
