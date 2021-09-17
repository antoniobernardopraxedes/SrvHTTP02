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

    @GetMapping(value = "/")
    public ResponseEntity<?> inicio() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/html"))
                .body(supService.LeArquivoTxt("index.html"));
    }

    @GetMapping(value = "/artigo000")
    public ResponseEntity<?> EnviaArtigo() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/html"))
                .body(supService.LeArquivoTxt("site/artigo000.html"));
    }

    @GetMapping(value = "/artigo000.css")
    public ResponseEntity<?> EnviaCSS() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/css"))
                .body(supService.LeArquivoTxt("site/artigo000.css"));
    }

    @GetMapping(value = "/artigo000.js")
    public ResponseEntity<?> EnviaJS() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/javascript"))
                .body(supService.LeArquivoTxt("site/artigo000.js"));
    }


}
