package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SiteService;
import praxsoft.SrvHTTP02.services.VlglService;

@RestController
public class VlglResources {

    @Autowired
    private SiteService siteService;

    @GetMapping(value = "/admin")
    public ResponseEntity<?> AdminVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "admin.html";

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "reservas.html";

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl.{nomeArquivo}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @PostMapping(value = "/reserva")
    public ResponseEntity<?> RecebeDados(@RequestBody String idUsuario) {
        System.out.println("Dado Recebido no Método POST: " + idUsuario);

        String[] dadosUsuario = VlglService.BuscaUsuario(idUsuario);

        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<RESERVA>\n" +
                          "  <ID>" + dadosUsuario[0] + "</ID>\n" +
                          "  <CLIENTE>" + dadosUsuario[1] + "</CLIENTE>\n" +
                          "</RESERVA>\n ";

        //System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

}
