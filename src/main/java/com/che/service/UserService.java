package com.che.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.che.dao.UserDao;
import com.che.model.vo.UserVo;
import com.che.utils.CommonUtils;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public UserVo getById(String id) {
		userDao.getClass();
		String password = "123456";
		String encode = CommonUtils.encode(password.getBytes());
		UserVo userVo = new UserVo(1 + "", "张三", encode);
		return userVo;
	}

}
