package com.pyg.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbSeller;
import com.pyg.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServcieImpl implements UserDetailsService{
    @Reference
    private SellerService sellerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        TbSeller seller = sellerService.queryOne(username);
        if(seller!=null&&"1".equals(seller.getStatus())){
            return new User(username,seller.getPassword(),authorities);
        }
        return null;
    }

    /*public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }*/
}

