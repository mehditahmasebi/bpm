package com.test.activiti.useridentity.security;

import com.test.activiti.useridentity.security.domain.UserProfile;
/**
 * interface returns user information which is already currently connected to the system.
 * It is used from {@link CustomDefaultLoginHandler}
 * @author mondhs
 *
 */
public interface CurrentUserService {

	UserProfile getCurrentUser();

}
