package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Artigo;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.Inicia;
import praxsoft.SrvHTTP02.services.SupService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

@RestController
public class SupResources {

    @Autowired
    private SupService supService;
    //boolean local = true;
    boolean Verbose = true;
    //private String IPConcArd = "192.168.0.150";
    private int ContMsgCoAP = 0;
    private int TamMsgCoAP = 320;

    private int numComando;
    private String strComando;

    @RequestMapping(value = "/supervisao", method = RequestMethod.GET)
    public String supHtml() {
        Verbose = Inicia.isVerbose();
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

        if (Inicia.isOpLocal()) {
            String IPConcArd = Inicia.getEndIpConc();
            byte[] MsgRec = SupService.ClienteCoAPUDP(IPConcArd, "estados", ContMsgCoAP, numComando, Verbose);
            Dados001.LeEstMedsPayload(MsgRec);
        }

        SupService.Terminal("Recebida Requisição de Atualização do Cliente", false, Verbose);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));
    }

    @PostMapping(value = "/atualiza")
    public ResponseEntity<?> Atualiza(@RequestBody Dados001 dados001) {

        String RspSrv = " { \"cmd\" : " + strComando + " }";
        strComando = "cmd=0000";

        SupService.Terminal("Recebida Mensagem de Atualização", false, Verbose);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(RspSrv);
    }

    @PostMapping(value = "/cmd={id}")
    public ResponseEntity<?> RecComando(@PathVariable("id") String id) {

        strComando = "cmd=" + id;
        numComando = SupService.StringToInt(id);
        SupService.Terminal("Comando Recebido do Cliente: " + strComando, false, Verbose);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));

    }
}
