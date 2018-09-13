package com.pyg.manage.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    @GetMapping("getUsername")
    public Map<String,Object> getUserName(){
        Map<String,Object> result=new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        result.put("username",name);
        return result;
    }
}
