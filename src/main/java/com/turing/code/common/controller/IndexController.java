package com.turing.code.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 首页默认跳转Swagger注解
 */
@Controller
@ApiIgnore
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "redirect:/doc.html";
    }

}
