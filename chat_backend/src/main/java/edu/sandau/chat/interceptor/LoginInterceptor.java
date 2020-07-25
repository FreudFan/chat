package edu.sandau.chat.interceptor;

import edu.sandau.chat.entity.user.User;
import edu.sandau.chat.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // 手动维护在线用户
            redisUtil.putUser(user);
            RequestContent.add(user);
            return true;
        } else {
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(401);
            PrintWriter pw = response.getWriter();
            pw.write("请先登陆哦~");
            pw.flush();
            pw.close();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestContent.remove();
    }
}
