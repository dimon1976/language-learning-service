package by.languagelearningservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CommentDto {

    private long id;

    private long postId;

    private long rating;

    private String creatorUsername;

    private String description;
}
