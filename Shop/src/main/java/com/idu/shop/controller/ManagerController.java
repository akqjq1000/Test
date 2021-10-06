package com.idu.shop.controller;

import com.idu.shop.config.MD5Generator;
import com.idu.shop.domain.DataContainer;
import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.category.mCategory;
import com.idu.shop.domain.category.sCategory;
import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.option.Option;
import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.domain.order.Coupon;
import com.idu.shop.domain.order.Courier;
import com.idu.shop.domain.order.Delivery;
import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductImage;
import com.idu.shop.domain.product.ProductOption;
import com.idu.shop.other.ScriptUtils;
import com.idu.shop.service.member.MemberService;
import com.idu.shop.service.option.OptionService;
import com.idu.shop.service.category.bCategoryService;
import com.idu.shop.service.category.mCategoryService;
import com.idu.shop.service.category.sCategoryService;
import com.idu.shop.service.option.OptionValueService;
import com.idu.shop.service.order.*;
import com.idu.shop.service.product.ProductImageService;
import com.idu.shop.service.product.ProductOptionService;
import com.idu.shop.service.product.ProductService;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    MemberService memberService;

    bCategoryService bCategoryService;
    mCategoryService mCategoryService;
    sCategoryService sCategoryService;

    OptionService optionService;
    OptionValueService optionValueService;

    OrderService orderService;
    DeliveryService deliveryService;
    CouponService couponService;
    PaymentService paymentService;
    CourierService courierService;

    ProductService productService;
    ProductOptionService productOptionService;
    ProductImageService productImageService;

    @Autowired
    public ManagerController(MemberService memberService,
                             ProductService productService,
                             ProductImageService productImageService,
                             ProductOptionService productOptionService,
                             OptionService optionService,
                             OptionValueService optionValueService,
                             bCategoryService bCategoryService,
                             mCategoryService mCategoryService,
                             sCategoryService sCategoryService,
                             OrderService orderService,
                             DeliveryService deliveryService,
                             PaymentService paymentService,
                             CourierService courierService,
                             CouponService couponService) {
        this.memberService = memberService;
        this.productService = productService;
        this.productImageService = productImageService;
        this.productOptionService = productOptionService;
        this.optionService = optionService;
        this.optionValueService = optionValueService;
        this.bCategoryService = bCategoryService;
        this.mCategoryService = mCategoryService;
        this.sCategoryService = sCategoryService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.paymentService = paymentService;
        this.courierService = courierService;
        this.couponService = couponService;
    }

    @GetMapping("/")
    public String main() {
        return "/manage/main";
    }

    @GetMapping("/member-manage")
    public String memberManage(Model model) {
        model.addAttribute("memberList", memberService.readList());
        return "/manage/member-list";
    }

    @GetMapping("/coupon-manage")
    public String couponManage(Model model) {
        model.addAttribute("couponList", couponService.readList());
        return "/manage/coupon-list";
    }

    @GetMapping("/product-manage")
    public String productManage(Model model) {
        model.addAttribute("productList", productService.readList());
        return "/manage/product-list";
    }

    @GetMapping("/product-create-view")
    public String productCreateView(Model model) {
        model.addAttribute("bCategoryList", bCategoryService.readList());
        model.addAttribute("mCategoryList", mCategoryService.readList());
        model.addAttribute("sCategoryList", sCategoryService.readList());
        model.addAttribute("optionList", optionService.readList());
        return "/manage/product-create";
    }

    @GetMapping("/option-manage")
    public String optionManage(Model model) {
        model.addAttribute("optionList", optionService.readList());
        model.addAttribute("optionValueList", optionValueService.readList());
        return "/manage/option-list";
    }


    @GetMapping("/category-manage")
    public String categoryManage(Model model) {
        model.addAttribute("bCategoryList", bCategoryService.readList());
        model.addAttribute("mCategoryList", mCategoryService.readList());
        model.addAttribute("sCategoryList", sCategoryService.readList());
        return "/manage/category-list";
    }

    @PostMapping("/product-create")
    public String productCreate(MultipartHttpServletRequest request) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String code = request.getParameter("code");
        String copName = request.getParameter("copName");
        String courier = request.getParameter("courier");
        String description = request.getParameter("description");
        String bCateNo = request.getParameter("bCateNo");
        String mCateNo = request.getParameter("mCateNo");
        String sCateNo = request.getParameter("sCateNo");
        String[] isCheck = request.getParameterValues("isCheck");
        int discount = Integer.parseInt(request.getParameter("discount"));
        int status = Integer.parseInt(request.getParameter("status"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int shippingFee = Integer.parseInt(request.getParameter("shippingFee"));

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCode(code);
        product.setCopName(copName);
        product.setCourier(courier);
        product.setRatio(0);
        product.setDescription(description);
        try {
            String origFilename = request.getFile("contentsImage").getOriginalFilename();
            if (!origFilename.equals("")) {
                product.setContentsImage(origFilename);
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + origFilename;
                request.getFile("contentsImage").transferTo((new File(filePath)));
            }
            origFilename = request.getFile("productsImage").getOriginalFilename();
            if (!origFilename.equals("")) {
                product.setProductImage(origFilename);
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + origFilename;
                request.getFile("productsImage").transferTo((new File(filePath)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setBCateNo((bCateNo != null) ? Integer.parseInt(bCateNo) : -1);
        product.setMCateNo((mCateNo != null) ? Integer.parseInt(mCateNo) : -1);
        product.setSCateNo((sCateNo != null) ? Integer.parseInt(sCateNo) : -1);
        if (isCheck != null) {
            for (String isChecked : isCheck) {
                if (isChecked.equals("isNew")) product.setIsNew(1);
                if (isChecked.equals("isHit")) product.setIsHit(1);
                if (isChecked.equals("isSale")) product.setIsSale(1);
            }
        }
        product.setDiscount(discount);
        product.setStatus(status);
        product.setStock(stock);
        product.setShippingFee(shippingFee);
        product.setSoldCount(0);

        productService.insert(product);

        int productNo = productService.read(product).getId();

        ProductImage productImage = new ProductImage();
        productImage.setProductNo(productNo);
        for (MultipartFile file : request.getFiles("productImage")) {
            try {
                String origFilename = file.getOriginalFilename();
                if (origFilename.equals("")) break;
                String filename = (new MD5Generator(origFilename).toString()) + ".jpg";
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                file.transferTo((new File(filePath)));

                productImage.setOrigFilename(origFilename);
                productImage.setFilename(filename);
                productImage.setFilePath(filePath);

                productImageService.insert(productImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] optionCheck = request.getParameterValues("optionCheck");

        if (optionCheck != null) {
            for (String opt : optionCheck) {
                int val = Integer.parseInt(opt);
                ProductOption po = new ProductOption();
                po.setProductNo(productNo);
                po.setOptionNo(val);
                productOptionService.insert(po);
            }
        }

        return "redirect:/manager/product-manage";
    }

    @PostMapping("/create")
    public String create( HttpServletRequest request) {
        String menu = request.getParameter("menu");
        if (menu.equals("member")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            int point = Integer.parseInt(request.getParameter("point"));

            Member member = new Member();
            member.setEmail(email);
            member.setPassword(password);
            member.setName(name);
            member.setPhone(phone);
            member.setAddress(address);
            member.setPoint(point);

            memberService.insert(member);

            return "redirect:/manager/member-manage";
        } else if (menu.equals("category")) {
            String opt = request.getParameter("insertOpt");

            if (opt.equals("b_cate")) {
                String categoryValue = request.getParameter("insertCategoryValue");

                bCategory bCate = new bCategory();
                bCate.setName(categoryValue);

                bCategoryService.insert(bCate);
                return "redirect:/manager/category-manage";
            } else if (opt.equals("m_cate")) {
                int bCateNo = Integer.parseInt(request.getParameter("insertCateNo"));
                String categoryValue = request.getParameter("insertCategoryValue");

                mCategory mCate = new mCategory();
                mCate.setBCateNo(bCateNo);
                mCate.setName(categoryValue);

                mCategoryService.insert(mCate);
                return "redirect:/manager/category-manage";
            } else if (opt.equals("s_cate")) {
                int mCateNo = Integer.parseInt(request.getParameter("insertCateNo"));
                String categoryValue = request.getParameter("insertCategoryValue");

                sCategory sCate = new sCategory();
                sCate.setMCateNo(mCateNo);
                sCate.setName(categoryValue);

                sCategoryService.insert(sCate);
                return "redirect:/manager/category-manage";
            }

            return "redirect:/manager/category-manage";
        } else if (menu.equals("option")) {
            String opt = request.getParameter("insertOpt");

            if (opt.equals("opt")) {
                String insertValue = request.getParameter("insertValue");

                Option option = new Option();
                option.setName(insertValue);

                optionService.insert(option);
            } else if (opt.equals("opt_value")) {
                String insertValue = request.getParameter("insertValue");
                int optionNo = Integer.parseInt(request.getParameter("insertOptionNo"));

                OptionValue optionValue = new OptionValue();
                optionValue.setOptionNo(optionNo);
                optionValue.setName(insertValue);

                optionValueService.insert(optionValue);
            }
            return "redirect:/manager/option-manage";
        } else if (menu.equals("coupon")) {
            String name = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String percentStr = request.getParameter("percent");

            Coupon coupon = new Coupon();
            coupon.setName(name);
            if (!priceStr.equals("")) coupon.setPrice(Integer.parseInt(priceStr));
            else if (!percentStr.equals("")) coupon.setPercent(Integer.parseInt(percentStr));
            couponService.insert(coupon);
            return "redirect:/manager/coupon-manage";
        } else if (menu.equals("order")) {
            return "";
        } else if (menu.equals("courier")) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String courier = request.getParameter("courier");

            Courier c = new Courier();
            c.setName(name);
            c.setPhone(phone);
            c.setCourier(courier);

            courierService.insert(c);

            return "redirect:/manager/courier-manage";
        }
        return "";
    }

    @GetMapping("/product-delete")
    public String productDelete(HttpServletRequest request) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        Product product = new Product();
        product.setId(productNo);
        productService.delete(product);

        return "redirect:/manager/product-manage";
    }

    @GetMapping("/product-update-view")
    public String productUpdateView(HttpServletRequest request, Model model) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));

        DataContainer dc = new DataContainer();
        dc.setBCategoryList(bCategoryService.readList());
        dc.setMCategoryList(mCategoryService.readList());
        dc.setSCategoryList(sCategoryService.readList());
        dc.setProduct(productService.readById(productNo));
        dc.setProductOptionList(productOptionService.readByProductNoList(productNo));
        dc.setOptionList(optionService.readList());
        dc.setProductImageList(productImageService.readByProductNoList(productNo));

        model.addAttribute("dataContainer", dc);

        return "/manage/product-update";
    }

    @PostMapping("/product-update")
    public String productUpdate(MultipartHttpServletRequest request) {
        int productNo = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String code = request.getParameter("code");
        String copName = request.getParameter("copName");
        String courier = request.getParameter("courier");
        String description = request.getParameter("description");
        String bCateNo = request.getParameter("bCateNo");
        String mCateNo = request.getParameter("mCateNo");
        String sCateNo = request.getParameter("sCateNo");
        String[] isCheck = request.getParameterValues("isCheck");
        int discount = Integer.parseInt(request.getParameter("discount"));
        int status = Integer.parseInt(request.getParameter("status"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int shippingFee = Integer.parseInt(request.getParameter("shippingFee"));

        Product product = productService.readById(productNo);
        product.setName(name);
        product.setPrice(price);
        product.setCode(code);
        product.setCopName(copName);
        product.setCourier(courier);
        product.setDescription(description);
        try {
            String origFilename = request.getFile("contentsImage").getOriginalFilename();
            if (!origFilename.equals("")) {
                product.setContentsImage(origFilename);
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + origFilename;
                request.getFile("contentsImage").transferTo((new File(filePath)));
            }
            origFilename = request.getFile("productsImage").getOriginalFilename();
            if (!origFilename.equals("")) {
                product.setProductImage(origFilename);
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + origFilename;
                request.getFile("productsImage").transferTo((new File(filePath)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setBCateNo((bCateNo != null) ? Integer.parseInt(bCateNo) : -1);
        product.setMCateNo((mCateNo != null) ? Integer.parseInt(mCateNo) : -1);
        product.setSCateNo((sCateNo != null) ? Integer.parseInt(sCateNo) : -1);
        if (isCheck != null) {
            for (String isChecked : isCheck) {
                if (isChecked.equals("isNew")) product.setIsNew(1);
                if (isChecked.equals("isHit")) product.setIsHit(1);
                if (isChecked.equals("isSale")) product.setIsSale(1);
            }
        }
        product.setDiscount(discount);
        product.setStatus(status);
        product.setStock(stock);
        product.setShippingFee(shippingFee);

        productService.update(product);

        ProductImage productImage = new ProductImage();
        productImage.setProductNo(productNo);

        for (MultipartFile file : request.getFiles("productImage")) {
            if (file == null) break;
            try {
                String origFilename = file.getOriginalFilename();
                if (origFilename.equals("")) break;
                String filename = (new MD5Generator(origFilename).toString()) + ".jpg";
                String savePath = System.getProperty("user.dir") + "\\files";
                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String filePath = savePath + "\\" + filename;
                file.transferTo((new File(filePath)));

                productImage.setOrigFilename(origFilename);
                productImage.setFilename(filename);
                productImage.setFilePath(filePath);

                productImageService.insert(productImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] optionCheck = request.getParameterValues("optionCheck");

        if (optionCheck != null) {
            for (String opt : optionCheck) {
                int val = Integer.parseInt(opt);
                ProductOption po = new ProductOption();
                po.setProductNo(productNo);
                po.setOptionNo(val);
                productOptionService.update(po);
            }
        }

        return "redirect:/manager/product-manage";
    }

    @GetMapping("/product-search")
    public String productSearch(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("keyword");

        model.addAttribute("productList", productService.searchByProductName(keyword));

        return "/manage/product-list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String menu = request.getParameter("menu");

        if (menu.equals("option")) {
            String deleteOpt = request.getParameter("deleteOpt");
            int id = Integer.parseInt(request.getParameter("deleteOptionNo"));
            if (deleteOpt.equals("opt")) {
                Option option = new Option();
                option.setId(id);
                optionService.delete(option);
                optionValueService.deleteByOptionNo(id);
            } else if (deleteOpt.equals("opt_value")) {
                OptionValue optionValue = new OptionValue();
                optionValue.setId(id);
                optionValueService.delete(optionValue);
            }
            return "redirect:/manager/option-manage";
        } else if (menu.equals("member")) {
            int id = Integer.parseInt(request.getParameter("id"));

            Member deleteMember = new Member();
            deleteMember.setId(id);

            Member member = memberService.read(deleteMember);

            memberService.delete(member);
            return "redirect:/manager/member-manage";
        } else if (menu.equals("category")) {
            String opt = request.getParameter("deleteOpt");
            int cateNo = Integer.parseInt(request.getParameter("deleteCateNo"));
            if (opt.equals("b_cate")) {
                for (mCategory mCate : mCategoryService.readList()) {
                    if (mCate.getBCateNo() == cateNo) {
                        sCategoryService.deleteBymCateNo(mCate.getId());
                        mCategoryService.deleteBybCateNo(cateNo);
                    }
                }
                bCategoryService.delete(cateNo);
            } else if (opt.equals("m_cate")) {
                sCategoryService.deleteBymCateNo(cateNo);
                mCategoryService.delete(cateNo);
            } else if (opt.equals("s_cate")) {
                sCategoryService.delete(cateNo);
            }
            return "redirect:/manager/category-manage";
        } else if (menu.equals("coupon")) {
            int id = Integer.parseInt(request.getParameter("id"));
            couponService.delete(id);
            return "redirect:/manager/coupon-manage";
        } else if (menu.equals("productImage")) {
            int productNo = Integer.parseInt(request.getParameter("productNo"));
            int productImageNo = Integer.parseInt(request.getParameter("productImageNo"));

            productImageService.delete(productImageNo);
            return "redirect:/manager/product-update-view?productNo="+productNo;
        }
        return "";
    }

    @PostMapping("update")
    public String update(HttpServletRequest request) {
        String menu = request.getParameter("menu");
        if (menu.equals("option")) {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));

            Option option = new Option();
            option.setId(id);
            option.setName(name);

            optionService.update(option);
            return "redirect:/manager/option-manage";
        } else if (menu.equals("member")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            int point = Integer.parseInt(request.getParameter("point"));

            Member member = new Member();
            member.setEmail(email);
            member.setPassword(password);
            member.setName(name);
            member.setPhone(phone);
            member.setAddress(address);
            member.setPoint(point);

            memberService.updatePw(member);
            return "redirect:/manager/member-manage";
        } else if (menu.equals("category")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");

            bCategory bCate = new bCategory();
            bCate.setId(id);
            bCate.setName(name);

            bCategoryService.update(bCate);
            return "redirect:/manager/category-manage";
        } else if (menu.equals("product")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            String code = request.getParameter("code");
            String copName = request.getParameter("copName");
            String courier = request.getParameter("courier");
            int ratio = Integer.parseInt(request.getParameter("ratio"));
            String description = request.getParameter("description");
            String contentsImage = request.getParameter("contentsImage");
            int bCateNo = Integer.parseInt(request.getParameter("bCateNo"));
            int mCateNo = Integer.parseInt(request.getParameter("mCateNo"));
            int sCateNo = Integer.parseInt(request.getParameter("sCateNo"));
            int isNew = Integer.parseInt(request.getParameter("isNew"));
            int isHit = Integer.parseInt(request.getParameter("isHit"));
            int isSale = Integer.parseInt(request.getParameter("isSale"));
            int discount = Integer.parseInt(request.getParameter("discount"));
            int status = Integer.parseInt(request.getParameter("status"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int shippingFee = Integer.parseInt(request.getParameter("shippingFee"));
            int soldCount = Integer.parseInt(request.getParameter("soldCount"));
            String regdate = request.getParameter("regdate");

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setCode(code);
            product.setCopName(copName);
            product.setCourier(courier);
            product.setRatio(ratio);
            product.setDescription(description);
            product.setContentsImage(contentsImage);
            product.setBCateNo(bCateNo);
            product.setMCateNo(mCateNo);
            product.setSCateNo(sCateNo);
            product.setIsNew(isNew);
            product.setIsHit(isHit);
            product.setIsSale(isSale);
            product.setDiscount(discount);
            product.setStatus(status);
            product.setStock(stock);
            product.setShippingFee(shippingFee);
            product.setSoldCount(soldCount);
            product.setRegdate(regdate);

            productService.update(product);
            return "redirect:/manager/product-manage";
        } else if (menu.equals("delivery")) {
            int deliveryNo = Integer.parseInt(request.getParameter("deliveryNo"));
            int status = Integer.parseInt(request.getParameter("status"));
            int courierNo = Integer.parseInt(request.getParameter("courierNo"));
            String code = request.getParameter("code") == null ? "" :  request.getParameter("code");

            Delivery delivery = deliveryService.readById(deliveryNo);
            delivery.setCourierNo(courierNo);
            delivery.setStatus(status);
            delivery.setCode(code);

            deliveryService.update(delivery);

            return "redirect:/manager/delivery-manage";
        }
        return "";
    }

    @GetMapping("/courier-manage")
    public String courierManage(Model model) {
        DataContainer dc = new DataContainer();
        dc.setCourierList(courierService.readList());
        model.addAttribute("dataContainer", dc);
        return "/manage/courier-list";
    }

    @GetMapping("delivery-courier-list")
    public String deliveryCourierList(Model model) {
        DataContainer dc = new DataContainer();
        dc.setCourierList(courierService.readList());
        model.addAttribute("dataContainer", dc);
        return "/manage/delivery-courier-list";
    }

    @GetMapping("/order-manage")
    public String orderManage(Model model) {
        model.addAttribute("orderList", orderService.readList());
        return "/manage/order-list";
    }

    @GetMapping("/delivery-manage")
    public String deliveryManage(Model model) {
        DataContainer dc = new DataContainer();

        dc.setPaymentList(paymentService.readList());
        dc.setDeliveryList(deliveryService.readList());
        dc.setCourierList(courierService.readList());

        model.addAttribute("dataContainer", dc);
        return "/manage/delivery-list";
    }
}
