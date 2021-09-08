package praxsoft.SrvHTTP02.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import praxsoft.SrvHTTP02.domain.Artigo;
import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.SupService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

import javax.xml.bind.JAXBException;

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
    public String atualizaVariaveis() throws JAXBException {
        String msgXML = "";
        msgXML = supService.MontaMsg();
        return msgXML;
    }

}
