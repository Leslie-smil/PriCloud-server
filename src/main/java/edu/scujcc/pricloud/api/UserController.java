package edu.scujcc.pricloud.api;

import edu.scujcc.pricloud.ip.IpUtil;
import edu.scujcc.pricloud.model.Result;
import edu.scujcc.pricloud.model.User;
import edu.scujcc.pricloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author FSMG
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Result<User> createUser(@RequestBody User user) {
        logger.debug("注册用户 数据：" + user + " 时间：" + time);
        Result<User> result = new Result<>();
        User user1 = service.createUser(user);
        if (user1.getId() == null) {
            result.setStatus(Result.FAIL);
            result.setMessage("用户名已存在");
        } else {
            result.setStatus(Result.SUCCESS);
            result.setMessage("注册成功");
            result.setData(user1);
        }
        return result;
    }

    @PutMapping
    public Result<User> updateUser(@RequestBody User user) {
        logger.debug("更新User" + user + "时间" + time);
        User user1 = service.updateUser(user);
        Result<User> result = new Result<>();
        result.setStatus(Result.SUCCESS);
        result.setMessage("更新成功");
        result.setData(user1);
        return result;
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user, HttpServletRequest request) {
        logger.debug("收到用户：" + user.getUsername() + "登录请求 时间：" + time);
        Result<User> result = new Result<>();
        User user1 = service.login(user, IpUtil.getIp(request));
        if (user1.getId() != null) {
            result.setStatus(Result.SUCCESS);
            result.setMessage("登录成功");
            result.setData(user1);
        } else {
            result.setStatus(Result.FAIL);
        }
        return result;
    }

}
