package praxsoft.SrvHTTP02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SiteService {

    @Autowired
    private SupService supService;

    public String VerificaMobile(String userAgent, String nomeArq) {
        if (userAgent.toLowerCase().contains("mobile")) {

            if (nomeArq.endsWith(".css")) {
                nomeArq = nomeArq.substring(0, nomeArq.length() - 4) + ".m.css";
            }
        }
        return nomeArq;
    }

    public ResponseEntity<?> LeArquivoMontaResposta(String caminho, String nomeArquivo) {

        String tipo = "text/plain";
        if (nomeArquivo.endsWith(".html")) {
            tipo = "text/html";
        }
        if (nomeArquivo.endsWith(".css")) {
            tipo = "text/css";
            caminho = caminho + "css/";
        }
        if (nomeArquivo.endsWith(".js")) {
            tipo = "text/javascript";
            caminho = caminho + "js/";
        }
        if (nomeArquivo.endsWith(".jpg")) {
            tipo = "image/jpeg";
            caminho = caminho + "img/";
        }

        if (tipo.equals("image/jpeg")) {
            byte[] arquivoByte = supService.LeArquivoByte(caminho, nomeArquivo);
            if (arquivoByte.length == 0) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND )
                        .contentType(MediaType.valueOf("text/html"))
                        .body(msgArqNaoEncontrado(nomeArquivo));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(tipo))
                        .body(arquivoByte);
            }
        }
        else {
            String arquivoTxt = supService.LeArquivoTxt(caminho, nomeArquivo);
            if (arquivoTxt == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND )
                        .contentType(MediaType.valueOf("text/html"))
                        .body(msgArqNaoEncontrado(nomeArquivo));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(tipo))
                        .body(arquivoTxt);
            }
        }
    }

    private String msgArqNaoEncontrado(String nomeArquivo) {

        return ("<h3>File not found: " + nomeArquivo + "</h3>");
    }

}
