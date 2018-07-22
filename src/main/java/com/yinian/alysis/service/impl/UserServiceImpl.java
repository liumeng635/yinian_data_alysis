package com.yinian.alysis.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yinian.alysis.model.User;
import com.yinian.alysis.model.UserMapper;
import com.yinian.alysis.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	 @Autowired
	 UserMapper userMapper;

	@Override
	public List<User> getUserByMapParam(Map<String, Object> params) {
		return userMapper.getUserByMapParam(params);
	}
	
	

}
