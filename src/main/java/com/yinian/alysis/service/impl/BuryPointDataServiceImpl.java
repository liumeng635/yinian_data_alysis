package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.ThemeAlbumAnalysis;
import com.yinian.alysis.model.ThemeAlbumAnalysisMapper;
import com.yinian.alysis.service.BuryPointDataService;

@Service
public class BuryPointDataServiceImpl implements BuryPointDataService{
    @Autowired
    ThemeAlbumAnalysisMapper themeMapper;

	@Override
	public List<ThemeAlbumAnalysis> findByMapParam(Map<String, Object> params) {
		return themeMapper.findByMapParam(params);
	}
    
    
}
