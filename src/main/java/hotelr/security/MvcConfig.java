package hotelr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {}

  @Bean
  public ApplicationSecurity applicationSecurity() {
    return new ApplicationSecurity();
  }

  @Configuration
  protected static class AuthenticationSecurity extends
      GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private Users users;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(users);
    }
  }

  protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
          .antMatchers("/").permitAll()
          .antMatchers("/login").permitAll()
          .antMatchers("/hotels/**").hasRole("GUEST")
          .antMatchers("/dashboards/guest/**").hasRole("GUEST")
          .antMatchers("/dashboards/admin/**").hasRole("ADMIN")
          .antMatchers("/dashboards/manager/**").hasRole("MANAGER")
          //.antMatchers("/dashboards/moderator/**").hasRole("MODERATOR")
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
}