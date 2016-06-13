package com.xd.account.service;

import com.xd.account.domain.User;
import com.xd.common.BaseService;

import java.util.UUID;

/**
 * Created by oasis on 2016/6/13.
 */
public class RegisterServiceImpl extends BaseService implements RegisterService{
    public boolean addUser(User user) {
        try {
            getHibernateDAO().save(user);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
