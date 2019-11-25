package sb.resource.jwtresource.config;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;

import sb.resource.jwtresource.common.JwtProperties;
import sb.resource.jwtresource.security.UserPrincipal;
import sb.resource.jwtresource.service.UserService;

public class JWTTokenAuthorizationFilter extends BasicAuthenticationFilter {

	AuthenticationManager authenticationManager;
	
	UserService userService;

	public JWTTokenAuthorizationFilter(AuthenticationManager authenticationManager,UserService userService) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Read the Authorization header, where the JWT token should be
		String header = request.getHeader(JwtProperties.HEADER_STRING);

		// If header does not contain BEARER or is null delegate to Spring impl and exit
		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		// If header is present, try grab user principal from database and perform
		// authorization
		Authentication authentication = authenticateToken(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Continue filter execution
		chain.doFilter(request, response);
	}

	private Authentication authenticateToken(HttpServletRequest request) {
		String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

		if (token != null) {
			// parse the token and validate it
			String userName="";
			try {
				userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token).getSubject();
			} catch (JWTVerificationException e) {
				request.setAttribute("expired",e.getMessage());
				throw e;
			}

			// Search in the DB
			if (userName != null) {
				UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(userName) ;
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
				return auth;
			}
		}
		return null;
	}
}
