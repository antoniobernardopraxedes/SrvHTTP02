package praxsoft.SrvHTTP02.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.Principal;
import java.util.*;

import org.apache.tomcat.util.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import praxsoft.SrvHTTP02.domain.Artigo;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.SupService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

import javax.servlet.*;
import javax.servlet.http.*;

@RestController
public class SupResources {

    @Autowired
    private SupService supService;

    @RequestMapping(value = "/supervisao", method = RequestMethod.GET)
    public String supHtml() {
        return supService.buscaHtmlSup();
    }

    @RequestMapping(value = "/sup/supcloud.css", method = RequestMethod.GET)
    public ResponseEntity<?> supCss() {
        String arquivoCss = null;
        try {
            arquivoCss = supService.buscaCssSup();
        } catch (ArquivoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/css"))
                .body(arquivoCss);
    }

    @RequestMapping(value = "/sup/supcloud.js", method = RequestMethod.GET)
    public ResponseEntity<?> supJs() {
        String arquivoJs = null;
        try {
            arquivoJs = supService.buscaJsSup();
        } catch (ArquivoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/javascript"))
                .body(arquivoJs);
    }

    @RequestMapping(value = "/local001.xml", method = RequestMethod.GET)
    public ResponseEntity<?> atualizaVariaveis() {
        String msgXML = null;

        try {
            msgXML = supService.MontaMsg();
        } catch (ArquivoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(msgXML);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/xml"))
                .body(msgXML);
    }

    //@RequestMapping(value = "/atualiza", method = RequestMethod.POST)
    //{"application/octet-stream"}

    @PostMapping(value = "/atualiza")
    public ResponseEntity<?> Atualiza(@RequestBody Dados001 MsgJson) { // @RequestBody String MsgJson

        System.out.println("A comunicação com o Atualizador está " + MsgJson.getComcnv() );
        System.out.println("Tensão das Baterias: " + MsgJson.getVbat() );
        System.out.println("Saúde das Baterias: " + MsgJson.getSdbat() );

        //System.out.println(MsgJson);

        String RspSrv = " { \"cmd\" : \"ack\" }";
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(RspSrv);
    }
}
