package com.xd.product.domain;

import com.xd.common.BaseBean;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by oasis on 2016/6/11.
 */
public class Word extends BaseBean{
    UUID id;
    String spelling;
    String definition;
    String sentences;
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

}
