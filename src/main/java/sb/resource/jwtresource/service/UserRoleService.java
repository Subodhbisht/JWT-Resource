package sb.resource.jwtresource.service;

import java.util.List;

import sb.resource.jwtresource.Bean.UserResourceBean;
import sb.resource.jwtresource.entity.UserRole;
import sb.resource.jwtresource.model.UserSearchCriteria;

public interface UserRoleService {

	public UserRole save(Long userId,Long roleId);
	
	public List<UserResourceBean> findByUser(UserSearchCriteria searchCriteria);
}
