package by.languagelearningservice.dto;

import by.languagelearningservice.entity.courses.Module;
import org.hibernate.validator.constraints.Length;

public class LessonDto {

    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String name;
    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String description;

    private Boolean activeComment;

    private Module module;
}
