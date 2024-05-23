package mytrophy.api.member.repository;

import mytrophy.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    Member findByLoginId(String LoginId);

    Optional<Member> findBySteamId(String id);
}
