package cn.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.app.constant.Constants;
import cn.app.pojo.DevUser;


//权限拦截器
public class SysInterceptor implements HandlerInterceptor {

	//在执行最终请求之前，先进入到preHandle方法中
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//进行用户登录的验证
		System.out.println("拦截器");
		//得到保存到Session作用域中用户对象信息
		DevUser user = (DevUser)request.getSession().getAttribute(Constants.USER_SESSION);
		if (user == null) {  //如果用户没有登录，那么就重定向到403.jsp页面
			response.sendRedirect(request.getContextPath() + "/403.jsp");
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
