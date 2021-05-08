package iducs.springboot.ab200412000.comsoblog.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class NoRestController {
    @RequestMapping("/apis")
    public String api() {
        return "api.html";
    }
}
