package sb.resource.jwtresource.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import sb.resource.jwtresource.entity.User;


public interface UserInfoDao extends JpaRepository<User, Long>, CustomUserInfoDao{
	
	public User findByUserName(String userName);
}
