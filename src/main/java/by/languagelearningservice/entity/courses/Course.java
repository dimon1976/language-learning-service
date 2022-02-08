package by.languagelearningservice.entity.courses;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;

    private String nameCourse;

    @Lob
    private String description;

    @Column(length = 2048)
    private String shortDescription;
    private String requirements;
    private String TheTargetAudience;

    @Column(updatable = false)
    private LocalDateTime dateCreating;
    private LocalDateTime dateLaunch;

    private Long teacherId;


    @JoinTable(name = "users_courses",
            joinColumns = @JoinColumn(name = "courses_course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_user_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH,targetEntity = Course.class)
    private List<User> user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Level level;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }
}
