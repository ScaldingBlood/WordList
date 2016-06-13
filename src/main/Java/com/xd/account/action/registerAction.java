package com.xd.account.action;

import com.xd.account.domain.User;
import com.xd.account.service.RegisterService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;

/**
 * Created by oasis on 2016/6/11.
 */
public class RegisterAction {
    private User user;
    private RegisterService registerService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RegisterService getRegisterService() {
        return registerService;
    }

    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    public String register() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        user = new User(UUID.randomUUID().toString(), name, password);
        if(registerService.addUser(user)) {
            request.getSession().setAttribute("id", user.getId());
            return SUCCESS;
        }
        else {
            return ERROR;
        }
    }
}
