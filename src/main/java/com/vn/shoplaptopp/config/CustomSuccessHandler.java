package com.vn.shoplaptopp.config;

import com.vn.shoplaptopp.domain.User;
import com.vn.shoplaptopp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String targetUrl = determineTargetUrl(authentication, request);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected String determineTargetUrl(final Authentication authentication, final HttpServletRequest request) {

        // Bản đồ chứa các URL tương ứng với từng role
        Map<String, String> roleTargetUrlMap = new HashMap<>();

        // Lấy thông tin SavedRequest từ session
        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : null;

        if (redirectUrl != null) {
            // Nếu người dùng có ROLE_USER, chuyển hướng đến URL trước đó
            roleTargetUrlMap.put("ROLE_USER", redirectUrl);
        } else {
            // Mặc định ROLE_USER chuyển hướng đến trang chủ
            roleTargetUrlMap.put("ROLE_USER", "/");
        }

        // ROLE_ADMIN chuyển hướng đến trang admin
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        // Duyệt qua danh sách quyền của người dùng
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        // Nếu không tìm thấy role tương ứng, ném ra lỗi
        throw new IllegalStateException("User role không hợp lệ!");
    }


    protected void clearAuthenticationAttributes(HttpServletRequest request, final Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        User user = this.userService.getUserByEmail(authentication.getName());
        if (user != null) {
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("id", user.getId());
            int sumOfCart = user.getCart() == null ? 0 : user.getCart().getSum();
            session.setAttribute("sumOfCart", sumOfCart);
        }

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request, authentication);
    }
}
