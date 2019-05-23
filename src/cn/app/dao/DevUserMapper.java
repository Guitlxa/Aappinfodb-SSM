package cn.app.dao;

import org.apache.ibatis.annotations.Param;

import cn.app.pojo.DevUser;

public interface DevUserMapper {
	
	/**
	 * 登录
	 * @param devCode 编码
	 * @param devName 密码
	 * @return
	 */
	DevUser login(@Param("devCode") String devCode,
			@Param("devPassword") String devPassword);
}
