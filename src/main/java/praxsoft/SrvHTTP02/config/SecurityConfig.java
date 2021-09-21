package praxsoft.SrvHTTP02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public static void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("bernardo").password("{noop}senha").roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/reservas").permitAll()
                .antMatchers("/vlgl.*").permitAll()
                .antMatchers("/reserva").permitAll()
                .anyRequest().authenticated()

                //.formLogin()
                //.loginPage("/reservas")
                //.permitAll();

                //.antMatchers("/reservas").permitAll()

                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
}
