package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.DadosVlgl;
import praxsoft.SrvHTTP02.services.Auxiliar;
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

        String nomeArquivo = "";
        if (DadosVlgl.VerificaAdmin(idUsuario)) {
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
    public ResponseEntity<?> RecebeDados(@RequestBody String dadosCliente) {
        System.out.println("Dado Recebido no Método POST: " + dadosCliente);

        String MsgXML = "null";

        // Se o Administrador fez login - está fazendo a reserva
        if (usuarioAdmin) {
            if (dadosCliente.equals("nomeAdmin")) {
                MsgXML = DadosVlgl.MontaXMLadmin(idUsuario);
            }
            else {
                String idCliente = Auxiliar.LeParametroArquivo(dadosCliente, "IdCliente:");
                String dataReserva = Auxiliar.LeParametroArquivo(dadosCliente, "DataReserva:");
                if (idCliente == null) { idCliente = "null"; }
                if (dataReserva == null) { idCliente = "null"; }
                MsgXML = DadosVlgl.MontaXMLclienteMesas(dataReserva, dadosCliente);


                //dadosUsuario = VlglService.BuscaUsuario(dadosReq);
                //dadosAdmin = VlglService.BuscaUsuario(idUsuario);
                //MsgXML = MsgXML + "<RESERVA>\n" +
                //        "  <ID>" + dadosUsuario[0] + "</ID>\n" +
                //        "  <ADMIN>" + dadosAdmin[0] + "</ADMIN>\n" +
                //        "  <CLIENTE>" + dadosUsuario[2] + "</CLIENTE>\n" +
                //        "</RESERVA>\n ";
            }
        }
        else {
            String[] dadosUsuario = VlglService.BuscaUsuario(idUsuario);
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
