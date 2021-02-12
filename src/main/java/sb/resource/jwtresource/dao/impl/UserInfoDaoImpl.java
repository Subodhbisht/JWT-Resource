package sb.resource.jwtresource.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import sb.resource.jwtresource.dao.CustomUserInfoDao;
import sb.resource.jwtresource.entity.Role;
import sb.resource.jwtresource.entity.User;

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

	@Override
	public Object delete(String userName) {
		entityManager.createQuery("Update User set status='D' where userName=:userName");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUser(Map<String, String> searchCriteria) {
		StringBuilder whereClause = new StringBuilder("");
		Iterator<Map.Entry<String, String>> itr = searchCriteria.entrySet().iterator(); 
        
        while(itr.hasNext()) 
        { 
             Map.Entry<String, String> entry = itr.next(); 
             whereClause.append(" "+entry.getKey()+" = '"+entry.getValue()+"' "+(itr.hasNext()?" and ":" ")); 
        }
		return entityManager.createQuery("Select user From User user where "+whereClause).getResultList();
	}
}
