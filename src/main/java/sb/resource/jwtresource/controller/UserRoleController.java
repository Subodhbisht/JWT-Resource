package sb.resource.jwtresource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sb.resource.jwtresource.Bean.UserResourceBean;
import sb.resource.jwtresource.entity.UserRole;
import sb.resource.jwtresource.model.UserRoleModel;
import sb.resource.jwtresource.model.UserSearchCriteria;
import sb.resource.jwtresource.service.UserRoleService;

@RestController
public class UserRoleController {

	@Autowired
	UserRoleService userRoleService;
	
	@PostMapping("/saveUserRole")
	public UserRole save(@RequestBody UserRoleModel model) {
		return userRoleService.save(model.getUserId(),model.getRoleId());
	}
	
	@GetMapping("/userRoles")
	public List<UserResourceBean> getUserRoles(@RequestBody UserSearchCriteria criteria){
		return userRoleService.findByUser(criteria);
	}
}
