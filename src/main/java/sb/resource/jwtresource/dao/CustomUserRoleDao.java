package sb.resource.jwtresource.dao;

import java.util.List;
import java.util.Map;

import sb.resource.jwtresource.Bean.UserResourceBean;

public interface CustomUserRoleDao {

	public List<UserResourceBean> findByUser(Map<String, String> mapCriteria);
}
