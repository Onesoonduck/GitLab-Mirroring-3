package mytrophy.api.article.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mytrophy.api.article.enumentity.Header;
import mytrophy.api.member.entity.Member;

@Data
@NoArgsConstructor
public class ArticleRequest {
    private Header header;
    private String name;
    private String content;
    private String imagePath;
//    private Member member;

    @Builder
    public ArticleRequest(Header header, String name, String content) {
        this.header = header;
        this.name = name;
        this.content = content;
    }
}
