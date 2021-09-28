package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Cliente;
import praxsoft.SrvHTTP02.services.Auxiliar;
import praxsoft.SrvHTTP02.services.SiteService;
import praxsoft.SrvHTTP02.services.VlglService;

@RestController
public class VlglResources {

    @Autowired
    private SiteService siteService;
    //private VlglService vlglService;

    @GetMapping(value = "/vlgl/admin")
    public ResponseEntity<?> EnviaDadosAdmin() {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/admin", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String MsgXML = VlglService.MontaXMLadmin(auth.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeArquivo;
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/reservas", false);

        if (VlglService.VerificaAdmin(auth.getName())) {
            nomeArquivo = "adminreservas.html";
        }
        else {
            nomeArquivo = "reservas.html";
        }
        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/cadastro")
    public ResponseEntity<?> CadastroVlgl(@RequestHeader(value = "User-Agent") String userAgent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nomeArquivo = "";
        if (VlglService.VerificaAdmin(auth.getName())) {
            VlglService.IniciaVariaveis();;
            nomeArquivo = "admincadastro.html";
        }
        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/data/{recurso}")
    public ResponseEntity<?> VerificaData(@PathVariable String recurso) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/data/" + recurso, false);

        VlglService.setDataReserva(recurso);
        String MsgXML = VlglService.MontaXMLData();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/cliente/{nomeUsuarioCliente}")
    public ResponseEntity<?> VerificaCliente(@PathVariable String nomeUsuarioCliente) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/cliente/" + nomeUsuarioCliente, false);

        String MsgXML = VlglService.MontaXMLCliente(nomeUsuarioCliente);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/aux/{recurso}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("recurso") String nomeArquivo,
                                              @RequestHeader(value = "User-Agent") String userAgent) {

        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/aux/" + nomeArquivo, false);
        return siteService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @PostMapping(value = "/vlgl/confirma")
    public ResponseEntity<?> ConfirmaReserva(@RequestBody String dadosMensagem) {
        Auxiliar.Terminal("Método POST - Recurso solicitado: /vlgl/confirma", false);

        VlglService.ConfirmaReserva(dadosMensagem);
        String MsgXML = VlglService.MontaXMLdataCliente();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PostMapping(value = "/vlgl/cadastra")
    public ResponseEntity<?> CadastraCliente(@RequestBody Cliente cliente) {
        Auxiliar.Terminal("Método POST - Recurso solicitado: /vlgl/cadastra", false);

        VlglService.GeraCadastroCliente(cliente);
        String MsgXML = VlglService.MontaXMLCliente(cliente.getNomeUsuario());
        //System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/favicon.ico")
    public ResponseEntity<?> EnviaIcone() {

        return siteService.LeArquivoMontaResposta("recursos/vlgl/", "favicon.ico", "null");
    }

}
