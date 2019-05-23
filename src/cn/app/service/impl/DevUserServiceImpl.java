package cn.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.app.dao.DevUserMapper;
import cn.app.pojo.DevUser;
import cn.app.service.DevUserService;

@Service("devUserService")
public class DevUserServiceImpl implements DevUserService{

	@Resource
	private DevUserMapper devusermapper;
	
	@Override
	public DevUser login(String devCode, String devPassword) {
		return devusermapper.login(devCode, devPassword);
	}

}
