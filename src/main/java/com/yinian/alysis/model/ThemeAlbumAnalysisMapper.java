package com.yinian.alysis.model;

import java.util.List;
import java.util.Map;

public interface ThemeAlbumAnalysisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThemeAlbumAnalysis record);

    int insertSelective(ThemeAlbumAnalysis record);

    ThemeAlbumAnalysis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThemeAlbumAnalysis record);

    int updateByPrimaryKey(ThemeAlbumAnalysis record);
    
    List<ThemeAlbumAnalysis> findByMapParam(Map<String, Object> params);
}