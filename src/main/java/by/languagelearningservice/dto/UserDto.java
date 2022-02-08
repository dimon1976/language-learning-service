package by.languagelearningservice.dto;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDto {

    private long userId;
    @NotBlank(message = "Пожалуйста заполните поле")
    @Email(message = "Email некорректный")
    private String email;

    @Length(min = 3, max = 64,message = "Значение меньше 3-х либо больше 64")
    private String password;

    @Transient
    @Length(message = "Подтвержденный пароль не совпадает")
    private String password2;

    @Length(min = 3, max = 64,message = "Значение меньше 3-х либо больше 64")
    private String firstname;
    @Length(min = 3, max = 64,message = "Значение меньше 3-х либо больше 64")
    private String lastname;
    private List<Course> courses;

    private String description;
    private String shortDescription;
    private Boolean teacher;

    @Enumerated(EnumType.STRING)
    private Language learning;
}
