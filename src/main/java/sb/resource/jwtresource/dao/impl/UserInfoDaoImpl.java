package sb.resource.jwtresource.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sb.resource.jwtresource.dao.CustomUserInfoDao;
import sb.resource.jwtresource.entity.Role;

public class UserInfoDaoImpl implements CustomUserInfoDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Role> findRoleByUserName(String userName) {
		System.out.println("Inside findRoleByUserName---------");
		return entityManager.createQuery(
				"Select roles From UserRole ur inner join ur.user users inner join ur.role roles where users.userName=:username")
				.setParameter("username", userName).getResultList();
	}
}
