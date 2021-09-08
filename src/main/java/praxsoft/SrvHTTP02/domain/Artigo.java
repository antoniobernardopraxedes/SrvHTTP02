package praxsoft.SrvHTTP02.domain;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Artigo {

    private int Id;

    private String nome;

    private String autor;

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String publicacao;

    private String Conteudo;

    public Artigo() {}
    public Artigo(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(String publicacao) {
        this.publicacao = publicacao;
    }

    public String getConteudo() {
        return Conteudo;
    }

    public void setConteudo(String conteudo) {
        Conteudo = conteudo;
    }
}
