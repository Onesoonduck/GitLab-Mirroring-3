package mytrophy.api.member.service;


import lombok.RequiredArgsConstructor;
import mytrophy.api.member.dto.MemberDto;
import mytrophy.api.member.entity.Member;
import mytrophy.api.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<Member> findBySteamId(String id) {
        return memberRepository.findBySteamId(id);
    }

    public void signupMember(MemberDto memberDto) {

        Boolean isExist = memberRepository.existsByUsername(memberDto.getUsername());

        if (isExist) {
            return;
        }

        // 만약 회원가입 정보가 없으면 (처음 회원가입하면)
        Member signupMember = new Member();
        signupMember.setUsername(memberDto.getUsername());
        signupMember.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        signupMember.setRole("ROLE_USER");
        signupMember.setName(memberDto.getName());
        signupMember.setNickname(memberDto.getNickname());
        signupMember.setEmail(memberDto.getEmail());
        signupMember.setSteamId(memberDto.getSteamId());
        signupMember.setLoginType("mytrophy");
        signupMember.setProfileImage(memberDto.getProfileImage());

        memberRepository.save(signupMember);
    }

    // 회원 조회
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("다음 ID에 해당하는 회원을 찾을 수 없습니다: " + id));
    }

    // 회원 수정
    public boolean updateMemberById(Long id, MemberDto memberDto) {
        if (memberRepository.existsById(id)) {
            Member member = memberRepository.findById(id).get();
            member.setName(memberDto.getName());
            member.setEmail(memberDto.getEmail());
            member.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
            member.setRole("ROLE_USER");
            member.setName(memberDto.getName());
            member.setNickname(memberDto.getNickname());
            member.setEmail(memberDto.getEmail());
            member.setSteamId(memberDto.getSteamId());
            member.setLoginType(memberDto.getLoginType());
            member.setProfileImage(memberDto.getProfileImage());
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    // 회원 삭제
    public boolean deleteMemberById(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
