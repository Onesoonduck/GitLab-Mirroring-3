package mytrophy.api.comment.service;


import lombok.RequiredArgsConstructor;
import mytrophy.api.article.entity.Article;
import mytrophy.api.article.repository.ArticleRepository;
import mytrophy.api.comment.dto.CommentDto;
import mytrophy.api.comment.dto.CreateCommentDto;
import mytrophy.api.comment.entity.Comment;
import mytrophy.api.comment.entity.CommentLike;
import mytrophy.api.comment.repository.CommentLikeRepository;
import mytrophy.api.comment.repository.CommentRepository;
import mytrophy.api.member.entity.Member;
import mytrophy.api.member.repository.MemberRepository;
import mytrophy.global.handler.CustomException;
import mytrophy.global.handler.ErrorCodeEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentLikeRepository commentLikeRepository;

    //댓글 등록
    @Override
    public CommentDto createComment(Long memberId, Long articleId, CreateCommentDto createCommentDto, Long parentCommentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_MEMBER_ID));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_ARTICLE_ID));

        Comment parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_PARENT_COMMENT_ID));

            if(parentComment.getParentComment() != null) {
                throw new CustomException(ErrorCodeEnum.NOT_PARENT_COMMENT);
            }
        }

        Comment comment = dtoToEntity(createCommentDto, member, article, parentComment);
        Comment createdComment = commentRepository.save(comment);
        return entityToDto(createdComment);
    }

    //댓글 수정
    @Override
    public CommentDto updateComment(Long commentId, Long memberId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_COMMENT_ID));

        if (!isAuthorized(commentId, memberId)) {
            throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);
        }

        comment.updateContent(content);
        return entityToDto(commentRepository.save(comment));
    }

    //댓글 삭제
    @Override
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_COMMENT_ID));

        if (!isAuthorized(commentId, memberId)) {
            throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);
        }

        if (!comment.getChildrenComment().isEmpty()) {
            commentRepository.deleteAllByParentComment(comment);
        }

        commentRepository.delete(comment);
    }

    //특정 회원의 댓글 전체조회
    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByMemberId(Long memberId) {
        List<Comment> comments = commentRepository.findByMemberId(memberId);
        return comments.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    //댓글 추천
    @Override
    public void toggleLikeComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_COMMENT_ID));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_MEMBER_ID));

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndMember(comment, member);

        if(existingLike.isPresent()) {
            // 좋아요 취소
            commentLikeRepository.delete(existingLike.get());
            comment.decrementLikes();
        } else {
            // 좋아요
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .member(member)
                    .build();
            commentLikeRepository.save(commentLike);
            comment.incrementLikes();
        }

        commentRepository.save(comment);
    }

    //권한 확인
    @Override
    public boolean isAuthorized(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.NOT_EXISTS_COMMENT_ID));
        return comment.getMember().getId().equals(memberId);
    }

    //entity -> dto
    private CommentDto entityToDto(Comment comment) {
        return new CommentDto(comment);
    }

    //dto -> entity
    private Comment dtoToEntity(CreateCommentDto createCommentDto, Member member, Article article, Comment parentComment) {
        return Comment.builder()
                .content(createCommentDto.getContent())
                .member(member)
                .article(article)
                .parentComment(parentComment)
                .build();
    }
}
