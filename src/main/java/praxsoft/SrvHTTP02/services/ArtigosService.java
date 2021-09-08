package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;

@Service
public class ArtigosService {

    public String buscaIndex() {
        String arquivoIndex = "";
        String Caminho = "/home/antonio/Recursos/";
        String NomeArquivo = "index.html";

        Arquivo arquivo = new Arquivo();

        if (arquivo.Existe(Caminho, NomeArquivo)) {
            arquivoIndex = arquivo.LeTexto(Caminho, NomeArquivo);
        }
        else {
            arquivoIndex = "arquivo index.html não encontrado";
        }

        return arquivoIndex;
    }



}
