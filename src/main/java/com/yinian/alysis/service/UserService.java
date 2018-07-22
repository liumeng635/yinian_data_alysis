package com.yinian.alysis.service;

import java.util.List;
import java.util.Map;
import com.yinian.alysis.model.User;

public interface UserService {
	List<User> getUserByMapParam(Map<String,Object> params);
}
