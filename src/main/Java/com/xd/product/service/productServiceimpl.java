package com.xd.product.service;

import com.xd.common.BaseService;
import com.xd.product.domain.Word;

import java.util.List;
import java.util.UUID;

/**
 * Created by hp on 2016/6/11.
 */
public class productServiceImpl extends BaseService implements productService{
    @Override
    public void add(Word wd) {

        this.getHibernateDAO().save(wd);
    }
    @Override
    public void update(Word wd){
        this.getHibernateDAO().update(wd);
    }

    @Override
    public List find(String sql)
    {
        return this.getHibernateDAO().findBySql(sql);
    }
    @Override
    public List FindByParameter(UUID id, int parameter,int page)
    {
            if(parameter ==0)
            {
                String sql1 = "SELECT SPELLING,DEFINITION,SENTENCES,DATE FROM WORDLIST.WORD WHERE ID = '"+id.toString()+"' ORDER BY DATE DESC LIMIT "+page+",10";
//                System.out.println(sql1);
                return find(sql1);
            }
            else if(parameter == 1)
            {
                String sql2 = "SELECT SPELLING,DEFINITION,SENTENCES,DATE FROM WORDLIST.WORD WHERE ID = '"+id.toString()+"' ORDER BY SPELLING LIMIT "+page+",10";
                return find(sql2);
            }
            else
                return null;
    }

    public int count(String sql)
    {
        return this.getHibernateDAO().findCountBySql(sql);
    }

}
