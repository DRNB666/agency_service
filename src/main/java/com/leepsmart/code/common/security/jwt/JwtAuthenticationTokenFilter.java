package com.leepsmart.code.common.security.jwt;

import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Token过滤器
 *
 * @author leepsmart
 * Created on 2018/8/1
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String tokenHead = "Bearer_";
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            try {
                String authToken = authHeader.substring(tokenHead.length());
                String username = JwtTokenUtil.getUsernameFromToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (JwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(401);
                response.getOutputStream().println(new String(ReturnBody.error(ResultCodeInfo.JWT_EXPIRE).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            } catch (UsernameNotFoundException e) {
                response.setStatus(401);
                response.getOutputStream().println(new String(ReturnBody.error(ResultCodeInfo.JWT_EXPIRE).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            }
        }
        chain.doFilter(request, response);
    }

}
