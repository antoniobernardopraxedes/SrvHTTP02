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

    @PostMapping(value = "/atualiza")
    public ResponseEntity<?> Atualiza(@RequestBody Dados001 MsgJson) { // @RequestBody String MsgJson

        System.out.println("Comunicação com o Atualizador:  " + MsgJson.getComcnv() );
        System.out.println("Comunicação com o Concentrador:  " + MsgJson.getComcnc() );
        System.out.println("Comunicação com a UTR:  " + MsgJson.getComutr() );
        System.out.println("Comunicação com o Controlador de Carga 1:  " + MsgJson.getComcc1());
        System.out.println("Comunicação com o Controlador de Carga 2:  " + MsgJson.getComcc2());
        System.out.println("Data e hora da UTR: " + MsgJson.getData() + " - " + MsgJson.getClk());
        System.out.println("Modo de Operação: " + MsgJson.getMdop() + "  - Modo de Comando: " + MsgJson.getMdcom());
        System.out.println("Modo de Controle Carga 1: " + MsgJson.getMdct1());
        System.out.println("Modo de Controle Cargas 2, 3 e 4: " + MsgJson.getMdct234());
        System.out.println("Energia Carga 1: " + MsgJson.getEncg1() + " - Energia Carga 2: " + MsgJson.getEncg2());
        System.out.println("Energia Carga 3: " + MsgJson.getEncg3() + " - Corrente Carga 3: " + MsgJson.getIcg3());
        System.out.println("Tensão do Barramento: " + MsgJson.getVbat() + " - Tensão da Rede: " + MsgJson.getVrede());
        System.out.println("Estado da Tensão da Rede: " + MsgJson.getEstvrd()
                            + " - Temperatura das Baterias: " + MsgJson.getTbat());
        System.out.println("Temperatura do Trafo Inv 2: " + MsgJson.getTtiv2());
        System.out.println("Temperatura do Trafo Inv 1: " + MsgJson.getTtiv1());

        String RspSrv = " { \"cmd\" : \"ack\" }";
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(RspSrv);
    }
}
