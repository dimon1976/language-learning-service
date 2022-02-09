package by.languagelearningservice.dto;

import by.languagelearningservice.entity.courses.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class LessonDto {


    private long lessonId;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String name;
//    @Length(min = 3,message = "Значение меньше 3-х")
//    @NotBlank
    private String description;

    private Boolean activeComment;

    private Module module;
}
