package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.services.SiteService;

@RestController
public class SiteResources {

    @Autowired
    private SiteService siteService;

    @GetMapping(value = "/isis")
    public ResponseEntity<?> InicioSite(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "indice.html";

        return siteService.LeArquivoMontaResposta("recursos/site/", nomeArquivo, userAgent);
   }

    @GetMapping(value = "/site.{nomeArquivo}")
    public ResponseEntity<?> EnviaHtmlSite(@PathVariable("nomeArquivo") String nomeArquivo,
                                           @RequestHeader(value = "User-Agent") String userAgent) {

        return siteService.LeArquivoMontaResposta("recursos/site/", nomeArquivo, userAgent);
  }

}
