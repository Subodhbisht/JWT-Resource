package sb.resource.jwtresource.service;

import java.util.List;

import sb.resource.jwtresource.entity.Role;

public interface RoleService {

	public Role save(Role role);

	public Role update(Role role);

	public void delete(String roleName);
	
	public Role findByName(String roleName);
	
	public List<Role> findAll();
}
