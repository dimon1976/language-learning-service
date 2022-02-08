package by.languagelearningservice.dto;

import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.entity.courses.Lesson;
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
public class ModuleDto {

    private long moduleId;
    @Length(min = 3, max = 64,message = "Значение меньше 3-х либо больше 64")
    private String name;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String description;

    private LocalDateTime dateLaunch;
    private List<Lesson> lessons;
    private Course courses;
}
