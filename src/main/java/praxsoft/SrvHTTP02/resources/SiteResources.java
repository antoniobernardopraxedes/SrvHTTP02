package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SupService;

@RestController
public class SiteResources {

    @Autowired
    private SupService supService;
    private boolean mobile;

    @GetMapping(value = "/site")
    public ResponseEntity<?> InicioSite(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "indice";

        nomeArquivo = VerificaMobile(userAgent, nomeArquivo, "html");

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

    @GetMapping(value = "/site.{nomeArquivo}.html")
    public ResponseEntity<?> EnviaHtmlSite(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        nomeArquivo = VerificaMobile(userAgent, nomeArquivo, "html");

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

    @GetMapping(value = "/site.{nomeArq}.css")
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

    @GetMapping(value = "/site.{nomeArq}.js")
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

    @GetMapping(value = "/site.{nomeArq}.jpg")
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

    private String VerificaMobile(String userAgent, String nomeArquivo, String extensao) {
        if (userAgent.toLowerCase().contains("mobile")) {
            nomeArquivo = nomeArquivo + ".m." + extensao;
            supService.Terminal("Acesso por Dispositivo Móvel", false);
        }
        else {
            nomeArquivo = nomeArquivo + "." + extensao;
        }
        return nomeArquivo;
    }

    private String msgArqNaoEncontrado(String nomeArquivo) {

        return ("<h3>File not found: " + nomeArquivo + "</h3>");
    }

}
