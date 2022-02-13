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

@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDto {

    private long userId;
    @NotBlank(message = "Пожалуйста заполните поле")
    @Email(message = "Email некорректный")
    private String email;
    private String filename;

    @NotBlank(message = "поле не может быть пустым")
    @Length(min = 3, max = 64, message = "Значение меньше 3-х либо больше 64")
    private String password;

    @Transient
    private String password2;

    @Length(min = 3, max = 64, message = "Значение меньше 3-х либо больше 64")
    private String firstname;
    @Length(min = 3, max = 64, message = "Значение меньше 3-х либо больше 64")
    private String lastname;
    private List<Course> courses;

    private String description;
    private String shortDescription;
    private Boolean teacher;

    @Enumerated(EnumType.STRING)
    private Language learning;
    @Enumerated(EnumType.STRING)
    private Language nativeLang;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Language getNativeLang() {
        return nativeLang;
    }

    public void setNativeLang(Language nativeLang) {
        this.nativeLang = nativeLang;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Boolean getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        this.teacher = teacher;
    }

    public Language getLearning() {
        return learning;
    }

    public void setLearning(Language learning) {
        this.learning = learning;
    }
}
