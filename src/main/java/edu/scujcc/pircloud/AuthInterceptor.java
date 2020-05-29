package edu.scujcc.pircloud;

import edu.scujcc.pircloud.model.User;
import edu.scujcc.pircloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author FSMG
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private UserService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean logged = false;
        String target = request.getRequestURI();
        if (null != target && target.startsWith(User.U)) {
            return true;
        }
//把出错界面也排除在保护之外
        if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            return true;
        }
        String token = request.getHeader("token");
        logger.debug("携带的token是:" + token);
        if (token != null) {
            int status = service.getToken(token);
            if (status == 1) {
                logged = true;
                logger.debug("token" + token + "允许访问" + request.getRequestURI());
            }
        }
        if (!logged) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "未登录");
        }
        return logged;
    }
}