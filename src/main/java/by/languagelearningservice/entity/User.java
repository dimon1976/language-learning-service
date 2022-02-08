package by.languagelearningservice.entity;

import by.languagelearningservice.entity.courses.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
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
    joinColumns = @JoinColumn(name = "user_id",
    referencedColumnName = "userId"),
    inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "courseId"))
    @ManyToMany
    private Collection<Course> courses;

    @Enumerated(EnumType.STRING)
    private Language learning;

    @Column(updatable = false)
    private LocalDateTime dateCreating;

    private Boolean teacher;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }
}
