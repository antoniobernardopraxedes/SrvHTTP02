package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SiteService;
import praxsoft.SrvHTTP02.services.VlglService;

@RestController
public class VlglResources {

    @Autowired
    private SiteService siteService;
    private static String idUsuario = "null";
    private static boolean usuarioAdmin = false;

    @GetMapping(value = "/admin")
    public ResponseEntity<?> AdminVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "admin.html";

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        idUsuario = auth.getName();
        String[] dadosUsuario = VlglService.BuscaUsuario(idUsuario);

        String nomeArquivo = "";
        if (dadosUsuario[1].equals("admin")) {
            usuarioAdmin = true;
            nomeArquivo = "adminreservas.html";
        }
        else {
            usuarioAdmin = false;
            nomeArquivo = "reservas.html";
        }

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    protected void LogoutSessao(HttpSecurity http) throws Exception {
        http.logout();
    }

    @GetMapping(value = "/vlgl.{nomeArquivo}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @PostMapping(value = "/reserva")
    public ResponseEntity<?> RecebeDados(@RequestBody String dadosReq) {
        System.out.println("Dado Recebido no Método POST: " + dadosReq);

        String[] dadosUsuario;

        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

        if (usuarioAdmin) {
            if (dadosReq.equals("nomeAdmin")) {
                dadosUsuario = VlglService.BuscaUsuario(idUsuario);
                MsgXML = MsgXML + "<RESERVA>\n" +
                        "  <ID>" + dadosUsuario[0] + "</ID>\n" +
                        "  <ADMIN>" + dadosUsuario[2] + "</ADMIN>\n" +
                        "  <CLIENTE>" + "null" + "</CLIENTE>\n" +
                        "</RESERVA>\n ";
            }
            else {
                dadosUsuario = VlglService.BuscaUsuario(dadosReq);
                MsgXML = MsgXML + "<RESERVA>\n" +
                        "  <ID>" + dadosUsuario[0] + "</ID>\n" +
                        "  <ADMIN>" + dadosUsuario[1] + "</ADMIN>\n" +
                        "  <CLIENTE>" + dadosUsuario[2] + "</CLIENTE>\n" +
                        "</RESERVA>\n ";
            }
        }
        else {
            dadosUsuario = VlglService.BuscaUsuario(idUsuario);
            MsgXML = MsgXML + "<RESERVA>\n" +
                    "  <ID>" + dadosUsuario[0] + "</ID>\n" +
                    "  <ADMIN>" + dadosUsuario[1] + "</ADMIN>\n" +
                    "  <CLIENTE>" + dadosUsuario[2] + "</CLIENTE>\n" +
                    "</RESERVA>\n ";
        }

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

}
