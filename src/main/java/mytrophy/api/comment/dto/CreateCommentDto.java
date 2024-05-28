package mytrophy.api.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentDto {

    private Long id;
    private String content;
    private int cntUp;

    public CreateCommentDto(Long id, String content, int cntUp) {
        this.id = id;
        this.content = content;
        this.cntUp = cntUp;
    }
}
