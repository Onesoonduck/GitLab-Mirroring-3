package mytrophy.api.comment.repository;

import mytrophy.api.comment.entity.Comment;
import mytrophy.api.comment.entity.CommentLike;
import mytrophy.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);

}
