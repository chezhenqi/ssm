package com.che.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.che.model.vo.UserVo;
import com.che.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getUsername")
	@ResponseBody
	public String getUsername() {
		return "test";
	}

	@RequestMapping("/getById")
	@ResponseBody
	public UserVo getById(String id) {
		UserVo userVo = userService.getById(id);
		return userVo;
	}
}
