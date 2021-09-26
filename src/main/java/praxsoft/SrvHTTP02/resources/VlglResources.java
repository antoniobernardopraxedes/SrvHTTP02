package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SiteService;
import praxsoft.SrvHTTP02.services.VlglService;

@RestController
public class VlglResources {

    @Autowired
    private SiteService siteService;
    //private VlglService vlglService;

    @GetMapping(value = "/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nomeArquivo = "";
        if (VlglService.VerificaAdmin(auth.getName())) {
            VlglService.IniciaVariaveis();;
            nomeArquivo = "adminreservas.html";
        }
        else {
            nomeArquivo = "reservas.html";
        }
        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl.{nomeArquivo}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/favicon.ico")
    public ResponseEntity<?> EnviaIcone() {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", "favicon.ico", "null");
    }

    @PostMapping(value = "/reserva")
    public ResponseEntity<?> RecebeDados(@RequestBody String dadosCliente) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idUsuario = auth.getName();

        return  VlglService.ExecutaComandos(dadosCliente, idUsuario);

    }

}
