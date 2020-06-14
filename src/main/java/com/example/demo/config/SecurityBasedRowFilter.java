package com.example.demo.config;

import labs.psychogen.row.context.RowContextHolder;
import labs.psychogen.row.domain.RowWebsocketSession;
import labs.psychogen.row.domain.protocol.RequestDto;
import labs.psychogen.row.domain.protocol.ResponseDto;
import labs.psychogen.row.filter.RowFilter;
import labs.psychogen.row.repository.RowSessionRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SecurityBasedRowFilter implements RowFilter {
    private final RowSessionRegistry sessionRegistry;

    public SecurityBasedRowFilter(RowSessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }


    @Override
    public boolean filter(RequestDto requestDto, ResponseDto responseDto) throws Exception {
        String userId = RowContextHolder.getContext().getRowUser().getUserId();
        Assert.notNull(userId, "Context user id can not be null");
        RowWebsocketSession session = sessionRegistry.getSession(userId);
        Assert.notNull(session, "Session can not be null");
        Authentication authentication = (Authentication) session.getExtra();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}