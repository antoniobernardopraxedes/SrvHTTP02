package praxsoft.SrvHTTP02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import praxsoft.SrvHTTP02.services.Arquivo;
//import praxsoft.SrvHTTP02.services.Inicia;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private static String[] nomeUsuario = new String[3];
    //private static String[] senha = new String[3];
    //private static String[] nome = new String[3];

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        Arquivo.LeConfiguracao();
        Arquivo.MostraDadosConfiguracao();
        Arquivo.LeUsuarios();
        Arquivo.MostraUsuarios();

        for (int i = 0; i < Arquivo.getNumUsuarios(); i++) {
            auth.inMemoryAuthentication()
                .withUser(Arquivo.getNomeUsuario(i)).password("{noop}" + Arquivo.getSenhaUsuario(i)).roles("USER");
        }
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/vlgl/cadastro/*").permitAll()
                .antMatchers("/vlgl/reserva/*").permitAll()
                .antMatchers("/atualiza").permitAll()
                .antMatchers("/local001.xml").permitAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }

}
