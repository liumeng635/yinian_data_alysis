package com.yinian.alysis.service;

import com.yinian.alysis.model.ActEffect;
import java.util.List;

public interface EffectService {
    List<ActEffect> findAllByPage(int pageNum, int pageSize);
}
