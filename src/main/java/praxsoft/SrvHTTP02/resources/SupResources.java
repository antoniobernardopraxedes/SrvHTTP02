package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.SupService;

@RestController
public class SupResources {

    @Autowired
    private SupService supService;
    private static final String dirSup = "sup/";
    private static String strComando;
    private static int numComando;

    @GetMapping(value = "/sup")
    public ResponseEntity<?> supHtml() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/html"))
                .body(supService.LeArquivoTxt(dirSup, "supcloud.html"));
    }

    @GetMapping(value = "sup.css")
    public ResponseEntity<?> supCss() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/css"))
                .body(supService.LeArquivoTxt(dirSup, "supcloud.css"));
    }

    @GetMapping(value = "sup.js")
    public ResponseEntity<?> supJs() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("text/javascript"))
                .body(supService.LeArquivoTxt(dirSup, "supcloud.js"));
    }

    @GetMapping(value = "/local001.xml")
    public ResponseEntity<?> atualizaVariaveis() {

        if (SupService.isOpLocal()) {
            String EndConcArd = SupService.getEndIpConc();
            byte[] MsgRec = supService.ClienteCoAPUDP(EndConcArd, "estados", numComando);
            numComando = 0;
        }

        supService.Terminal("Recebida Requisição de Atualização", false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));
    }

    @PostMapping(value = "/atualiza")
    public ResponseEntity<?> Atualiza(@RequestBody Dados001 dados001) {

        String RspSrv = " { \"cmd\" : " + strComando + " }";
        strComando = "cmd=0000";

        supService.Terminal("Recebida Mensagem de Atualização", false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(RspSrv);
    }

    @PostMapping(value = "/cmd={id}")
    public ResponseEntity<?> RecComando(@PathVariable("id") String id) {

        strComando = "cmd=" + id;
        numComando = SupService.StringToInt(id);
        supService.Terminal("Comando Recebido do Cliente: " + strComando, false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));

    }
}
