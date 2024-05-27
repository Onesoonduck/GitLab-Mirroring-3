package mytrophy.api.article.service;

import mytrophy.api.article.dto.ArticleRequest;
import mytrophy.api.article.entity.Article;
import mytrophy.api.article.enumentity.Header;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    // 게시글 생성
    Article createArticle(ArticleRequest articleRequest, List<MultipartFile> files) throws IOException;

    // 게시글 리스트 조회
    List<Article> findAll();

    // 해당 게시글 조회
    Article findById(Long id);

    // 말머리 별 게시글 리스트 조회
    List<Article> findAllByHeader(Header header);

    // 말머리 별 해당 게시글 조회
    Article findByIdAndHeader(Long id, Header header);

    // 게시글 수정
    Article updateArticle(Long id, ArticleRequest articleRequest);

    // 게시글 삭제
    void deleteArticle(Long id);

    // 좋아요 증가
    void upCntUp(Long id);

    // 좋아요 감소
    void CntUpDown(Long id);

}
