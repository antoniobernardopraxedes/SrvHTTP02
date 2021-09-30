package praxsoft.SrvHTTP02.domain;

import praxsoft.SrvHTTP02.services.Auxiliar;

public class Cliente {

    private String nomeUsuario;
    private String nome;
    private String celular;
    private String obs1;
    private String obs2;
    private String idoso;
    private String locomocao;
    private String exigente;
    private String genero;
    private String adminResp;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public String getObs1() {
        if (obs1.equals("")) { obs1 = "não informada"; }
        return obs1;
    }

    public String getObs2() {
        if (obs2.equals("")) { obs2 = "não informada"; }
        return obs2;
    }

    public String getIdoso() {
        return idoso;
    }

    public String getLocomocao() {
        return locomocao;
    }

    public String getExigente() {
        return exigente;
    }

    public String getGenero() {
        return genero;
    }

    public String getAdminResp() {
        return adminResp;
    }

    public void MostraCamposTerminal() {
        Auxiliar.Terminal("Nome de usuário do cliente: " + nomeUsuario, false);
        Auxiliar.Terminal("Nome do cliente: " + nome, false);
        Auxiliar.Terminal("Celular do cliente: " + celular, false);
        Auxiliar.Terminal("Observação 1: " + obs1, false);
        Auxiliar.Terminal("Observação 2: " + obs2, false);
        Auxiliar.Terminal("O cliente é idoso? " + idoso, false);
        Auxiliar.Terminal("O cliente tem problema de locomoção? " + locomocao, false);
        Auxiliar.Terminal("O cliente é exigente? " + exigente, false);
        Auxiliar.Terminal("Gênero: " + genero, false);
        Auxiliar.Terminal("Admin responsável pelo cadastro: " + adminResp, false);
    }
}
