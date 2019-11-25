package sb.resource.jwtresource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import sb.resource.jwtresource.service.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//
		.httpBasic().authenticationEntryPoint(customAuthenticationEntryPoint)
		.and()
		.addFilter(new JWTTokenAuthorizationFilter(authenticationManager(),userService))
		.authorizeRequests()
		.antMatchers(
                HttpMethod.GET,
                "/",
                "/v2/api-docs",           // swagger
                "/webjars/**",            // swagger-ui webjars
                "/swagger-resources/**",  // swagger-ui resources
                "/configuration/**",      // swagger configuration
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        ).permitAll()
		.antMatchers(HttpMethod.GET).hasRole("ADMIN")
		;
	}
	 

		
//	
}
