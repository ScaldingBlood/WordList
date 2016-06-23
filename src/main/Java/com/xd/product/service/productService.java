package com.xd.product.service;

import com.xd.product.domain.Word;

import java.util.List;
import java.util.UUID;

/**
 * Created by hp on 2016/6/11.
 */
public interface productService {
    void add(Word a);
    List find(String sql);
    List FindByParameter(UUID id, int parameter,int page);
    void update(Word a);
    int count(String sql);
}
