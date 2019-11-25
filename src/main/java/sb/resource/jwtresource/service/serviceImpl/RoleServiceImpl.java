package sb.resource.jwtresource.service.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sb.resource.jwtresource.dao.RoleDao;
import sb.resource.jwtresource.entity.Role;
import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleDao roleRepo;
	
	private User principal;

	@Override
	public Role save(Role role) {
		principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("Created By :"+principal.getUserId()+" "+principal.getUserName());
		role.setRecordCount(0L);
		role.setCreatedBy(principal.getUserId());
		role.setCreatedOn(new Date());
		return roleRepo.save(role);
	}

	@Override
	public Role update(Role role) {
		principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		role.setRecordCount(role.getRecordCount()+1);
		role.setModifiedBy(principal.getUserId());
		role.setModifiedOn(new Date());
		return roleRepo.save(role);
	}

	@Override
	public void delete(String roleName) {
		roleRepo.deleteByRoles(roleName);
	}

	@Override
	public Role findByName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepo.findByroles(roleName);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

}
