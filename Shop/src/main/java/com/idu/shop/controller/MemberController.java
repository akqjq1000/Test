package com.idu.shop.controller;

import com.idu.shop.domain.DataContainer;
import com.idu.shop.domain.member.Heart;
import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.member.MemberCoupon;
import com.idu.shop.domain.member.MemberShippingAddress;
import com.idu.shop.domain.order.Coupon;
import com.idu.shop.domain.order.ShoppingCart;
import com.idu.shop.domain.product.Product;
import com.idu.shop.other.ScriptUtils;
import com.idu.shop.service.member.HeartService;
import com.idu.shop.service.category.bCategoryService;
import com.idu.shop.service.member.MemberCouponService;
import com.idu.shop.service.member.MemberService;
import com.idu.shop.service.member.MemberShippingAddressService;
import com.idu.shop.service.order.CouponService;
import com.idu.shop.service.order.ShoppingCartService;
import com.idu.shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    MemberShippingAddressService memberShippingAddressService;
    MemberService memberService;
    MemberCouponService memberCouponService;
    HeartService heartService;
    ShoppingCartService shoppingCartService;
    CouponService couponService;
    ProductService productService;
    bCategoryService bCategoryService;

    @Autowired
    public MemberController(MemberService memberService,
                            MemberShippingAddressService memberShippingAddressService,
                            MemberCouponService memberCouponService,
                            HeartService heartService,
                            ShoppingCartService shoppingCartService,
                            CouponService couponService,
                            bCategoryService bCategoryService,
                            ProductService productService) {
        this.memberService = memberService;
        this.memberShippingAddressService = memberShippingAddressService;
        this.memberCouponService = memberCouponService;
        this.heartService = heartService;
        this.shoppingCartService = shoppingCartService;
        this.couponService = couponService;
        this.productService = productService;
    }

    @GetMapping("/loginView")
    public String loginView(HttpServletRequest request, Model model) {
        model.addAttribute("rememberEmail", false);
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("rememberEmail")) {
                    model.addAttribute("rememberEmail", true);
                    break;
                }
            }
        }

        if (request.getParameter("menu") != null) {
            if (request.getParameter("menu").equals("change-shipping-address")) {
                return "redirect:/orders/change-shipping-address-list";
            }
        }

        return "/member/login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] rememberEmail = request.getParameterValues("rememberEmail");
        boolean flag = false;

        if (rememberEmail != null) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("rememberEmail")) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    Cookie newCookie = new Cookie("rememberEmail", email);
                    newCookie.setMaxAge(60 * 60 * 24 * 7);
                    newCookie.setPath("/");
                    response.addCookie(newCookie);
                    System.out.println("clear");
                }
            }

        } else {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("rememberEmail")) {
                        Cookie remove = new Cookie("rememberEmail", null);
                        remove.setPath("/");
                        remove.setMaxAge(0);
                        response.addCookie(remove);
                    }
                }
            }
        }

        Member loginMember = new Member();
        loginMember.setEmail(email);
        loginMember.setPassword(password);

        Member retMember = null;
        if ((retMember = memberService.login(loginMember)) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginEmail", retMember.getEmail());
            session.setAttribute("loginPoint", retMember.getPoint());

            List<ShoppingCart> scList = shoppingCartService.readByMemberNoList(retMember.getId());

            session.setMaxInactiveInterval(60 * 60 * 1);
            session.setAttribute("shoppingCartListSize", scList.size());

            return "redirect:/";
        }
        else {
            model.addAttribute("message", "※ 이메일과 비밀번호를 확인해주세요");
            return "/member/login";
        }

    }

    @GetMapping("/find-view")
    public String findView() {
        return "/member/find";
    }

    @PostMapping("/find-email")
    public String findEmail(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        Member findMember = new Member();
        findMember.setName(name);
        findMember.setPhone(phone);

        Member retMember = null;
        if ((retMember = memberService.findEmail(findMember)) != null) {
            model.addAttribute("result", "회원님의 이메일은 '" + retMember.getEmail() + "'입니다.");
        } else {
            model.addAttribute("result", "일치하는 정보의 이메일이 없습니다.");
        }
        return "/member/result";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/";
        Member retMember = null;
        String email = request.getSession().getAttribute("loginEmail").toString();
        if ((retMember = memberService.readByEmail(email)) != null) {
            model.addAttribute("memberShippingAddressList", memberShippingAddressService.readMemberList(retMember.getId()));
            List<Coupon> couponList = new ArrayList<>();
            for (MemberCoupon mc: memberCouponService.readByMemberNo(memberService.getId(email))) {
                couponList.add(couponService.readById(mc.getCouponNo()));
            }
            model.addAttribute("couponList", couponList);
            model.addAttribute("member", retMember);
            return "/member/info";
        } else {
            return "";
        }
    }

    @GetMapping("/delete-heart")
    public String deleteHeart(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";

        heartService.delete(Integer.parseInt(request.getParameter("productNo")));

        return "redirect:/members/heart-list";
    }

    @GetMapping("/add-heart")
    public String addHeart(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";
        int memberNo = memberService.getId(request.getSession().getAttribute("loginEmail").toString());
        int id = Integer.parseInt(request.getParameter("id"));

        Heart heart = new Heart();
        heart.setMemberNo(memberNo);
        heart.setProductNo(id);

        List<Heart> heartList = heartService.readByMemberNo(memberNo);
        for (Heart h : heartList) {
            if (h.getProductNo() == id) {
                try {
                    ScriptUtils.alertAndBackPage(response, "이미 찜한 상품입니다.");
                    return "redirect:/";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!heartService.insert(heart)) {
            return "/error/message";
        }

        try {
            ScriptUtils.alertAndBackPage(response, "찜 리스트에 추가되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/heart-list")
    public String heartList(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";
        int memberNo = memberService.getId(request.getSession().getAttribute("loginEmail").toString());
        List<Heart> heartList = heartService.readByMemberNo(memberNo);
        List<Product> productList = new ArrayList<>();
        for (Heart h : heartList) {
            int productNo = h.getProductNo();
            Product product = productService.readById(productNo);
            System.out.println(product);
            productList.add(product);
        }

        DataContainer dc = new DataContainer();
        dc.setProductList(productList);

        model.addAttribute("dataContainer", dc);
        return "/member/heart-list";
    }

    @GetMapping("set-representative-shipping-address")
    public String setRepresentativeShippingAddress(@RequestParam int id, HttpServletRequest request) {
        String loginEmail = request.getSession().getAttribute("loginEmail").toString();

        if (loginEmail != null) {
            int memberNo = memberService.getId(loginEmail);

            for (MemberShippingAddress msa : memberShippingAddressService.readMemberList(memberNo)) {
                msa.setRepresentative(0);
                if (msa.getId() == id) msa.setRepresentative(1);
                memberShippingAddressService.update(msa);
            }
        }
        return "redirect:/members/info";
    }

    @PostMapping("/update-shipping-address")
    public String updateShippingAddress(HttpServletRequest request, Model model) {
        String email = request.getSession().getAttribute("loginEmail").toString();

        String name = "";
        String phone = "";
        String address = "";
        String comments = "";
        int id = -1;

        if (request.getParameter("menu") != null) {
            if (request.getParameter("menu").equals("change-shipping-address")) {
                name = request.getParameter("childName");
                phone = request.getParameter("childPhone");
                address = request.getParameter("childAddress");
                comments = request.getParameter("childComments");
                id = Integer.parseInt(request.getParameter("childId"));

                updateShippingAddress(id, name, email, phone, address, comments);

                return "redirect:/orders/change-shipping-address-list";
            } else {
                name = request.getParameter("name");
                phone = request.getParameter("phone");
                address = request.getParameter("address");
                comments = request.getParameter("comments");
                id = Integer.parseInt(request.getParameter("id"));

                updateShippingAddress(id, name, email, phone, address, comments);

                return "redirect:/members/info";
            }
        }
        return "redirect:/members/info";
    }

    public void updateShippingAddress(int id, String name, String email, String phone, String address, String comments) {
        MemberShippingAddress msa = new MemberShippingAddress();
        msa.setId(id);
        msa.setMemberNo(memberService.getId(email));
        msa.setName(name);
        msa.setPhone(phone);
        msa.setAddress(address);
        msa.setComments(comments);

        memberShippingAddressService.update(msa);
    }

    @GetMapping("delete-shipping-address")
    public String deleteShippingAddress(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        MemberShippingAddress msa = new MemberShippingAddress();
        msa.setId(id);

        memberShippingAddressService.delete(msa);
        if (request.getParameter("menu") != null) {
            if (request.getParameter("menu").equals("change-shipping-address")) {
                return "redirect:/orders/change-shipping-address-list";
            }
        }
        return "redirect:/members/info";

    }

    @PostMapping("/add-shipping-address")
    public String addShippingAddress(HttpServletRequest request, Model model) {
        String email = request.getSession().getAttribute("loginEmail").toString();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String comments = request.getParameter("comments");

        MemberShippingAddress msa = new MemberShippingAddress();
        msa.setMemberNo(memberService.getId(email));
        msa.setName(name);
        msa.setPhone(phone);
        msa.setAddress(address);
        msa.setComments(comments);
        if (memberShippingAddressService.readMemberList(memberService.getId(email)) == null) {
            msa.setRepresentative(1);
        } else msa.setRepresentative(0);


        memberShippingAddressService.insert(msa);

        if (request.getParameter("menu") != null) {
            if (request.getParameter("menu").equals("change-shipping-address")) {
                return "redirect:/orders/change-shipping-address-list";
            }
        }
        return "redirect:/members/info";
    }

    @GetMapping("/search-email-view")
    public String searchEmail(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");

        if (memberService.readByEmail(email) != null) {
            model.addAttribute("message", true);

        } else {
            model.addAttribute("message", false);
        }
        return "/member/search-email";
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request) {
        String email = request.getSession().getAttribute("loginEmail").toString();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        Member updateMember = new Member();

        updateMember.setEmail(email);
        updateMember.setName(name);
        updateMember.setPhone(phone);
        updateMember.setAddress(address);

        if (password.length() != 0) {
            updateMember.setPassword(password);
            memberService.updatePw(updateMember);
        } else {
            memberService.update(updateMember);
        }

        return "redirect:/members/info";
    }

    @PostMapping("/unregister")
    public String unregister(HttpServletRequest request, Model model) {
        String email = request.getSession().getAttribute("loginEmail").toString();
        String password = request.getParameter("password");
        Member unregisterMember = new Member();
        unregisterMember.setEmail(email);
        unregisterMember.setPassword(password);
        if (memberService.delete(unregisterMember)) {
            request.getSession().invalidate();
            return "redirect:/";
        } else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "/member/info";
        }

    }

    @PostMapping("/register")
    public String register(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        System.out.println(email);
        Member registerMember = new Member();
        registerMember.setEmail(email);
        registerMember.setPassword(password);
        registerMember.setName(name);
        registerMember.setPhone(phone);
        registerMember.setPoint(0);
        registerMember.setAddress(address);

        if (memberService.insert(registerMember)) {
            return "redirect:/members/loginView";
        } else {
            return "/member/register";
        }
    }

    @GetMapping("/registerView")
    public String registerView() {
        return "/member/register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
