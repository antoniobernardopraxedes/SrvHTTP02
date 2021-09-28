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

        int numUsuarios = 5;
        String[][] usuarioSenha = new String[numUsuarios][2];
        usuarioSenha[0][0] = "Ingrid";       usuarioSenha[0][1] = "moqueca";
        usuarioSenha[1][0] = "Bernardo";       usuarioSenha[1][1] = "cl030379";
        usuarioSenha[2][0] = "IsisDias";    usuarioSenha[2][1] = "25052009";
        usuarioSenha[3][0] = "LuccaBorges";    usuarioSenha[3][1] = "25052009";
        usuarioSenha[4][0] = "SofiaDias";    usuarioSenha[4][1] = "15122007";

        for (int i = 0; i < numUsuarios; i++) {
            auth.inMemoryAuthentication()
                    .withUser(usuarioSenha[i][0]).password("{noop}" + usuarioSenha[i][1]).roles("USER");
        }

    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/reservas").permitAll()
                .antMatchers("/vlgl.*").permitAll()
                .antMatchers("/vlgl/confirma").permitAll()
                .antMatchers("/atualiza").permitAll()
                .antMatchers("/local001.xml").permitAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
}
