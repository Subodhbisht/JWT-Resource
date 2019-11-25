package sb.resource.jwtresource.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		final String expired = (String) request.getAttribute("expired");
		String json="";
		
		System.out.println(expired+"-------->");
		
//        PrintWriter writer = response.getWriter();
//        writer.println("HTTP Status 401 - " + authException.getMessage());
		
		if(expired!=null) {
			response.addHeader("WWW-Authenticate", expired);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
			json = String.format("{\"message\": \"%s\"}", expired);
		}
		else {
			response.addHeader("WWW-Authenticate", "Basic realm=" 
					+ "getRealmName() + ");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        
        // How to customize the retured message?
        // Link: https://stackoverflow.com/a/40791087
        json = String.format("{\"message\": \"%s\"}", authException.getMessage());
		}
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);   
	}

}
