package praxsoft.SrvHTTP02.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import praxsoft.SrvHTTP02.services.SiteService;

@RestController
public class GeralResources {

    @Autowired
    private SiteService siteService;

    @GetMapping(value = "/")
    public ResponseEntity<?> inicio(@RequestHeader(value = "User-Agent") String userAgent) {
        String nomeArquivo = "index.html";

        return siteService.LeArquivoMontaResposta("recursos/", nomeArquivo, userAgent);
    }

}
