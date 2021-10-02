package praxsoft.SrvHTTP02.domain;

public class DadosMesa {

    private int numMesas = 17;
    private String[] nomeUsuario = new String[numMesas];
    private String[] nomeCompleto = new String[numMesas];
    private String[] numeroPessoas = new String[numMesas];
    private String[] horaChegada = new String[numMesas];
    private String[] adminResponsavel = new String[numMesas];
    private String[] horaRegistro = new String[numMesas];
    private String[] dataRegistro = new String[numMesas];

    public String[] getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String[] nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String[] getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String[] nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String[] getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(String[] numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }

    public String[] getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(String[] horaChegada) {
        this.horaChegada = horaChegada;
    }

    public String[] getAdminResponsavel() {
        return adminResponsavel;
    }

    public void setAdminResponsavel(String[] adminResponsavel) {
        this.adminResponsavel = adminResponsavel;
    }

    public String[] getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String[] horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public String[] getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String[] dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
