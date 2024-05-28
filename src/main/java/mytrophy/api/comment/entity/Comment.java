package mytrophy.api.comment.entity;


import io.jsonwebtoken.lang.Assert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mytrophy.api.article.entity.Article;
import mytrophy.api.common.base.BaseEntity;
import mytrophy.api.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    //추천수
    @Column(name = "cnt_up", nullable = false)
    private int cntUp = 0;

    //댓글 추천
    public void incrementCntUp(){
        this.cntUp++;
    }

    //댓글 추천 취소
    public void decrementCntUp(){
        this.cntUp = Math.max(0, this.cntUp - 1);
    }

    //setter는 불변성 보장 x -> 사용 자제해야함. 대신에 Builder 패턴 사용
    @Builder
    public Comment(String content, Member member, Article article, int cntUp) {
        Assert.notNull(content, "내용 필수 작성");    //null 이면 IllegalArgumentException

        // jpa 쓰니까 id는 세팅할 필요없음
        this.content = content;
        this.member = member;
        this.article = article;
        this.cntUp = cntUp;
    }

    //댓글 내용 업데이트
    public void updateContent(String content) {
        this.content = content;
    }
}

