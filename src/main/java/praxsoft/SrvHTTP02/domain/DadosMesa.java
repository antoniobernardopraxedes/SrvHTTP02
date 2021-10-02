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
    public String getNomeUsuario(int i) {
        return nomeUsuario[i];
    }

    public void setNomeUsuario(String[] nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario, int i) {
        this.nomeUsuario[i] = nomeUsuario;
    }

    public String[] getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String[] nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto, int i) {
        this.nomeCompleto[i] = nomeCompleto;
    }

    public String[] getNumeroPessoas() {
        return numeroPessoas;
    }
    public String getNumeroPessoas(int i) {
        return numeroPessoas[i];
    }

    public void setNumeroPessoas(String[] numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }
    public void setNumeroPessoas(String numeroPessoas, int i) {
        this.numeroPessoas[i] = numeroPessoas;
    }

    public String[] getHoraChegada() {
        return horaChegada;
    }
    public String getHoraChegada(int i) {
        return horaChegada[i];
    }

    public void setHoraChegada(String[] horaChegada) {
        this.horaChegada = horaChegada;
    }
    public void setHoraChegada(String horaChegada, int i) {
        this.horaChegada[i] = horaChegada;
    }

    public String[] getAdminResponsavel() {
        return adminResponsavel;
    }
    public String getAdminResponsavel(int i) {
        return adminResponsavel[i];
    }
    public void setAdminResponsavel(String[] adminResponsavel) {
        this.adminResponsavel = adminResponsavel;
    }
    public void setAdminResponsavel(String adminResponsavel, int i) {
        this.adminResponsavel[i] = adminResponsavel;
    }

    public String[] getHoraRegistro() {
        return horaRegistro;
    }
    public String getHoraRegistro(int i) {
        return horaRegistro[i];
    }
    public void setHoraRegistro(String[] horaRegistro) {
        this.horaRegistro = horaRegistro;
    }
    public void setHoraRegistro(String horaRegistro, int i) {
        this.horaRegistro[i] = horaRegistro;
    }

    public String[] getDataRegistro() {
        return dataRegistro;
    }
    public String getDataRegistro(int i) {
        return dataRegistro[i];
    }
    public void setDataRegistro(String[] dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    public void setDataRegistro(String dataRegistro, int i) {
        this.dataRegistro[i] = dataRegistro;
    }
}
