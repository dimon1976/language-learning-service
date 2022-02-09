package by.languagelearningservice.entity;

import by.languagelearningservice.entity.courses.Course;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    @Column(length = 2048)
    private String description;

    @Column(length = 1028)
    private String shortDescription;
    @JoinTable(name = "users_courses",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ManyToMany(fetch = FetchType.EAGER,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Course> courses;

    @Enumerated(EnumType.STRING)
    private Language learning;

    @Column(updatable = false)
    private LocalDateTime dateCreating;

    private Boolean teacher;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Language getLearning() {
        return learning;
    }

    public void setLearning(Language learning) {
        this.learning = learning;
    }

    public LocalDateTime getDateCreating() {
        return dateCreating;
    }

    public void setDateCreating(LocalDateTime dateCreating) {
        this.dateCreating = dateCreating;
    }

    public Boolean getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        this.teacher = teacher;
    }
}
