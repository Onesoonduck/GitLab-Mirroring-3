package mytrophy.api.article.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import mytrophy.api.article.enumentity.Header;

@Data
public class ArticleRequest {
    private Header header;
    private String name;
    private String content;
    private String imagePath;

    public ArticleRequest() {
    }

    @Builder
    public ArticleRequest(Header header, String name, String content) {
        this.header = header;
        this.name = name;
        this.content = content;
    }
}
