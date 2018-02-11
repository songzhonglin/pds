/**
 * 
 */
package com.fykj._b._core.login.service.impl;

import com.fykj._b._core.sysuser.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.login.service.LoginService;
import com.fykj._b._core.sysuser.UserCodesTable;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.security.SecurityService;
import com.fykj.kernel._c.service.ServiceSupport;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class LoginServiceImpl extends ServiceSupport implements LoginService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SecurityService securityService;

	@Override
	public SessionUser userLogin(SysUser sysUser) {
		String user_account = sysUser.getUserAccount();
		String password = sysUser.getPassword();

		SysUser dbUser = sysUserService.getSysUserByAccount(user_account);

		if (null == dbUser) {
			throw new BusinessException("用户账号或者密码错误!");
		}

		// 密码正确
		if (securityService.encriptyByMD5(password).equals(dbUser.getPassword())) {
			if (UserCodesTable.UserState.DISABLE.equals(dbUser.getDisabled())) {
				throw new BusinessException("用户账号已禁用,请联系系统管理员!");
			}
			String userId = dbUser.getId();
			// 用户信息
			SessionUser user = new SessionUser();
			user.setId(userId);
			user.setUserName(dbUser.getUserAccount());
			user.setNatureName(dbUser.getName());
			return user;

		} else {
			throw new BusinessException("用户账号或者密码错误!");
		}
	}

	@Override
	public SessionUser userLogin(String userName) {
		SessionUser user = new SessionUser();
		SysUser dbUser = sysUserService.getSysUserByAccount(userName);
		if (null == dbUser) {
			user.setErrorCode("DL003");
			user.setErrorMessage("用户账号未同步，请联系管理员!");
			return user;
		}
		// 密码正确
		if (UserCodesTable.UserState.DISABLE.equals(dbUser.getDisabled())) {
			user.setErrorCode("DL004");
			user.setErrorMessage("用户账号已禁用,请联系系统管理员!");
			return user;
		}
		String userId = dbUser.getId();
		// 用户信息
		user.setId(userId);
		user.setUserName(dbUser.getUserAccount());
		user.setNatureName(dbUser.getName());
		return user;
	}

}
