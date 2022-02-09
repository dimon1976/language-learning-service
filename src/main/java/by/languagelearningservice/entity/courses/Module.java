package by.languagelearningservice.entity.courses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Course courses;
}
