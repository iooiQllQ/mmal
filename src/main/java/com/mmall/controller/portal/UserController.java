package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping("login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @GetMapping("login.out")
    @ResponseBody
    public ServerResponse<String> login(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @PostMapping("register.do")
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 填写完一个input框，填写下一个input框时调用的实时反馈；
     *
     * @param str
     * @param type
     * @return
     */
    @GetMapping("check_valid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户有信息
     * @param session
     * @return
     */
    @PostMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }

    /**
     * 密保问题
     * @param username
     * @return
     */
    @GetMapping("forget_get_question.do")
    @ResponseBody
    public ServerResponse<String> forgerGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    public ServerResponse<String> forgerCheckAnswer(String username,String question,String answer){
        return iUserService.selectQuestion(username);
    }

    

}