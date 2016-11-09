package br.com.school.alert;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import br.com.school.alert.controller.dto.UserDTO;
import br.com.school.alert.security.DefaultFailureHandler;
import br.com.school.alert.security.DefaultSuccessHandler;
import br.com.school.alert.security.DefaultUser;
import br.com.school.alert.security.UserLoginService;

@Configuration
public class SecurityConfig {

  @Bean
  public ApplicationSecurity applicationSecurity() {
    return new ApplicationSecurity();
  }

  @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
  protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userLoginService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().antMatchers("/public").permitAll().anyRequest().fullyAuthenticated();
      this.configureCsrf(http);
      this.configureSession(http);
      this.configureEntryPoint(http);
      this.configureAuthentication(http);
    }

    private void configureAuthentication(HttpSecurity http) throws Exception {
      DefaultSuccessHandler successHandler = new DefaultSuccessHandler(p -> new UserDTO((DefaultUser) p));

      http.formLogin().loginProcessingUrl("/login").successHandler(successHandler)
          .failureHandler(new DefaultFailureHandler());

      http.logout().logoutUrl("/logout").logoutSuccessHandler(successHandler);
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
      http.csrf().disable();
    }

    private void configureSession(HttpSecurity http) throws Exception {
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    private void configureEntryPoint(HttpSecurity http) throws Exception {
      http.exceptionHandling().authenticationEntryPoint(
          (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized"));
    }
  }
}