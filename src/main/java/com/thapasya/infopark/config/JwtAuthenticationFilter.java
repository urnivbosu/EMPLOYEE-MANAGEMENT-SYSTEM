//package com.thapasya.infopark.config;
//
//import com.thapasya.infopark.util.JwtTokenUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtTokenUtil jwtTokenUtil;
//
//    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
////            throws ServletException, IOException {
////
////        // Get the Authorization header and extract the token
////        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
////        if (token != null && token.startsWith("Bearer ")) {
////            token = token.substring(7); // Remove "Bearer " prefix
////
////            String username = jwtTokenUtil.extractUsername(token);
////            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////                // Validate the token using the username (you could also load user details here if needed)
////                if (jwtTokenUtil.validateToken(token, username)) {
////                    UsernamePasswordAuthenticationToken authentication =
////                            new UsernamePasswordAuthenticationToken(username, null, null);
////                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                    SecurityContextHolder.getContext().setAuthentication(authentication);
////                }
////            }
////        }
////        chain.doFilter(request, response);
////    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Remove "Bearer " prefix
//
//            String username = jwtTokenUtil.extractUsername(token);
//            String role = jwtTokenUtil.extractRole(token); // Extract role from token
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                if (jwtTokenUtil.validateToken(token, null)) {
//                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(username, null, authorities);
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//}
