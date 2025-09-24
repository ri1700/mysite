package com.bit2025.mysite.config;

import java.io.IOException;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.bit2025.mysite.repository.UserRepository;
import com.bit2025.mysite.security.UserDetailsServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> {
        	formLogin
        		.loginPage("/user/login")
        		.loginProcessingUrl("/user/auth")
        		.usernameParameter("email")
        		.passwordParameter("password")
        		.defaultSuccessUrl("/")
        		// 파라미터와 함께 리다이렉트 응답
        		// .failureUrl("/user/login?result=fail")
        		.failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
						request.setAttribute("email", request.getParameter("email"));
						request
							.getRequestDispatcher("/user/login")
							.forward(request, response);
					}
        		});
        		
        })
        .logout(logout -> {
        	logout
        		.logoutUrl("/user/logout")
        		.logoutSuccessUrl("/");
        })
    	.authorizeHttpRequests(authorizeRequest -> {
    		/* ACL */
    		authorizeRequest
				.requestMatchers(new RegexRequestMatcher("^/admin/?.*$", null))
				.hasRole("ADMIN")
    			
				.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
				.hasAnyRole("USER", "ADMIN")
    			
				.requestMatchers(new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$", null))
				.hasAnyRole("USER", "ADMIN")
				
				.anyRequest().permitAll();

    	})
    	.exceptionHandling(exceptionHandling -> {
    		exceptionHandling.accessDeniedHandler(new AccessDeniedHandler() {
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
					response.sendRedirect(request.getContextPath());
				}
    		});
    	});
        
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncoder);
    	
    	return new ProviderManager(authenticationProvider);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4); // 4 ~ 31
    }
    
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
    	return new UserDetailsServiceImpl(userRepository);
    }
}