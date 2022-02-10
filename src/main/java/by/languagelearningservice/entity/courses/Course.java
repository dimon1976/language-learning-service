package by.languagelearningservice.entity.courses;

import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;
    @Column(length = 1024)
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
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<User> user;

    @JoinTable(name = "course_modules",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    @ManyToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Module> modules;

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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        if (nameCourse != null) {
            this.nameCourse = nameCourse;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        if (shortDescription != null) {
            this.shortDescription = shortDescription;
        }
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        if (shortDescription != null) {
            this.requirements = requirements;
        }
    }

    public String getTheTargetAudience() {
        return TheTargetAudience;
    }

    public void setTheTargetAudience(String theTargetAudience) {
        if (theTargetAudience != null) {
            TheTargetAudience = theTargetAudience;
        }
    }

    public LocalDateTime getDateCreating() {
        return dateCreating;
    }

    public void setDateCreating(LocalDateTime dateCreating) {
            this.dateCreating = dateCreating;
    }

    public LocalDateTime getDateLaunch() {
        return dateLaunch;
    }

    public void setDateLaunch(LocalDateTime dateLaunch) {
        if (dateLaunch != null) {
            this.dateLaunch = dateLaunch;
        }
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
            this.teacherId = teacherId;
    }

    public Collection<User> getUser() {
        return user;
    }

    public void setUser(Collection<User> user) {
        this.user = user;
    }

    public Collection<Module> getModules() {
        return modules;
    }

    public void setModules(Collection<Module> modules) {
        this.modules = modules;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        if (courseStatus != null) {
            this.courseStatus = courseStatus;
        }

    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        if (language != null) {
            this.language = language;
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
        }

    }
}
