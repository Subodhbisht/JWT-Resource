package sb.resource.jwtresource.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.model.UserSearchCriteria;

public interface UserService extends UserDetailsService {

	public User save(User user);

	public User update(User user);

	public void delete(String userName);
	
	public List<User> findUsers(UserSearchCriteria searchCriteria);
	
	public User getUser(String userName);
	
	
}
