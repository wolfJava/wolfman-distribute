package com.wolfman.distribute.session;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(HttpServletRequest request){
        request.getSession().setAttribute("huhao","aaa");
        return "success";
    }

    @GetMapping("/getValue")
    public String getValue(HttpServletRequest request){
        String str = (String) request.getSession().getAttribute("huhao");
        return str;
    }



}
