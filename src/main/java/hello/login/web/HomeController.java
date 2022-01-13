package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(@CookieValue(name="memberId",required = false) Long memberId , Model model) {
        // valid
        if (memberId == null) {
            return "home";
        }else{
            Member loginMember = memberRepository.findById(memberId);
            log.info("loginMember = {}" , loginMember);
            // valid
            if (loginMember == null) {
                return "home";
            }else{
                // real login
                model.addAttribute("member", loginMember);
                return "loginHome";
            }
        }
    }
}