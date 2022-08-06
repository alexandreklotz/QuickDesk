package fr.alexandreklotz.quickdesk.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    final SecurityUserDetailsService securityUserDetailsService;

    public ApplicationSecurityConfig(SecurityUserDetailsService securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().configurationSource(httpServletRequest -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.applyPermitDefaultValues();
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                    corsConfiguration.setAllowedHeaders(
                            Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                                    "Accept", "Authorization","Access-Control-Allow-Origin"));
                    return corsConfiguration;
                })

                .and()
                .csrf().disable().httpBasic().and()
                .authorizeRequests()
                .antMatchers("/mypanel/").hasAnyRole("USER", "VIP", "ADMIN")
                .antMatchers("/admin/").hasRole("ADMIN")
                .antMatchers("/admin**").hasRole("ADMIN")
                .antMatchers("/user**").hasAnyRole("USER", "VIP", "ADMIN")
                .antMatchers("/ticket**").hasAnyRole("USER", "VIP", "ADMIN")
                .antMatchers("**/delete/").hasRole("ADMIN")
                .antMatchers("/queue**").hasRole("ADMIN")
                .antMatchers("/login").hasAnyRole()
                .antMatchers("/test/").hasRole("ADMIN")
                .antMatchers("/test**").hasRole("ADMIN")
                .antMatchers("/setup/").hasRole("SETUP")
                .antMatchers("/setup**").hasRole("SETUP")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .defaultSuccessUrl("/mypanel", true) //TODO : Check if i should remove /mypanel ?
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(false).permitAll();
    }
}
