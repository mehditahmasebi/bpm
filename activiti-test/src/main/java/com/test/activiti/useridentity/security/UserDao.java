package com.test.activiti.useridentity.security;

import java.util.List;

import com.test.activiti.useridentity.security.domain.UserProfile;
import com.test.activiti.useridentity.spring.CustomUserEntityManager;
/**
 * Simplified representation of custom User management DAO service.
 * It is called by {@link CustomUserEntityManager}. 
 * @author mondhs
 *
 */
public interface UserDao {

	List<UserProfile> findAll();

	UserProfile findUserByLoginName(String id);

}
