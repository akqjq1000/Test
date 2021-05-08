package iducs.springboot.ab200412000.comsoblog.controller;

import iducs.springboot.ab200412000.comsoblog.domain.Member;
import iducs.springboot.ab200412000.comsoblog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
    // Controller :Post, Get, Put, Delete 메소드
    // Repository or DAO : Create, Read(One, List), Update, Delete
    HttpSession session;
    MemberService memberService;

    @Autowired // Spring Framework 가 주입함.
    public MemberController(MemberService memberService) {
        this.memberService = memberService; // 오른쪽 memberService 객체는 등록된 객체를 주입
    }

    /*
    public MemberController() { // 생성자를 활용하여 의존성을 직접 해결 : 강한 결합
        memberService = new MemberServiceImpl();
    }
    */
    @GetMapping("/signin-form")
    public String signinForm(){
        return "/member/signin-form"; // sign-form.html로 이동
    }
    @PostMapping("/signin") //정보를 추가 PostMapping(보안에 좋음), 수정은 PutMapping, 삭제는 DeleteMapping
    public String signin(HttpServletRequest request, Model model){
        session = request.getSession();
        String email = request.getParameter("email");
        String pw = request.getParameter("pw");
        // 입력한 email/pw가 서버 쪽에 존재하면 해당 레코드를 객체로 반환, 그렇지 않은 경우 null
        Member retMember= null;
        if((retMember = memberService.getMemberByEmail(email)) != null && pw.equals(retMember.getPw())) {
            session.setAttribute("login", retMember.getName());
            return "main/index";
        } else {
            model.addAttribute("message", "계정 정보를 확인하시오");
            return "errors/message";
        }
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate(); //현재 session 객체를 무효화
        //session.setAttribute("login", "null"); login 속성 값만 변경
        return "main/index";
    }
}
