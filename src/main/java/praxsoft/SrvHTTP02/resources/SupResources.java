package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Artigo;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.SupService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

@RestController
public class SupResources {

    @Autowired
    private SupService supService;

    private String comando;

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

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(comando));
    }

    @PostMapping(value = "/atualiza")
    public String Atualiza(@RequestBody Dados001 dados001) {

        String RspSrv = " { \"cmd\" : " + comando + " }";
        comando = "ack";

        System.out.println("Recebida Mensagem de Atualização");

        return RspSrv;
    }

    @PostMapping(value = "/cmd={id}")
    public ResponseEntity<?> RecComando(@PathVariable("id") String id) {

        comando = "cmd=" + id;

        System.out.println("Recebido Comando: " + id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(comando));

    }
}
