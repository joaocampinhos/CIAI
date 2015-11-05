package hotelr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
              .dataSource(dataSource)
              .withDefaultSchema();
  }

  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
          .antMatchers("/").permitAll()
          .antMatchers("/hotels").hasRole("GUEST")
          .anyRequest().authenticated()
          .and()
        .formLogin()
            .loginPage("/login")
            //.defaultSuccessUrl("/dashboard")
            .permitAll()
            .and()
        .logout()
          .logoutSuccessUrl("/")
          .permitAll();
  }

}