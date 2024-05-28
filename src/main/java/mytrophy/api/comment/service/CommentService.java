package mytrophy.api.comment.service;

import mytrophy.api.comment.dto.CommentDto;
import mytrophy.api.comment.dto.CreateCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long memberId, Long articleId, CreateCommentDto createCommentDto);
    CommentDto updateComment(Long commentId, String content);

    //수정할 부분
    void deleteComment(Long commentId);

    List<CommentDto> findByArticleId(Long articleId);
    List<CommentDto> findByMemberId(Long memberId);

    //수정할 부분
    int countByArticleId(Long articleId);

    void likeComment(Long commentId, Long memberId);
    void unlikeComment(Long commentId, Long memberId);

    boolean isAuthorized(Long commentId, Long memberId);
}
