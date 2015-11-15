package hotelr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MvcConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private Users users;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(users);
  }

  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/js/**", "/css/**", "/images/**", "/", "/login/**", "/register/**").permitAll()
        .antMatchers("/hotels/**").hasRole("GUEST")
        .antMatchers("/dashboards/guest/**").hasRole("GUEST")
        .antMatchers("/dashboards/admin/**").hasRole("ADMIN")
        .antMatchers("/dashboards/manager/**").hasRole("MANAGER")
        .antMatchers("/dashboards/moderator/**").hasRole("MODERATOR")
        .anyRequest().authenticated()
        .and()
      .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/dashboards")
          .permitAll()
          .and()
      .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .permitAll();
  }
}