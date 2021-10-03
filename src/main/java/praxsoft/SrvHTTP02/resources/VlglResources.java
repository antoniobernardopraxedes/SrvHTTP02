package praxsoft.SrvHTTP02.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import praxsoft.SrvHTTP02.domain.Cliente;
import praxsoft.SrvHTTP02.domain.DadosMesa;
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
        String nomeArquivo = "adminreservas.html";

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/reservas/data/{dataReserva}")
    public ResponseEntity<?> VerificaData(@PathVariable String dataReserva) {
        Auxiliar.Terminal("Solicitação de reservas na data: " + dataReserva, false);

        String MsgXML = vlglService.MontaXMLData(dataReserva);

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PostMapping(value = "/vlgl/reservas")
    public ResponseEntity<?> ConfirmaReserva(@RequestBody ReservaMesa reservaMesa) {
        Auxiliar.Terminal("Solicitação de reserva de mesa", false);

        reservaMesa.MostraCamposTerminal();

        boolean confirma = vlglService.ReservaMesa(reservaMesa);
        String MsgXML = vlglService.MontaXMLReserva(reservaMesa, confirma);

        System.out.println(MsgXML);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @DeleteMapping(value = "/vlgl/reserva/exclui/{dataReserva}/{idMesa}")
    public ResponseEntity<?> ExcluiReserva(@PathVariable String dataReserva, @PathVariable String idMesa) {
        Auxiliar.Terminal("Requisição de exclusão: " + dataReserva + " - Mesa: " + idMesa, false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean confirma = vlglService.ExcluiReservaMesa(dataReserva, idMesa);
        String MsgXML = vlglService.MontaXMLExclui(dataReserva, idMesa, auth.getName(), confirma);

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/cadastro")
    public ResponseEntity<?> CadastroVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        Auxiliar.Terminal("Método GET - Recurso solicitado: /vlgl/cadastro", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nomeArquivo = "admincadastro.html";

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/cadastro/cliente/{nomeUsuario}")
    public ResponseEntity<?> VerificaCliente(@PathVariable String nomeUsuario) {
        Auxiliar.Terminal("Solicitação de verificação de cliente: " + nomeUsuario, false);

        String MsgXML = vlglService.MontaXMLCliente(nomeUsuario);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PostMapping(value = "/vlgl/cadastro/cliente")
    public ResponseEntity<?> CadastraCliente(@RequestBody Cliente cliente) {
        Auxiliar.Terminal("Solicitação de inclusão de cliente: " + cliente.getNomeUsuario(), false);

        vlglService.GeraCadastroCliente(cliente);
        String MsgXML = vlglService.MontaXMLCliente(cliente.getNomeUsuario());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @DeleteMapping(value = "/vlgl/cadastro/cliente/{nomeUsuario}")
    public ResponseEntity<?> ExcluiCadastroCliente(@PathVariable String nomeUsuario) {
        Auxiliar.Terminal("Solicitação de inclusão de cliente: " + nomeUsuario, false);

        vlglService.ExcluiCadastroCliente(nomeUsuario);
        String MsgXML = vlglService.MontaXMLCliente(nomeUsuario);

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

    @GetMapping(value = "/favicon.ico")
    public ResponseEntity<?> EnviaIcone() {

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", "favicon.ico", "null");
    }

}
