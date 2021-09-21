package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SiteService;

@RestController
public class VlglResources {

    @Autowired
    private SiteService siteService;

    @GetMapping(value = "/vlgl")
    public ResponseEntity<?> InicioVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "indice.html";

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl.{nomeArquivo}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @PostMapping(value = "/reserva")
    public ResponseEntity<?> RecebeDados(@RequestBody String dado) {
        System.out.println("Dado Recebido no Método POST: " + dado);

        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<RESERVA>\n" +
                          "  <CLIENTE>Isis Dias Vieira</CLIENTE>\n" +
                          "</RESERVA>\n ";

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

}
