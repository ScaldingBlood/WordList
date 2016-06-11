package com.xd.product.action;

import com.xd.common.BaseAction;
import com.xd.product.domain.Word;
import com.xd.product.service.productService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.UUID;

/**
 * Created by hp on 2016/6/11.
 */
public class productAction extends BaseAction{

    private productService Service;
    private Word data;

    public productService getService() {
        return Service;
    }

    public void setService(productService service) {
        Service = service;
    }
    public void setWord( UUID id,String spelling,String definition,String sentences,Date date)
    {
        data.setId(id);
        data.setSpelling(spelling);
        data.setDefinition(definition);
        data.setSentences(sentences);
        data.setDate(date);
    }
    public void store()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        UUID id = UUID.fromString(request.getParameter("id"));
        String spelling = request.getParameter("spelling");
        String definition = request.getParameter("definition");
        String sentences = request.getParameter("sentences");
        java.util.Date utildate = new java.util.Date();
        Date date = new Date(utildate.getTime());
        setWord(id, spelling, definition, sentences, date);
        this.getService().add(data);
    }

}
