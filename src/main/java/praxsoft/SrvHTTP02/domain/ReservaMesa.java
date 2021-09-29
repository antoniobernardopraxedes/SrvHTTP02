package praxsoft.SrvHTTP02.domain;

import praxsoft.SrvHTTP02.services.Auxiliar;

public class ReservaMesa {

    private String nomeUsuario;
    private String nomeCliente;
    private String dataReserva;
    private String numPessoas;
    private String horaChegada;
    private String mesaSelecionada;
    private String adminResp;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getNumPessoas() {
        return numPessoas;
    }

    public String getHoraChegada() {
        return horaChegada;
    }

    public String getMesaSelecionada() {
        return mesaSelecionada;
    }

    public String getAdminResp() {
        return adminResp;
    }

    public void MostraCamposTerminal() {
        Auxiliar.Terminal("Nome de usuário: " + nomeUsuario, false);
        Auxiliar.Terminal("Nome do cliente: " + nomeCliente, false);
        Auxiliar.Terminal("Data da reserva: " + dataReserva, false);
        Auxiliar.Terminal("Número de Pessoas: " + numPessoas, false);
        Auxiliar.Terminal("Hora de chegada: " + horaChegada, false);
        Auxiliar.Terminal("Mesa selecionada: " + mesaSelecionada, false);
    }

}
