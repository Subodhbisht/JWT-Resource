package sb.resource.jwtresource.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sb.resource.jwtresource.Bean.UserResourceBean;
import sb.resource.jwtresource.dao.CustomUserRoleDao;

public class CustomUserRoleDaoImpl implements CustomUserRoleDao {

	@PersistenceContext
	EntityManager entityManager;

	public List<UserResourceBean> findByUser(Map<String, String> searchCriteria) {
//		Session session = (Session) entityManager.getDelegate();
//		 session.createQuery(
//				"Select users.userName as userName,users.fullName as fullName,users.country as country,role.roles as roles From UserRole ur inner join ur.user users inner join ur.role role where users.userName=:username")
//				.setResultTransformer(Transformers.aliasToBean(UserResourceBean.class))
//				.setParameter("username", userName).getResultList();
		
		StringBuilder whereClause = new StringBuilder("");
		Iterator<Map.Entry<String, String>> itr = searchCriteria.entrySet().iterator(); 
        
        while(itr.hasNext()) 
        { 
             Map.Entry<String, String> entry = itr.next(); 
             whereClause.append(" "+entry.getKey()+" = '"+entry.getValue()+"' "+(itr.hasNext()?" and ":" ")); 
        }
		return entityManager.createQuery(
				"Select new sb.resource.jwtresource.Bean.UserResourceBean(users.userName,users.fullName,users.country,role.roles) From UserRole ur inner join ur.user users inner join ur.role role where "+whereClause,UserResourceBean.class)
				.getResultList();
	}
}
