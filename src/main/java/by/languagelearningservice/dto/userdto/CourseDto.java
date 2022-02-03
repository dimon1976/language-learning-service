package by.languagelearningservice.dto.userdto;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.entity.courses.Level;
import by.languagelearningservice.entity.courses.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CourseDto {

    private String nameCourse;
    private String description;
    private String shortDescription;
    private Boolean published;
    private String requirements;
    private String TheTargetAudience;
    private LocalDateTime dateCreating;
    private LocalDateTime dateLaunch;
    private User teacher;
    private List<User> users;
    private List<Module> modules;
    private CourseStatus courseStatus;
    private Language language;
    private Level level;
}
