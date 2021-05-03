package sb.resource.jwtresource.dao;

import java.util.List;
import java.util.Map;

import sb.resource.jwtresource.entity.Role;
import sb.resource.jwtresource.entity.User;

public interface CustomUserInfoDao {
	
	List<Role> findRoleByUserName(String userName);
}
