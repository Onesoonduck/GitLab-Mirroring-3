package mytrophy.api.article.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mytrophy.api.article.dto.ArticleRequest;
import mytrophy.api.article.entity.Article;
import mytrophy.api.article.enumentity.Header;
import mytrophy.api.article.repository.ArticleRepository;
import mytrophy.global.handler.resourcenotfound.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    // 게시글 생성
    @Transactional // 트랜잭션 처리
    public Article createArticle(ArticleRequest articleRequest, List<MultipartFile> urls) throws IOException {

        // 이미지 경로 설정
        if (urls == null) {
            Article article = Article.builder()
                .header(articleRequest.getHeader())
                .name(articleRequest.getName())
                .content(articleRequest.getContent())
                .build();
        }

        // Article 생성
        Article article = Article.builder()
            .header(articleRequest.getHeader())
            .name(articleRequest.getName())
            .content(articleRequest.getContent())
            .imagePath(articleRequest.getImagePath()) // 이미지 경로 설정
            .build();

        return articleRepository.save(article);
    }

    // 게시글 리스트 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // 해당 게시글 조회
    public Article findById(Long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return article;
    }

    // 말머리 별 게시글 리스트 조회
    public List<Article> findAllByHeader(Header header) {
        return articleRepository.findByHeader(header);
    }

    // 말머리 별 해당 게시글 조회
    public Article findByIdAndHeader(Long id, Header header) {
        return articleRepository.findByIdAndHeader(id, header);
    }

    // 게시글 수정
    @Transactional
    public Article updateArticle(Long id, ArticleRequest articleRequest) {
        Article article = findById(id);

        if (article == null) {
            throw new ResourceNotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        // 게시글 정보 업데이트
        article.updateArticle(articleRequest.getHeader(), articleRequest.getName(), articleRequest.getContent(), articleRequest.getImagePath());

        return articleRepository.save(article);
    }

    // 게시글 삭제
    @Transactional
    public void deleteArticle(Long id) {
        Article article = findById(id);
        if (article != null) {
            articleRepository.delete(article);
        } else {
            throw new ResourceNotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        articleRepository.delete(article);
    }

    // 좋아요 증가
    @Transactional
    public void upCntUp(Long id) {
        Article article = findById(id);
        article.upCntUp();
    }

    // 좋아요 감소
    @Transactional
    public void CntUpDown(Long id) {
        Article article = findById(id);
        article.CntUpDown();
    }

}
