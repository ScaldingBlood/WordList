package com.xd.common;

/**
 * Created by oasis on 2016/6/11.
 */
public class BaseService {
    private HibernateDAO hibernateDAO;

    public HibernateDAO getHibernateDAO() {
        return hibernateDAO;
    }

    public void setHibernateDAO(HibernateDAO hibernateDAO) {
        this.hibernateDAO = hibernateDAO;
    }
}
