package by.languagelearningservice.dto.userdto;

import by.languagelearningservice.entity.Language;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDto {

    @NotBlank(message = "Пожалуйста заполните поле")
    @Email(message = "Email некорректный")
    private String email;

    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String password;

    @Transient
    @Length(message = "Подтвержденный пароль не совпадает")
    private String password2;

    @Length(min = 3, max = 255,message = "Значение меньше 3-х либо больше 255")
    private String name;

    @Enumerated(EnumType.STRING)
    private Language learning;
}
