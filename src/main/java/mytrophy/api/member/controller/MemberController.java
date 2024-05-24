package mytrophy.api.member.controller;

import mytrophy.api.member.dto.SignupDto;
import mytrophy.api.member.service.SignupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final SignupService signupService;

    public MemberController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupMember(SignupDto signupDto) {
        signupService.SignupMember(signupDto);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }
}
