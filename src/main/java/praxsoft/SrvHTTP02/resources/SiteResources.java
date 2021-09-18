package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.boot.autoconfigure.webservices.WebServicesProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import praxsoft.SrvHTTP02.services.SupService;

@RestController
public class SiteResources {

    @Autowired
    private SupService supService;
    private static final String dirSite = "site/";

    @GetMapping(value = "/site")
    public ResponseEntity<?> InicioSite() {

        String nomeArquivo = "siteindex.html";
        String arquivo = supService.LeArquivoTxt(dirSite, nomeArquivo);
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

    @GetMapping(value = "/site{nomeArq}.html")
    public ResponseEntity<?> EnviaHtmlSite(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".html";
        String arquivo = supService.LeArquivoTxt("site/", nomeArquivo);
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

    @GetMapping(value = "/site{nomeArq}.css")
    public ResponseEntity<?> EnviaCSSSite(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".css";
        String arquivo = supService.LeArquivoTxt("site/css/", nomeArquivo);
        if (arquivo != null ) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("text/css"))
                    .body(arquivo);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND )
                    .contentType(MediaType.valueOf("text/html"))
                    .body(msgArqNaoEncontrado(nomeArquivo));
        }
    }

    @GetMapping(value = "/site{nomeArq}.js")
    public ResponseEntity<?> EnviaJSSite(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".js";
        String arquivo = supService.LeArquivoTxt("site/js/", nomeArquivo);
        if (arquivo != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("text/javascript"))
                    .body(arquivo);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND )
                    .contentType(MediaType.valueOf("text/html"))
                    .body(msgArqNaoEncontrado(nomeArquivo));
        }
    }

    @GetMapping(value = "/site{nomeArq}.jpg")
    public ResponseEntity<?> EnviaImagemSite(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".jpg";
        byte[] arquivo = supService.LeArquivoByte("site/img/", nomeArquivo);
        if (arquivo.length > 0) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/jpeg"))
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
