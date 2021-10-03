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
                .antMatchers("/vlgl/cadastra").permitAll()
                .antMatchers("/vlgl/reserva/*").permitAll()
                .antMatchers("/atualiza").permitAll()
                .antMatchers("/local001.xml").permitAll()
                .anyRequest().authenticated()

                .and()
                .httpBasic()
                .and()
                .csrf().disable();

    }
/*
    private void LeUsuariosArquivoConf() {
        String arquivoConf;
        String caminho = "recursos/";
        String nomeArquivo = "srvhttp02.cnf";

        arquivoConf = Arquivo.LeTexto(caminho, nomeArquivo);

        if (arquivoConf != null) {
            nomeUsuario[0] = Arquivo.LeParametro(arquivoConf, "NomeUsuarioAdmin1:");
            senha[0] = Arquivo.LeParametro(arquivoConf, "SenhaAdmin1:");
            nome[0] = Arquivo.LeCampo(arquivoConf, "NomeAdmin1:");

            nomeUsuario[1] = Arquivo.LeParametro(arquivoConf, "NomeUsuarioAdmin2:");
            senha[1] = Arquivo.LeParametro(arquivoConf, "SenhaAdmin2:");
            nome[1] = Arquivo.LeCampo(arquivoConf, "NomeAdmin2:");

            nomeUsuario[2] = Arquivo.LeParametro(arquivoConf, "NomeUsuarioAdmin3:");
            senha[2] = Arquivo.LeParametro(arquivoConf, "SenhaAdmin3:");
            nome[2] = Arquivo.LeCampo(arquivoConf, "NomeAdmin3:");
        }

        System.out.println("");
        int numUsuarios = 3;
        for (int i = 0; i < numUsuarios; i++) {
            System.out.print("Administrador " + (i + 1) + " - Nome de usuário: " + getNomeUsuario(i));
            System.out.print(" - Senha: " + getSenha(i));
            System.out.println(" - Nome: " + getNome(i));
        }
    }
    */

}
