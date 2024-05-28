package mytrophy.api.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mytrophy.api.game.entity.Category;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 여기서 username 이 로그인할 때 입력하는 로그인 아이디
    private String password; // 비밀번호
    private String role; // 권한 (ROLE_USER, ROLE_ADMIN)
    private String name; // 이름
    private String nickname; // 별명
    private String email; // 이메일
    private Long steamId; // 스팀 ID 값
    private String loginType; // 로그인 형태 (소셜로그인, 일반로그인)
    private String imagePath; // 프로필 이미지

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;

    @CreationTimestamp
    private LocalDateTime created_at; // 생성날짜

    @UpdateTimestamp
    private LocalDateTime update_at; // 수정날짜
}
