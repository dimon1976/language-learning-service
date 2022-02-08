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
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String description;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 130")
    private String shortDescription;
    private Long teacherId;

    private Boolean published;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String requirements;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String TheTargetAudience;
    private LocalDateTime dateCreating;
    private LocalDateTime dateLaunch;
    private User users;
    private List<Module> modules;
    private CourseStatus courseStatus;
    private Language language;
    private Level level;
}
