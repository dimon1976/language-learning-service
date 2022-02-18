package by.languagelearningservice.entity.courses;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long moduleId;

    private String name;
    @Column(length = 2048)
    private String description;
    private LocalDateTime dateLaunch;

    @JoinTable(name = "module_lessons",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    @OneToMany(orphanRemoval = true)
    private Collection<Lesson> lessons;



    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
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

    public LocalDateTime getDateLaunch() {
        return dateLaunch;
    }

    public void setDateLaunch(LocalDateTime dateLaunch) {
        if (dateLaunch != null) {
            this.dateLaunch = dateLaunch;
        }
    }

    public Collection<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Collection<Lesson> lessons) {
        this.lessons = lessons;
    }
}
