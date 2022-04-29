package com.lh.blog.service;

import com.lh.blog.po.Label;

import java.util.List;

public interface EssayLabelService {

    int insertBatch(Long essayId, List<Label> labels);
}
