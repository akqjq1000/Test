package com.idu.shop.controller;

import com.idu.shop.domain.DataContainer;
import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.service.category.bCategoryService;
import com.idu.shop.service.category.mCategoryService;
import com.idu.shop.service.category.sCategoryService;
import com.idu.shop.service.member.MemberService;
import com.idu.shop.service.option.OptionService;
import com.idu.shop.service.option.OptionValueService;
import com.idu.shop.service.product.ProductImageService;
import com.idu.shop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    bCategoryService bCategoryService;
    mCategoryService mCategoryService;
    sCategoryService sCategoryService;
    OptionService optionService;
    OptionValueService optionValueService;
    ProductImageService productImageService;
    ProductService productService;
    @Autowired
    public HomeController(bCategoryService bCategoryService, mCategoryService mCategoryService,
                          sCategoryService sCategoryService,
                          OptionService optionService, OptionValueService optionValueService,
                          ProductImageService productImageService, ProductService productService) {
        this.bCategoryService = bCategoryService;
        this.mCategoryService = mCategoryService;
        this.productService = productService;
        this.optionService = optionService;
        this.optionValueService = optionValueService;
        this.sCategoryService = sCategoryService;
        this.productImageService = productImageService;
    }

    @GetMapping("")
    @ApplicationScope
    public String home(HttpServletRequest request) {
        ServletContext application = request.getServletContext();
        DataContainer dc = new DataContainer();
        dc.setBCategoryList(bCategoryService.readList());
        dc.setMCategoryList(mCategoryService.readList());
        dc.setOptionList(optionService.readList());
        dc.setOptionValueList(optionValueService.readList());
        dc.setProductList(productService.readList());
        dc.setProductImageList(productImageService.readList());
        application.setAttribute("dataContainer", dc);
        return "/main/index";
    }
}
