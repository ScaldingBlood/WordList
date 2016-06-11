package com.xd.product.service;

import com.xd.common.BaseService;
import com.xd.product.domain.Word;

/**
 * Created by hp on 2016/6/11.
 */
public class productServiceImpl extends BaseService implements productService{
    @Override
    public void add(Word wd) {

        this.getHibernateDAO().save(wd);
    }
}
