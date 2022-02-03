package by.languagelearningservice.entity.courses;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.Teacher;
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
    private long id;

    private String description;
    private String shortDescription;
    private Boolean published;
    private String requirements;
    private String TheTargetAudience;

    @Column(updatable = false)
    private LocalDateTime dateCreating;
    private LocalDateTime dateLaunch;


    @ManyToOne
    private Teacher teacher;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<User> users;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Module> modules;

    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Enumerated(EnumType.STRING)
    private Language language;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }
}
