package by.languagelearningservice.dto;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.CourseStatus;
import by.languagelearningservice.entity.courses.Level;
import by.languagelearningservice.entity.courses.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CourseDto {

    private long courseId;
    @Length(min = 3, max = 64,message = "Значение меньше 3-х либо больше 64")
    private String nameCourse;
    @Length(min = 3,message = "Значение меньше 3-х")
    private String description;
    @Length(min = 3, max = 1024,message = "Значение меньше 3-х либо больше 1024")
    private String shortDescription;
    private Long teacherId;
    private String filename;
    private Boolean published;
    private String requirements;
    private String TheTargetAudience;
    private LocalDateTime dateCreating;
    private LocalDateTime dateLaunch;
    private User users;
    private List<Module> modules;
    private CourseStatus courseStatus;
    private Language language;
    private Level level;
}
