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
        log.info(String.format("Request module by id %s ", id));
        Optional<Module> module = moduleRepository.findById(id);
        return module.orElse(null);
    }

    public Module save(Module module){
        log.info(String.format("Request Module %s save", module.getModuleId()));
       return moduleRepository.save(module);
    }
}
