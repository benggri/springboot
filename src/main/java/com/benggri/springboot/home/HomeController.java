package com.benggri.springboot.home;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name="home-controller", description="Home 컨트롤러")
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(
        HttpServletRequest request
    ) {
        return "html/index";
    }

}
