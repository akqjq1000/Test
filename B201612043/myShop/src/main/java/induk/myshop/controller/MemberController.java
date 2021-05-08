package induk.myshop.controller;

import induk.myshop.domain.Member;
import induk.myshop.repository.MemberDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/loginView")
    public String loginView() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Member loginMember = null;
        MemberDAO dao = new MemberDAO();

        if ((loginMember = dao.readByLogin(email, password)) != null) {
            request.getSession().setAttribute("email", loginMember.getEmail());
            return "main/index";
        } else {
            request.setAttribute("message", "※ 아이디와 비밀번호를 확인해주세요");
            return "member/login";
        }
    }

    @GetMapping("/registerView")
    public String registerView() {
        return "member/register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Member newMember = new Member();
        newMember.setEmail(email);
        newMember.setPassword(password);
        newMember.setName(name);
        newMember.setPhone(phone);
        newMember.setAddress(address);

        MemberDAO dao = new MemberDAO();
        if (dao.insert(newMember)) {
            return "member/login";
        } else {
            request.setAttribute("message", "※ ");
            return "member/register";
        }
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        String email = (String)request.getSession().getAttribute("email");
        Member member = null;

        MemberDAO dao = new MemberDAO();
        if ((member = dao.readByEmail(email)) != null) {
            member.setPassword("");
            model.addAttribute("member", member);
        }
        return "member/info";
    }

    @PostMapping("update")
    public String update(HttpServletRequest request, Model model) {
        String email = (String)request.getSession().getAttribute("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Member updateMember = new Member();
        updateMember.setEmail(email);
        updateMember.setPassword(password);
        updateMember.setName(name);
        updateMember.setPhone(phone);
        updateMember.setAddress(address);

        MemberDAO dao = new MemberDAO();
        if (dao.update(updateMember)) {
            updateMember.setPassword("");
            model.addAttribute("member", updateMember);
            return "member/info";
        } else {
            return "main/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "main/index";
    }
}
