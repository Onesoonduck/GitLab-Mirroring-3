package mytrophy.api.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username; // 여기서 username 이 로그인할 때 입력하는 로그인 아이디
    private String password; // 비밀번호
    private String role; // 권한 (ROLE_USER, ROLE_ADMIN)
    private String name; // 이름
    private String nickname; // 별명
    private Long steam_id; // 스팀 ID 값
    private String login_type; // 로그인 형태 (소셜로그인, 일반로그인)
    private String profile_image; // 프로필 이미지

    @CreationTimestamp
    private LocalDateTime created_at; // 생성날짜
}
