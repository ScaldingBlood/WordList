package com.xd.product.action;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;
import  com.alibaba.fastjson.JSON;

import com.xd.common.BaseAction;
import com.xd.product.domain.Word;
import com.xd.product.service.productService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
    public String store()
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
        return SUCCESS;
    }
    public String search()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        int parameter = Integer.parseInt(request.getParameter("parameter"));
        //String spelling = request.getParameter("spelling");
        HttpSession session = request.getSession();
        UUID id = (UUID)session.getAttribute("id");
        JSONArray result = new JSONArray();

        try {
            List xcz = this.getService().FindByParameter(id,parameter);
            if (xcz != null && xcz.size() > 0) {
                for (int i = 0; i < xcz.size(); i++) {
                    Object[] obj = (Object[]) xcz.get(i);
                    JSONObject p = new JSONObject();
                    p.put("id",id);
                    p.put("spelling",(String)obj[0]);
                    p.put("definition",(String)obj[1]);
                    p.put("sentences",(String)obj[2]);
                    p.put("date",((Date)obj[3]).getTime());//may provoke exception
                    result.add(p);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println(result.toJSONString());
            out.flush();
            out.close();
            return SUCCESS;

        }catch (IOException e)
        {
            e.printStackTrace();
            return ERROR;
        }
    }

}
