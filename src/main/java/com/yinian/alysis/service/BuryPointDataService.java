package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;

import com.yinian.alysis.model.ThemeAlbumAnalysis;

public interface BuryPointDataService {
	List<ThemeAlbumAnalysis> findByMapParam(Map<String,Object> params);    
}
