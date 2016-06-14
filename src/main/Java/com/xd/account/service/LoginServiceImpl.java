package com.xd.account.service;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import com.xd.account.domain.User;
import com.xd.common.BaseService;

import java.util.List;
import java.util.UUID;

/**
 * Created by oasis on 2016/6/11.
 */
public class LoginServiceImpl extends BaseService implements LoginService {
    @Override
    public User check(String name, String password) {
        String hql = "from com.xd.account.domain.User where username='" + name + "' and password='" + password + "'";
        List list = getHibernateDAO().find(hql);
        if(list != null && list.size() > 0)
            return (User)list.get(0);
        else
            return null;
    }
}
