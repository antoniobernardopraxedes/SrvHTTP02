package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Cliente;
import praxsoft.SrvHTTP02.domain.ReservaMesa;
import praxsoft.SrvHTTP02.services.Auxiliar;
import praxsoft.SrvHTTP02.services.VlglService;

@RestController
public class VlglResources {

    @Autowired
    private VlglService vlglService;

    @GetMapping(value = "/vlgl/admin")
    public ResponseEntity<?> EnviaDadosAdmin() {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/admin", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String MsgXML = vlglService.MontaXMLadmin(auth.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/reservas", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeArquivo;

        if (vlglService.VerificaAdmin(auth.getName())) {
            nomeArquivo = "adminreservas.html";
        }
        else {
            nomeArquivo = "reservas.html";
        }
        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/cadastro")
    public ResponseEntity<?> CadastroVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/cadastro", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nomeArquivo = "";
        if (vlglService.VerificaAdmin(auth.getName())) {
            nomeArquivo = "admincadastro.html";
        }
        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/data/{dataReserva}")
    public ResponseEntity<?> VerificaData(@PathVariable String dataReserva) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/data/" + dataReserva, false);

        String MsgXML = vlglService.MontaXMLData(dataReserva);

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/cliente/{nomeUsuarioCliente}")
    public ResponseEntity<?> VerificaCliente(@PathVariable String nomeUsuarioCliente) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/cliente/" + nomeUsuarioCliente, false);

        String MsgXML = vlglService.MontaXMLCliente(nomeUsuarioCliente);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/aux/{recurso}")
    public ResponseEntity<?> EnviaArquivoVlgl(@PathVariable("recurso") String nomeArquivo,
                                              @RequestHeader(value = "User-Agent") String userAgent) {

        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/aux/" + nomeArquivo, false);

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @PostMapping(value = "/vlgl/confirma")
    public ResponseEntity<?> ConfirmaReserva(@RequestBody ReservaMesa reservaMesa) {
        Auxiliar.Terminal("Método POST - Recurso solicitado: /vlgl/confirma", false);

        reservaMesa.MostraCamposTerminal();
        boolean confirma = VlglService.AtualizaArquivo(reservaMesa);
        String MsgXML = vlglService.MontaXMLConfirma(reservaMesa, confirma);

        System.out.println(MsgXML);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PostMapping(value = "/vlgl/cadastra")
    public ResponseEntity<?> CadastraCliente(@RequestBody Cliente cliente) {
        Auxiliar.Terminal("Método POST - Recurso solicitado: /vlgl/cadastra", false);

        VlglService.GeraCadastroCliente(cliente);
        String MsgXML = vlglService.MontaXMLCliente(cliente.getNomeUsuario());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/favicon.ico")
    public ResponseEntity<?> EnviaIcone() {

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", "favicon.ico", "null");
    }

}
