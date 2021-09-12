package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SupService {

    public String buscaHtmlSup() {
        String arquivoTxt = "";
        String Caminho = "/home/antonio/Recursos/sup/";
        String NomeArquivo = "supcloud.html";

        Arquivo arquivo = new Arquivo();

        if (arquivo.Existe(Caminho, NomeArquivo)) {
            arquivoTxt = arquivo.LeTexto(Caminho, NomeArquivo);
        }
        else {
            throw new ArquivoNaoEncontradoException("");
        }
        return arquivoTxt;
    }


    public String buscaCssSup() {
        String arquivoTxt = "";
        String Caminho = "/home/antonio/Recursos/sup/";
        String NomeArquivo = "supcloud.css";

        Arquivo arquivo = new Arquivo();

        if (arquivo.Existe(Caminho, NomeArquivo)) {
            arquivoTxt = arquivo.LeTexto(Caminho, NomeArquivo);
        }
        else {
            throw new ArquivoNaoEncontradoException("");
        }
        return arquivoTxt;
    }

    public String buscaJsSup() {
        String arquivoTxt = "";
        String Caminho = "/home/antonio/Recursos/sup/";
        String NomeArquivo = "supcloud.js";

        Arquivo arquivo = new Arquivo();

        if (arquivo.Existe(Caminho, NomeArquivo)) {
            arquivoTxt = arquivo.LeTexto(Caminho, NomeArquivo);
        }
        else {
            throw new ArquivoNaoEncontradoException("");
        }
        return arquivoTxt;
    }

    public static String MontaMsg(Dados001 MsgJson) {
        String arquivoTxt = "";
        String Caminho = "/home/antonio/Recursos/";
        String NomeArquivo = "local001.xml";

        Arquivo arquivo = new Arquivo();
        if (arquivo.Existe(Caminho, NomeArquivo)) {
            arquivoTxt = arquivo.LeTexto(Caminho, NomeArquivo);
        }
        else {
            throw new ArquivoNaoEncontradoException("");
        }

        return arquivoTxt;
    }



}
