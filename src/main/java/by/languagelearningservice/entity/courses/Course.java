package by.languagelearningservice.entity.courses;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private User users;

    @OneToMany
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
