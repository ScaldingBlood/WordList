package com.xd.account.action;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.xd.account.domain.User;
import com.xd.account.service.LoginService;
import com.xd.common.BaseAction;
import org.apache.commons.io.IOExceptionWithCause;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by oasis on 2016/6/11.
 */
public class LoginAction extends BaseAction {
    private User user;
    private LoginService loginService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        User user = loginService.check(name, password);
        if(user != null) {
            JSONObject json = new JSONObject();
            json.put("name", user.getName());
            request.getSession().setAttribute("id", user.getId());
            try {
                response.getWriter().write(json.toJSONString());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String logout() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().removeAttribute("id");
        return SUCCESS;
    }

}
