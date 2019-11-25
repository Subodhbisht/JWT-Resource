package sb.resource.jwtresource.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sb.resource.jwtresource.dao.UserInfoDao;
import sb.resource.jwtresource.entity.Role;
import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.model.UserSearchCriteria;
import sb.resource.jwtresource.security.UserPrincipal;
import sb.resource.jwtresource.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserInfoDao userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private UserPrincipal principal;
	
	@Override
	@Transactional
	public User save(User user) {
		principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("Created By :"+principal.getUser().getUserName());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRecordCount(0L);
		user.setCreatedBy(principal.getUser().getUserId());
		user.setCreatedOn(new Date());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User update(User user) {
		principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRecordCount(user.getRecordCount()+1);
		user.setModifiedBy(principal.getUser().getUserId());
		user.setModifiedOn(new Date());
		return userRepository.save(user);
	}

	@Override
	public void delete(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> findUsers(UserSearchCriteria searchCriteria) {
		
		//userName, fullName, country
		Map<String, String> mapCriteria = new HashMap<String, String>();
		if(searchCriteria.getUserName()!=null) {
			mapCriteria.put("user.userName", searchCriteria.getUserName());
		}
		if(searchCriteria.getFullName()!=null) {
			mapCriteria.put("user.fullName", searchCriteria.getFullName());
		}
		if(searchCriteria.getCountry()!=null) {
			mapCriteria.put("user.country", searchCriteria.getCountry());
		}
		return userRepository.findUser(mapCriteria);
		
	}

	@Override
	public User getUser(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}

	@Override
	public UserPrincipal loadUserByUsername(String userName) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("InsideXXXXXXXXXXXXX "+userName);
		User user = userRepository.findByUserName(userName) ;
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		List<Role> roles = userRepository.findRoleByUserName(userName);
		for (Role role : roles) {
			System.out.println("Role is "+role.getRoles());
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.getRoles());
			authorities.add(authority);
		}
		return new UserPrincipal(user, authorities);
//		return (UserDetails)new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
	}

}
