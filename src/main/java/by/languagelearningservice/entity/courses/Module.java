package by.languagelearningservice.entity.courses;


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
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long moduleId;

    private String name;
    @Column(length = 2048)
    private String description;
    private LocalDateTime dateLaunch;

    @OneToMany
    private List<Lesson> lessons;

    @ManyToOne
    private Course courses;

}
