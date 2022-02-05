package by.languagelearningservice.repository;

import by.languagelearningservice.entity.courses.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {

}
