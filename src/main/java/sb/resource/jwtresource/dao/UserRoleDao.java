package sb.resource.jwtresource.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sb.resource.jwtresource.entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Long>,CustomUserRoleDao {

	
}
