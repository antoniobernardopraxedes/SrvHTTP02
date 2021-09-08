package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileOutputStream;

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

    public String MontaMsg() throws JAXBException {
        local001 dvd = new local001(101, "Lord of the Rings", 10);
        JAXBContext context = JAXBContext.newInstance(local001.class);
        Marshaller marshaller = context.createMarshaller();
        String msgXML = "";
        marshaller.marshal(dvd, System.out);
        return msgXML;
    }

}
