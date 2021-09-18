package praxsoft.SrvHTTP02.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.SupService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

@RestController
public class ArtigoResources {

    @Autowired
    private SupService supService;
    private static final String dirSite = "site/";

    @GetMapping(value = "/")
    public ResponseEntity<?> inicio() {
        String nomeArquivo = "index.html";
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

    @GetMapping(value = "/{nomeArq}.html")
    public ResponseEntity<?> EnviaArtigo(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".html";
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

    @GetMapping(value = "/{nomeArq}.css")
    public ResponseEntity<?> EnviaCSSArquivo(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".css";
        String arquivo = supService.LeArquivoTxt(dirSite, nomeArquivo);
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

    @GetMapping(value = "/{nomeArq}.js")
    public ResponseEntity<?> EnviaJS(@PathVariable String nomeArq) {

        String nomeArquivo = nomeArq + ".js";
        String arquivo = supService.LeArquivoTxt(dirSite, nomeArquivo);
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

    @GetMapping(value = "/{nomeArq}.jpg")
    public ResponseEntity<?> EnviaImagem(@PathVariable String nomeArq) {
        String nomeArquivo = nomeArq + ".jpg";
        byte[] arquivo = supService.LeArquivoByte(dirSite, nomeArquivo);
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
