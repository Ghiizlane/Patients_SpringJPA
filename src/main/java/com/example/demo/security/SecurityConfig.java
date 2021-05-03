package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

@Configuration //annotation dire que cette classe qui va être traité qu démarrage de l'application
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	 @Override 
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 PasswordEncoder passwordEncoder=passwordEncoder();
			/*
			 * auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.
			 * encode("12345")).roles("USER");
			 * auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.
			 * encode("12345")).roles("USER");
			 * auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.
			 * encode("12345")).roles("USER", "ADMIN");
			 */
		 System.out.println("***************************************************");
		 System.out.println(passwordEncoder.encode("12345"));
		 System.out.println("**************************************************");

		 auth.jdbcAuthentication().dataSource(dataSource)
		 .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
		 .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?").passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
		 }
	 
 @Override
 protected void configure(HttpSecurity http) throws Exception{
	 //http.formLogin();//utiliser le formulaire d'authentification par défaut
	 http.formLogin().loginPage("/login");
	 http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/form**/**").hasRole("ADMIN");//spécifier les règles de gestion 
	 http.authorizeRequests().antMatchers("/patient**/**").hasRole("USER");
	 http.authorizeRequests().antMatchers("/user/**", "/login", "/webjars/**").permitAll(); 
	 http.authorizeRequests().anyRequest().authenticated();//toutes les requetes http n'écessite une authentification
	 http.exceptionHandling().accessDeniedPage("/notAuthorized");//personnaliser les pages d'erreur 403
 }
 @Bean //pour pouvoir l'inecter dans les autres parties de l'application 
 public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder();
 }

}