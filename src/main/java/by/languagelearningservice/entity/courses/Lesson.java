package by.languagelearningservice.entity.courses;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name!=null){
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description!=null){
            this.description = description;
        }
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public Boolean getActiveComment() {
        return activeComment;
    }

    public void setActiveComment(Boolean activeComment) {
        if(activeComment!=null){
            this.activeComment = activeComment;
        }
    }
}
