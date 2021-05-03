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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sb.resource.jwtresource.dao.UserInfoDao;
import sb.resource.jwtresource.entity.Role;
import sb.resource.jwtresource.entity.User;
import sb.resource.jwtresource.exception.UserNotFoundException;
import sb.resource.jwtresource.model.UserSearchCriteria;
import sb.resource.jwtresource.security.UserPrincipal;
import sb.resource.jwtresource.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("InsideXXXXXXXXXXXXX " + userName);
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        List<Role> roles = userRepository.findRoleByUserName(userName);
        roles.forEach((role) -> {
            System.out.println("Role is " + role.getRoles());
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoles());
            authorities.add(authority);
        });
		/*for (Role role : roles) {
			System.out.println("Role is "+role.getRoles());
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.getRoles());
			authorities.add(authority);
		}*/
        return new UserPrincipal(user, authorities);
    }

}
