package by.languagelearningservice.entity.courses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lessonId;

    private String name;
    @Lob
    private String description;
    private Boolean activeComment;

    @ManyToOne
    private Module module;
}
