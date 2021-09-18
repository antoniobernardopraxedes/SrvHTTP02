package praxsoft.SrvHTTP02.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import praxsoft.SrvHTTP02.services.SupService;

@RestController
public class GeralResources {

    @Autowired
    private SupService supService;

    @GetMapping(value = "/")
    public ResponseEntity<?> inicio() {
        String nomeArquivo = "index.html";
        String arquivo = supService.LeArquivoTxt("", nomeArquivo);

        if (arquivo != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("text/html"))
                    .body(arquivo);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND )
                    .contentType(MediaType.valueOf("text/html"))
                    .body(msgArqNaoEncontrado(nomeArquivo));
        }
    }

    private String msgArqNaoEncontrado(String nomeArquivo) {
        return ("<h3>File not found: " + nomeArquivo + "</h3>");
    }

}
