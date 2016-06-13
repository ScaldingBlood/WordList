package com.xd.account.service;

import com.xd.account.domain.User;

/**
 * Created by oasis on 2016/6/11.
 */
public interface LoginService {
    User check(String name, String password);
}
