package iducs.springboot.ab200412000.comsoblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ThymeleafController {
    @GetMapping("/thtext")  //url주소
    public String gothText(){
        return "th-text"; // th-text.html 파일을 view or 텝플릿으로 사용함
    }

    @GetMapping("")  //url주소
    public String gohome(HttpServletRequest request, HttpServletResponse response){
        return "main/index"; //index.html 파일을 view or 텝플릿으로 사용함
    }

}
