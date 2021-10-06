package com.idu.shop.controller;

import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.domain.order.*;
import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductOption;
import com.idu.shop.other.ScriptUtils;
import com.idu.shop.service.member.MemberService;
import com.idu.shop.service.member.MemberShippingAddressService;
import com.idu.shop.service.option.OptionService;
import com.idu.shop.service.option.OptionValueService;
import com.idu.shop.service.order.*;
import com.idu.shop.service.product.ProductOptionService;
import com.idu.shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    MemberService memberService;
    MemberShippingAddressService memberShippingAddressService;
    ProductService productService;
    ProductOptionService productOptionService;
    ShoppingCartService shoppingCartService;
    ShoppingCartOptionService shoppingCartOptionService;
    OrderService orderService;
    OrderOptionService orderOptionService;
    DeliveryService deliveryService;
    PaymentService paymentService;
    CourierService courierService;
    OptionService optionService;
    OptionValueService optionValueService;

    private String createSerialNumber() {
        String ret = "";
        long time = System.currentTimeMillis();
        return ret + time;
    }

    @Autowired
    public OrderController(MemberService memberService,
                           MemberShippingAddressService memberShippingAddressService,
                           ProductService productService,
                           ProductOptionService productOptionService,
                           ShoppingCartService shoppingCartService,
                           ShoppingCartOptionService shoppingCartOptionService,
                           OptionService optionService,
                           OptionValueService optionValueService,
                           OrderService orderService,
                           DeliveryService deliveryService,
                           OrderOptionService orderOptionService,
                           PaymentService paymentService,
                           CourierService courierService) {
        this.memberService = memberService;
        this.memberShippingAddressService = memberShippingAddressService;
        this.productService = productService;
        this.productOptionService = productOptionService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartOptionService = shoppingCartOptionService;
        this.optionService = optionService;
        this.optionValueService = optionValueService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.orderOptionService = orderOptionService;
        this.paymentService = paymentService;
        this.courierService = courierService;
    }

    @PostMapping("order-detail")
    public String orderDetail(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginEmail") == null) return "redirect:/";

        int memberNo = memberService.getId(session.getAttribute("loginEmail").toString());
        String orderSequence = request.getParameter("orderSequence");
        Payment payment = paymentService.readByOrderSequence(orderSequence);
        List<Order> orderList = orderService.readBySerialNumber(orderSequence);
        List<List<OrderOption>> orderOptionList = new ArrayList<>();

        for (Order order: orderList) {
            orderOptionList.add(orderOptionService.readByOrderNo(order.getId()));
        }
        Delivery delivery = deliveryService.read(payment.getOrderSequence());

        model.addAttribute("orderList", orderList);
        model.addAttribute("orderOptionList", orderOptionList);
        model.addAttribute("payment", payment);
        model.addAttribute("paymentList", paymentService.readByMemberNo(memberNo));
        model.addAttribute("delivery", delivery);
        model.addAttribute("courierList", courierService.readList());
        model.addAttribute("shippingAddress", memberShippingAddressService.read(payment.getMemberShippingAddressNo()));

        return "/order/order-detail";
    }


    @PostMapping("add-order")
    public String addOrder(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginEmail") == null) return "redirect:/";
        String serialNumber = createSerialNumber();
        int memberNo = memberService.getId(session.getAttribute("loginEmail").toString());
        int size = Integer.parseInt(request.getParameter("cnt"));
        String[] productNoStr = request.getParameterValues("productNo");
        String[] countStr = request.getParameterValues("count");
        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
        int shippingAddressNo = Integer.parseInt(request.getParameter("shippingAddressNo"));
        String couponNoStr = request.getParameter("couponNumber");
        String paymentWay = request.getParameter("paymentWay");

        for (int i = 0; i < size; i++) {
            String[] optionNoStr = request.getParameterValues("optionNo"+i);
            String[] optionValueNoStr = request.getParameterValues("optionValueNo"+i);
            int productNo = Integer.parseInt(productNoStr[i]);
            int count = Integer.parseInt(countStr[i]);
            Order order = new Order();
            order.setOrderCnt(i);
            order.setMemberNo(memberNo);
            order.setOrderSequence(serialNumber);
            order.setProductNo(productNo);
            Product product = productService.readById(productNo);
            product.setSoldCount(product.getSoldCount() + count);
            product.setStock(product.getStock() - count);
            if (!productService.increaseSoldCount(product)) {
                return "/error/message";
            }
            order.setCount(count);
            if (!orderService.insert(order)) {
                return "/error/message";
            }

            if (optionNoStr != null) {
                for (int j = 0; j < optionNoStr.length; j++) {
                    int optionNo = Integer.parseInt(optionNoStr[j]);
                    int optionValueNo = Integer.parseInt(optionValueNoStr[j]);
                    OrderOption oo = new OrderOption();
                    oo.setOrderNo(orderService.read(order).getId());
                    oo.setOptionNo(optionNo);
                    oo.setOptionValueNo(optionValueNo);
                    if (orderOptionService.insert(oo) != 1) {
                        orderService.delete(order);
                    }
                }
            }
        }

        Payment payment = new Payment();
        payment.setOrderSequence(serialNumber);
        if (paymentWay.equals("card")) {
            int cardType = Integer.parseInt(request.getParameter("cardType"));
            String cardNo = request.getParameter("cardNumber");
            payment.setCardType(cardType);
            payment.setCardNumber(cardNo);
            payment.setMoneyType(0);
            payment.setMoneyNumber("");
            payment.setPaymentWay(1);
        } else if(paymentWay.equals("money")) {
            int moneyType = Integer.parseInt(request.getParameter("moneyType"));
            String moneyNo = request.getParameter("moneyNumber");
            payment.setMoneyNumber(moneyNo);
            payment.setMoneyType(moneyType);
            payment.setCardType(0);
            payment.setCardNumber("");
            payment.setPaymentWay(2);
        }
        payment.setTotalPrice(totalPrice);
        payment.setCouponNo((couponNoStr != null) ? Integer.parseInt(couponNoStr) : 0);
        payment.setMemberNo(memberNo);
        payment.setMemberShippingAddressNo(shippingAddressNo);

        if (!paymentService.insert(payment)) {
            // 주문 목록 제거
            // 주문 옵션 목록 제거
        }

        Delivery delivery = new Delivery();
        delivery.setMemberNo(memberNo);
        delivery.setMemberShippingAddressNo(shippingAddressNo);
        delivery.setOrderSequence(serialNumber);
        // 0 - 주문완료
        // 1 - 배송출발
        // 2 - 배송중
        // 3 - 배송완료
        delivery.setStatus(0);
        delivery.setCode("");
        delivery.setCourierNo(0);

        deliveryService.insert(delivery);

        model.addAttribute("message", "주문이 완료되었습니다.");
        model.addAttribute("orderSequence", payment.getOrderSequence());

        return "/order/order-result";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginEmail") == null) return "redirect:/";
        int memberNo = memberService.getId(session.getAttribute("loginEmail").toString());

        List<ShoppingCart> shoppingCarts = shoppingCartService.readByMemberNoList(memberNo);
        List<List<ShoppingCartOption>> scoList = new ArrayList<>();
        for (ShoppingCart sc : shoppingCarts) {
            scoList.add(shoppingCartOptionService.read(sc.getId()));
        }

        model.addAttribute("shoppingCartOptionList", scoList);
        model.addAttribute("optionList", optionService.readList());
        model.addAttribute("optionValueList", optionValueService.readList());
        session.setAttribute("shoppingCartListSize", shoppingCarts.size());
        model.addAttribute("shoppingCartList", shoppingCarts);
        return "/order/shopping-cart";
    }

    @GetMapping("/orderList")
    public String orderList(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";
        String loginEmail = request.getSession().getAttribute("loginEmail").toString();

        int memberNo = memberService.getId(loginEmail);
        List<Payment> paymentList = paymentService.readByMemberNo(memberNo);
        List<List<Order>> orderList = new ArrayList<>();
        for (Payment payment : paymentList) {
            orderList.add(orderService.readBySerialNumber(payment.getOrderSequence()));
        }
        model.addAttribute("optionValueList", optionValueService.readList());
        model.addAttribute("orderOptionList", orderOptionService.readList());
        model.addAttribute("paymentList", paymentList);
        model.addAttribute("orderList", orderList);

        return "order/order-list";
    }

    @PostMapping("add-shopping-cart")
    public String addShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";
        String loginEmail = request.getSession().getAttribute("loginEmail").toString();
        String serialNumber = createSerialNumber();

        int memberNo = -1;
        if (loginEmail != null) {
            memberNo = memberService.getId(loginEmail);
        } else {
            return "redirect:/members/loginView";
        }

        int cnt = 1;
        if (request.getParameter("cnt") != null) {
            cnt = Integer.parseInt(request.getParameter("cnt"));
        }

        for (int i = 1; i <= cnt; i++) {
            String productNoStr = request.getParameter("productNo"+i);
            String[] options = request.getParameterValues("options"+i);
            String countStr = request.getParameter("count" + i);
            int checker = 0;

            if (productNoStr != null) {
                int productNo = Integer.parseInt(productNoStr);
                int count = Integer.parseInt(countStr);

                if (options != null) {
                    // 제품 옵션들
                    List<ProductOption> poList = productOptionService.readByProductNoList(productNo);
                    // 장바구니 리스트
                    List<ShoppingCart> scList = shoppingCartService.readByMemberNoList(memberNo);
                    for (ShoppingCart sc : scList) {// 장바구니 하나씩 꺼내기
                        checker=0;
                        if (sc.getProductNo() != productNo) break;// 없는 제품이면 나가기
                        // 장바구니 옵션 리스트
                        List<ShoppingCartOption> scoList = shoppingCartOptionService.read(sc.getId());
                        for (ShoppingCartOption sco: scoList) {// 장바구니 옵션 하나씩 꺼내기
                            for (int j = 0; j < poList.size(); j++) {
                                // 제품 옵션과 장바구니 옵션과 비교
                                if (poList.get(j).getOptionNo() == sco.getOptionNo()) {
                                    // 넘어온 옵션과 비교
                                    for (int k = 0; k < options.length; k++) {
                                        if (sco.getOptionValueNo() == Integer.parseInt(options[k])) {
                                            checker++;// 모든 옵션이 같다면 checker증가
                                        }
                                    }
                                }
                            }
                        }
                        // 옵션이 모두 중복된다면 break
                        if (checker == options.length) break;
                    }
                }
                if (checker == options.length) {
                    try {
                        ScriptUtils.alertAndBackPage(response, "이미 장바구니에 있는 옵션입니다.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                ShoppingCart sc = new ShoppingCart();
                sc.setCnt(i);
                sc.setSerialNumber(serialNumber);
                sc.setProductNo(productNo);
                sc.setMemberNo(memberNo);
                sc.setCount(count);

                if (!shoppingCartService.insert(sc)) {
                    System.out.println("장바구니 추가 실패");
                }
                if (options != null) {
                    List<ProductOption> poList = productOptionService.readByProductNoList(productNo);
                    for (ProductOption po : poList) {
                        ShoppingCartOption sco = new ShoppingCartOption();
                        sco.setShoppingCartNo(shoppingCartService.read(sc).getId());
                        sco.setOptionNo(po.getOptionNo());
                        for (int j = 0; j < poList.size(); j++) {
                            OptionValue ov = new OptionValue();
                            ov.setId(Integer.parseInt(options[j]));
                            if (optionValueService.read(ov).getOptionNo() == po.getOptionNo()) {
                                sco.setOptionValueNo(ov.getId());
                            }
                        }
                        if (shoppingCartOptionService.insert(sco) != 1) {
                            System.out.println("장바구니 옵션 추가 실패");
                        }
                    }
                }
            } else {
                System.out.println("제품번호 없음");
            }
        }
        return "redirect:/orders/shopping-cart";
    }

    @PostMapping("payment-view")
    public String paymentView(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("loginEmail") == null) return "redirect:/members/loginView";
        String loginEmail = request.getSession().getAttribute("loginEmail").toString();

        int memberNo = -1;
        if (loginEmail != null) {
            memberNo = memberService.getId(loginEmail);
        } else {
            return "redirect:/members/loginView";
        }

        int cnt = Integer.parseInt(request.getParameter("cnt"));

        String menu = request.getParameter("menu");
        if (menu.equals("shoppingCart")) {

        } else if (menu.equals("payment")) {

        }

        List<OrderTemp> orderList = new ArrayList<>();
        int sum = 0;
        int shippingFee = 0;
        int overlap = 0;

        for (int i = 1; i <= cnt; i++) {
            String productNoStr = request.getParameter("productNo"+i);
            String[] options = request.getParameterValues("options"+i);
            String countStr = request.getParameter("count" + i);

            OrderTemp order = new OrderTemp();
            List<OrderOption> orderOptions = new ArrayList<>();

            if (productNoStr != null) {
                int productNo = Integer.parseInt(productNoStr);
                if (overlap != productNo) {
                    overlap = productNo;
                    shippingFee += productService.readById(productNo).getShippingFee();
                }

                if (options != null) {
                    List<ProductOption> poList = productOptionService.readByProductNoList(productNo);
                    for (ProductOption po : poList) {
                        OrderOption oo = new OrderOption();
                        oo.setOrderNo(po.getId());
                        oo.setOptionNo(po.getOptionNo());
                        for (int j = 0; j < poList.size(); j++) {
                            OptionValue ov = new OptionValue();
                            ov.setId(Integer.parseInt(options[j]));
                            if (optionValueService.read(ov).getOptionNo() == po.getOptionNo()) {
                                oo.setOptionValueNo(ov.getId());
                            }
                        }
                        orderOptions.add(oo);
                    }
                }

                int count = Integer.parseInt(countStr);

                order.setId(i);
                order.setOrderOptions(orderOptions);
                order.setProduct(productService.readById(productNo));
                order.setCount(count);
                order.setMemberNo(memberNo);
                order.setTotalPrice(0);
                order.setCouponNo(0);
                orderList.add(order);

                sum += order.getProduct().getPrice() * count;

            } else continue;
        }

        if (memberNo != -1) {
            model.addAttribute("memberShippingAddressList", memberShippingAddressService.readMemberList(memberNo));
            model.addAttribute("member", memberService.readByEmail(loginEmail));
        }
        model.addAttribute("optionList", optionService.readList());
        model.addAttribute("optionValueList", optionValueService.readList());
        model.addAttribute("orderList", orderList);
        model.addAttribute("totalPrice", sum);
        model.addAttribute("shippingFee", shippingFee);

        return "/order/payment";
    }

    @PostMapping("delete-shopping-cart")
    public String deleteShoppingCart(HttpServletRequest request) {
        String[] deleteShoppingCartStr = request.getParameterValues("deleteShoppingCartNo");

        for (int i = 0; i < deleteShoppingCartStr.length; i++) {
            int deleteShoppingCartNo = Integer.parseInt(deleteShoppingCartStr[i]);

            shoppingCartService.delete(deleteShoppingCartNo);
            shoppingCartOptionService.delete(deleteShoppingCartNo);
        }

        return "redirect:/orders/shopping-cart";
    }

    @GetMapping("change-shipping-address-list")
    public String changeShippingAddressList(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("loginEmail") == null) {
            model.addAttribute("menu", request.getParameter("menu"));
            return "/member/login";
        }
        String loginEmail = request.getSession().getAttribute("loginEmail").toString();

        int memberNo = memberService.getId(loginEmail);
        model.addAttribute("memberShippingAddressList", memberShippingAddressService.readMemberList(memberNo));

        return "/order/order-shipping-address-list";
    }
}
