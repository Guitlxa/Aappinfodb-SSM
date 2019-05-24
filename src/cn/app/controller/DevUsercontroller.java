package cn.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.app.pojo.DevUser;
import cn.app.service.DevUserService;

@Controller
@RequestMapping("/devUser")
public class DevUsercontroller {
	private Logger logger=Logger.getLogger(DevUsercontroller.class);
	
	@Resource
	private DevUserService devUserService;
	
	/**
	 * 登录跳转页面
	 * @return
	 */
	@RequestMapping("/loginT")
	public String loginT(){
		return "devlogin";
	}
	
	/**
	 * 登录
	 * @param devCode
	 * @param devPassword
	 * @param session
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@RequestParam("devCode") String devCode, 
			@RequestParam("devPassword") String devPassword,
			HttpSession session){
		DevUser devUser=devUserService.login(devCode, devPassword);
		if(devUser!=null){
			session.setAttribute("devUserSession", devUser);
			return "developer/main";
		}else {
			return "devlogin";
		}
	}
	
	/**
	 * 注销账户
	 */
	@RequestMapping("/loginout")
	public String loginout(HttpSession session){
		// 销毁session
		session.removeAttribute("devUserSession");
		return "devlogin";
	}
}
