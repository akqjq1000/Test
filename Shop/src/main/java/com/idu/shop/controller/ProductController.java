package com.idu.shop.controller;

import com.idu.shop.domain.DataContainer;
import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.category.mCategory;
import com.idu.shop.domain.category.sCategory;
import com.idu.shop.domain.option.Option;
import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.domain.order.Review;
import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductOption;
import com.idu.shop.service.category.sCategoryService;
import com.idu.shop.service.category.mCategoryService;
import com.idu.shop.service.category.bCategoryService;
import com.idu.shop.service.member.MemberService;
import com.idu.shop.service.option.OptionService;
import com.idu.shop.service.option.OptionValueService;
import com.idu.shop.service.order.ReviewService;
import com.idu.shop.service.product.ProductImageService;
import com.idu.shop.service.product.ProductOptionService;
import com.idu.shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    MemberService memberService;

    ProductService productService;
    ProductOptionService productOptionService;
    ProductImageService productImageService;

    bCategoryService bCategoryService;
    mCategoryService mCategoryService;
    sCategoryService sCategoryService;

    OptionService optionService;
    OptionValueService optionValueService;

    ReviewService reviewService;
    @Autowired
    public ProductController(MemberService memberService,
                             ProductService productService,
                             ProductOptionService productOptionService,
                             ProductImageService productImageService,
                             bCategoryService bCategoryService,
                             mCategoryService mCategoryService,
                             sCategoryService sCategoryService,
                             OptionService optionService,
                             OptionValueService optionValueService,
                             ReviewService reviewService) {
        this.memberService = memberService;
        this.productService = productService;
        this.productOptionService = productOptionService;
        this.productImageService = productImageService;
        this.bCategoryService = bCategoryService;
        this.mCategoryService = mCategoryService;
        this.sCategoryService = sCategoryService;
        this.optionService = optionService;
        this.optionValueService = optionValueService;
        this.reviewService = reviewService;
    }

    @GetMapping("/product-detail")
    public String productDetail(HttpServletRequest request, Model model) {
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        Product product = productService.readById(productNo);
        product.setId(productNo);
        model.addAttribute("product", product);

        List<Option> options = null;
        List<List<OptionValue>> optionValues = null;
        List<ProductOption> productOptions = productOptionService.readByProductNoList(productNo);
        if (productOptions != null) {
            options = new ArrayList<>();
            optionValues = new ArrayList<>();
            for (ProductOption po : productOptions) {
                options.add(optionService.readByOptionNo(po.getOptionNo()));
                optionValues.add(optionValueService.readByOptionNo(po.getOptionNo()));
            }
        }

        bCategory bCategory = bCategoryService.read(product.getBCateNo());
        mCategory mCategory = mCategoryService.read(product.getMCateNo());
        sCategory sCategory = sCategoryService.read(product.getSCateNo());

        String ret = "";
        if (bCategory != null) {
            ret += bCategory.getName();
            if (mCategory != null) ret += ">";
        }
        if (mCategory != null) {
            ret += mCategory.getName();
            if (sCategory != null) ret += ">";
        }
        if (sCategory != null) {
            ret += sCategory.getName();
        }

        model.addAttribute("optionList", options);
        model.addAttribute("optionValueList", optionValues);
        model.addAttribute("category", ret);
        model.addAttribute("reviewList", reviewService.readProductNoList(productNo));
        model.addAttribute("productImageList", productImageService.readByProductNoList(productNo));
        return "/product/product-detail";
    }

    @GetMapping("/product-list")
    public String productList(HttpServletRequest request, Model model) {
        String menu = request.getParameter("menu");
        DataContainer dc = new DataContainer();
        if (menu.equals("search")) {
            String keyword = request.getParameter("keyword");

            model.addAttribute("keyword", keyword);
            model.addAttribute("searchProductList", productService.searchByProductName(keyword));
        } else if (menu.equals("category")) {
            int bCateNo = Integer.parseInt(request.getParameter("bCateNo"));

            dc.setBCategory(bCategoryService.read(bCateNo));
            dc.setMCategoryList(mCategoryService.readListByBCateNo(bCateNo));

            model.addAttribute("searchProductList", productService.readListByBCateNo(bCateNo));
        } else if (menu.equals("sub-category")) {
            int bCateNo = Integer.parseInt(request.getParameter("bCateNo"));
            int mCateNo = Integer.parseInt(request.getParameter("mCateNo"));

            dc.setBCategory(bCategoryService.read(bCateNo));
            dc.setMCategory(mCategoryService.read(mCateNo));
            dc.setMCategoryList(mCategoryService.readListByBCateNo(bCateNo));

            model.addAttribute("searchProductList", productService.readListByMCateNo(mCateNo));
        }
        model.addAttribute("dataContainer", dc);
        return "/product/product-list";
    }

    @PostMapping("/review")
    public String review(HttpServletRequest request) {
        String reviewComments = request.getParameter("review");
        int productNo = Integer.parseInt(request.getParameter("productNo"));
        int ratio = Integer.parseInt(request.getParameter("rating"));
        Review review = new Review();
        review.setProductNo(productNo);
        review.setMemberNo(memberService.getId(request.getSession().getAttribute("loginEmail").toString()));
        review.setComments(reviewComments);
        review.setRatio(ratio);

        Product product = productService.readById(productNo);

        int totalRatio = 0;
        for (Review re : reviewService.readProductNoList(productNo)) {
            totalRatio += re.getRatio();
        }

        totalRatio = totalRatio / ((reviewService.readProductNoList(productNo).size() == 0) ? 1 : reviewService.readProductNoList(productNo).size());

        product.setRatio(totalRatio);
        productService.updateToRatio(product);

        reviewService.insert(review);

        return "redirect:/products/product-detail?productNo="+productNo;
    }
}
