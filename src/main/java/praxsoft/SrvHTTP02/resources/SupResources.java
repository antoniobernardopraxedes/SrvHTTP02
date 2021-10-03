package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Dados001;
import praxsoft.SrvHTTP02.services.*;

@RestController
public class SupResources {

    @Autowired
    private SiteService siteService;
    private SupService supService;
    private static String strComando;
    private static int numComando;

    @GetMapping(value = "/sup")
    public ResponseEntity<?> supHtml(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "tabela.html";

        return siteService.LeArquivoMontaResposta("recursos/sup/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/sup.{nomeArquivo}")
    public ResponseEntity<?> EnviaHtmlSite(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/sup/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/local001.xml")
    public ResponseEntity<?> atualizaVariaveis() {

        if (Arquivo.isOpLocal()) {
            String EndConcArd = Arquivo.getEndIpConc();
            byte[] MsgRec = supService.ClienteCoAPUDP(EndConcArd, "estados", numComando);
            numComando = 0;
        }

        Auxiliar.Terminal("Recebida Requisição de Atualização do Cliente", false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));
    }

    @PostMapping(value = "/atualiza")
    public ResponseEntity<?> Atualiza(@RequestBody Dados001 dados001) {

        String RspSrv = " { \"cmd\" : " + strComando + " }";
        strComando = "cmd=0000";

        Auxiliar.Terminal("Recebida Mensagem de Atualização", false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(RspSrv);
    }

    @PostMapping(value = "/cmd={id}")
    public ResponseEntity<?> RecComando(@PathVariable("id") String id) {

        strComando = "cmd=" + id;
        numComando = Auxiliar.StringToInt(id);
        Auxiliar.Terminal("Comando Recebido do Cliente: " + strComando, false);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(Dados001.MontaXML(strComando, true));

    }

}
