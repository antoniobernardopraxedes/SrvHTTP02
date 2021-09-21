package praxsoft.SrvHTTP02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        String[][] usuarioSenha = new String[3][2];
        usuarioSenha[0][0] = "bernardo";       usuarioSenha[0][1] = "cl030379";
        usuarioSenha[1][0] = "IsisDias";       usuarioSenha[1][1] = "260998";
        usuarioSenha[2][0] = "LuccaBorges";    usuarioSenha[2][1] = "25052009";

        auth
            .inMemoryAuthentication()
            .withUser(usuarioSenha[0][0]).password("{noop}" + usuarioSenha[0][1]).roles("USER").and()
            .withUser(usuarioSenha[1][0]).password("{noop}" + usuarioSenha[1][1]).roles("USER").and()
            .withUser(usuarioSenha[2][0]).password("{noop}" + usuarioSenha[2][1]).roles("USER");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/reservas").permitAll()
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
