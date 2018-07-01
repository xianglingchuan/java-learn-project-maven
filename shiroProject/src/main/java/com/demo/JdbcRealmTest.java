package com.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
//import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcRealmTest {
	
	DruidDataSource dataSource = new DruidDataSource();
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/shiroDate");
		dataSource.setUsername("root");
		dataSource.setPassword("111111");
	}

//	@Test
	public void testAuthentication(){
		
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		//打开jdbc查询权限的开头，默认是false
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//defaultSecurityManager.setRealm(iniRealm);
		defaultSecurityManager.setRealm(jdbcRealm);
		
		//2、主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
		//用户名错误异常
		//org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.SimpleAccountRealm@5479e3f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - Make1, rememberMe=false].
		//密码错误异常
		//org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - Make, rememberMe=false] did not match the expected credentials.
		subject.login(token);
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		//验证角色
		//subject.checkRoles("admin");
		subject.checkRoles("admin","user");
		
		//验证功能
		subject.checkPermissions("user:select", "user:create");
		
	}	
	
}