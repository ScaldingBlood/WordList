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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hp on 2016/6/11.
 */
public class productAction extends BaseAction{

    private productService Service;
    public productService getService() {
        return Service;
    }

    public void setService(productService service) {
        Service = service;
    }
    public void setWord(Word data, UUID id,String spelling,String definition,String sentences,Date date)
    {
        data.setId(id.toString());
        data.setSpelling(spelling);
        data.setDefinition(definition);
        data.setSentences(sentences);
        data.setDate(date);
    }
    public void store()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        UUID id = UUID.fromString((String) session.getAttribute("id"));
        String spelling = request.getParameter("spelling");
        String definition = request.getParameter("definition");
        String sentences = request.getParameter("sentences");
        java.util.Date utildate = new java.util.Date();
        Date date = new Date(utildate.getTime());
        Word data = new Word();
        setWord(data,id, spelling, definition, sentences, date);
        this.getService().add(data);
    }
    public void search()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ps = request.getParameter("parameter");
        int parameter = 0;
        if(ps !=null)
            parameter= Integer.parseInt(ps);
        String spelling = request.getParameter("spelling");
        String ts = request.getParameter("totalPage");
        int page = 0;
        if(ts != null)
            page= Integer.parseInt(ts);
        HttpSession session = request.getSession();
        UUID id = UUID.fromString((String) session.getAttribute("id"));
        JSONArray result = new JSONArray();
        if(spelling != null)
        {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String sql = "SELECT SPELLING,DEFINITION,SENTENCES,DATE FROM WORDLIST.WORD WHERE ID = '"+id.toString()+"'AND SPELLING = '"+spelling+"'";
            List xcz = this.getService().find(sql);
            if(xcz ==null&&xcz.isEmpty())
            {
                return;
            }
            else
            {
                JSONObject p = new JSONObject();
                Object[] obj = (Object[]) xcz.get(0);
                p.put("id", id);
                p.put("spelling", (String) obj[0]);
                p.put("definition", (String) obj[1]);
                p.put("sentences", (String) obj[2]);
                p.put("date", new Date(((Timestamp) obj[3]).getTime()).toString());

                try {
                    response.getWriter().append(p.toJSONString());
                    response.getWriter().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        else
        {
            try {
                List xcz = this.getService().FindByParameter(id,parameter,page-1);
                if (xcz != null && xcz.size() > 0) {
                    for (int i = 0; i < xcz.size(); i++) {
                        Object[] obj = (Object[]) xcz.get(i);
                        JSONObject p = new JSONObject();
                        p.put("id",id);
                        p.put("spelling",(String)obj[0]);
                        p.put("definition",(String)obj[1]);
                        p.put("sentences",(String)obj[2]);
                        p.put("date",new Date(((Timestamp)obj[3]).getTime()).toString());//may provoke exception
                        result.add(p);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        String JSONstr = result.toJSONString();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try
        {
            response.getWriter().append(JSONstr);
            response.getWriter().close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void update()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        UUID id = UUID.fromString((String) session.getAttribute("id"));
        String spelling = request.getParameter("spelling");
        String definition = request.getParameter("definition");
        String sentences = request.getParameter("sentences");
        java.util.Date utildate = new java.util.Date();
        Date date = new Date(utildate.getTime());
        Word data = new Word();
        setWord(data,id, spelling, definition, sentences, date);
        this.getService().update(data);
    }
    public void pagecount()
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        UUID id = UUID.fromString((String) session.getAttribute("id"));
        String sql = "SELECT SPELLING FROM WORDLIST.WORD WHERE ID = '"+id.toString()+"'";
        int page = this.getService().count(sql);
        JSONObject a = new JSONObject();
        a.put("totalPage",(page/10)+1);
        try
        {
            response.getWriter().append(a.toJSONString());
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
