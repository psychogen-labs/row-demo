package com.example.demo.config;

import labs.psychogen.row.domain.WebsocketUserData;
import labs.psychogen.row.exception.AuthenticationFailedException;
import labs.psychogen.row.ws.RowHandshakeAuthHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("rowHandshakeAuthHandler")
public class SecurityBasedHandshakeAuthHandler implements RowHandshakeAuthHandler {
    @Override
    public WebsocketUserData handshake(String token) throws AuthenticationFailedException {
        Object auth = null;
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if(token.equals("adminToken")){
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            auth = new UsernamePasswordAuthenticationToken("admin", "admin", grantedAuthorityList);
        }else if(token.equals("userToken")){
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
            auth = new UsernamePasswordAuthenticationToken("user", "user", grantedAuthorityList);
        }
        return new WebsocketUserData(UUID.randomUUID().toString(), auth);
    }
}
