package com.fykj.pds.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fykj.kernel._Cfg;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private _Cfg cfg;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURL().toString();
		System.out.println(url);
		String ssotoken=request.getHeader("_redis_token");
		String token=request.getHeader("_token");
		String authortoken=request.getParameter("_author_token");
		String resultUrl="{\"code\":\"0001\",\"url\":\""+cfg.getSso().getHost()+cfg.getSso().getLoginUrl()+"\"}";
		//本地和远程ssoToken 都没有跳转登陆
		if(StringUtils.isBlank(ssotoken) && StringUtils.isBlank(token) && StringUtils.isBlank(authortoken)){
			response.getWriter().write(resultUrl);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
