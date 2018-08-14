package com.test.activiti.useridentity.security.impl;

import com.test.activiti.useridentity.security.CurrentUserService;
import com.test.activiti.useridentity.security.domain.UserProfile;
/**
 * Primitive implementation that returns hardcoded user.
 * 
 * @author mondhs
 *
 */
public class CurrentUserServiceStaticImpl implements CurrentUserService{

	UserProfile currentUser;
	
	public CurrentUserServiceStaticImpl() {
		currentUser = new UserProfile();
		currentUser.setLoginName("qa");
		currentUser.setFirstName("Qa");
		currentUser.setLastName("Qa");
		currentUser.setEmailAddress("qa@qa.lt");
	}
	
	@Override
	public UserProfile getCurrentUser() {
		return currentUser;
	}

}
