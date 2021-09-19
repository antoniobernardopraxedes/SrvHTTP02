package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
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

        nomeArquivo = siteService.VerificaMobile(userAgent, nomeArquivo);

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo);
    }

    @GetMapping(value = "/vlgl.{nomeArquivo}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        nomeArquivo = siteService.VerificaMobile(userAgent, nomeArquivo);

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo);
    }

}
