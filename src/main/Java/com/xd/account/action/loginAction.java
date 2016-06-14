package com.xd.account.action;

import com.opensymphony.xwork2.ActionContext;
import com.xd.account.domain.User;
import com.xd.account.service.LoginService;
import com.xd.common.BaseAction;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static java.security.AccessController.getContext;

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

    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        User user = loginService.check(name, password);
        if(user == null) {
            return ERROR;
        }
        else {
            request.getSession().setAttribute("id", user.getId());
            return SUCCESS;
        }
    }
    public String logout() {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getSession().removeAttribute("id");
        return SUCCESS;
    }

}
