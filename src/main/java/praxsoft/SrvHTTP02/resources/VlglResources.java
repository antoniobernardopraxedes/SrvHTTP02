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
import praxsoft.SrvHTTP02.services.Arquivo;
import praxsoft.SrvHTTP02.services.VlglService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class VlglResources {

    @Autowired
    private VlglService vlglService;

    @GetMapping(value = "/vlgl/admin")
    public ResponseEntity<?> EnviaDadosAdmin() {
        vlglService.Terminal("Método GET - Recurso solicitado: /vlgl/admin", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String MsgXML = vlglService.MontaXMLadmin(auth.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/reservas")
    public ResponseEntity<?> ReservasVlgl(@RequestHeader(value = "User-Agent") String userAgent) {
        vlglService.Terminal("Método GET - Recurso solicitado: /vlgl/reservas", false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nomeArquivo = "adminreservas.html";

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/reservas/data/{dataReserva}")
    public ResponseEntity<?> VerificaData(@PathVariable String dataReserva) {
        vlglService.Terminal("Solicitação de reservas na data: " + dataReserva, false);

        //String MsgXML = vlglService.MontaXMLData(dataReserva);

        //return ResponseEntity
        //        .status(HttpStatus.OK)
        //        .contentType(MediaType.valueOf("application/xml"))
        //        .body(MsgXML);

        ReservaMesa[] reservaMesas = vlglService.LeArquivoReservaMesa(dataReserva);
        List<ReservaMesa[]> MsgJson = new ArrayList<ReservaMesa[]>(Collections.singleton(reservaMesas));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(MsgJson);
    }

    @PostMapping(value = "/vlgl/reserva")
    public ResponseEntity<?> ConfirmaReserva(@RequestBody ReservaMesa reservaMesa) {
        vlglService.Terminal("Solicitação de reserva de mesa", false);

        boolean confirma = vlglService.EscreveArquivoReservaMesa(reservaMesa);
        String MsgXML = vlglService.MontaXMLReserva(reservaMesa, confirma);
        vlglService.GeraArquivoImpressaoReserva(reservaMesa);
        vlglService.GeraArquivoRegistroReserva(reservaMesa);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @DeleteMapping(value = "/vlgl/reserva/exclui/{dataReserva}/{idMesa}")
    public ResponseEntity<?> ExcluiReserva(@PathVariable String dataReserva, @PathVariable String idMesa) {
        vlglService.Terminal("Solicitação de exclusão: " + dataReserva + " - Mesa: " + idMesa, false);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        vlglService.ExcluiReservaMesa(dataReserva, idMesa);
        String MsgXML = vlglService.MontaXMLExclui(dataReserva, idMesa, auth.getName());
        vlglService.GeraArquivoExclusaoReserva(dataReserva, idMesa, auth.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @GetMapping(value = "/vlgl/reserva/consulta/{dataReservaidMesa}")
    public ResponseEntity<?> ConsultaReserva(@PathVariable String dataReservaidMesa) {
        String dataReserva = dataReservaidMesa.substring(0, 10);
        String idMesa = dataReservaidMesa.substring(10, 13);
        vlglService.Terminal("Solicitação de consulta - Data: " + dataReserva + " - Mesa: " + idMesa, false);

        ReservaMesa reservaMesa = vlglService.ConsultaReservaMesa(dataReserva, idMesa);
        //List<ReservaMesa> MsgJson = new ArrayList<ReservaMesa>(Collections.singletonList(reservaMesa));

        boolean resultado = vlglService.GeraArquivoImpressaoReserva(reservaMesa);
        String MsgXML = vlglService.MontaXMLConsulta(reservaMesa, resultado);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(reservaMesa);
    }

    @GetMapping(value = "/vlgl/reserva/impressao")
    public ResponseEntity<?> EnviaArquivoImpressao() {
        vlglService.Terminal("Solicitação do arquivo de impressão", false);
        String caminho = Arquivo.getDiretorioBd() + "relatorios/";
        String nomeArquivo = vlglService.getNomeArquivoImpressao();
        String arquivoImpressao = Arquivo.LeTexto(caminho, nomeArquivo);

        if (arquivoImpressao != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("text/html"))
                    .header("Content-Disposition", "attachment; filename=" + nomeArquivo)
                    .body(arquivoImpressao);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND )
                    .contentType(MediaType.valueOf("text/html"))
                    .body("Arquivo não encontrado: " + nomeArquivo);
        }
    }

    @GetMapping(value = "/vlgl/cadastro")
    public ResponseEntity<?> CadastroVlgl(@RequestHeader(value = "User-Agent") String userAgent) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nomeArquivo = "admincadastro.html";

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/vlgl/cadastro/cliente/{nomeUsuario}")
    public ResponseEntity<?> VerificaCliente(@PathVariable String nomeUsuario) {
        vlglService.Terminal("Verificação de cliente: " + nomeUsuario, false);

        String MsgXML = vlglService.MontaXMLCliente(nomeUsuario);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PostMapping(value = "/vlgl/cadastro/cliente")
    public ResponseEntity<?> CadastraCliente(@RequestBody Cliente cliente) {
        vlglService.Terminal("Cadastro de cliente: " + cliente.getNomeUsuario(), false);

        vlglService.GeraCadastroCliente(cliente);
        String MsgXML = vlglService.MontaXMLCliente(cliente.getNomeUsuario());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @PutMapping(value = "/vlgl/cadastro/cliente")
    public ResponseEntity<?> AtualizaCadastroCliente(@RequestBody Cliente cliente) {
        vlglService.Terminal("Atualização de cadastro de cliente: " + cliente.getNomeUsuario(), false);

        vlglService.AtualizaCadastroCliente(cliente);
        String MsgXML = vlglService.MontaXMLCliente(cliente.getNomeUsuario());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);
    }

    @DeleteMapping(value = "/vlgl/cadastro/cliente/{nomeUsuario}")
    public ResponseEntity<?> ExcluiCadastroCliente(@PathVariable String nomeUsuario) {
        vlglService.Terminal("Exclusão de cadastro de cliente: " + nomeUsuario, false);

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

        vlglService.Terminal("Método GET - Recurso solicitado: /vlgl/aux/" + nomeArquivo, false);

        return vlglService.LeArquivoMontaResposta("recursos/vlgl/", nomeArquivo, userAgent);
    }

    @GetMapping(value = "/teste/data/{dataReserva}")
    public ResponseEntity<?> EnviaDataJson(@PathVariable String dataReserva) {
        vlglService.Terminal("Teste - Solicitação de reservas na data: " + dataReserva, false);

        ReservaMesa[] reservaMesas = vlglService.LeArquivoReservaMesa(dataReserva);

        List<ReservaMesa[]> MsgJson = new ArrayList<ReservaMesa[]>(Collections.singleton(reservaMesas));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(MsgJson);
    }

    @GetMapping(value = "/teste/consulta/{dataReservaidMesa}")
    public ResponseEntity<?> EnviaReservaJson(@PathVariable String dataReservaidMesa) {
        String dataReserva = dataReservaidMesa.substring(0, 10);
        String idMesa = dataReservaidMesa.substring(10, 13);
        vlglService.Terminal("Solicitação de consulta - Data: " + dataReserva + " - Mesa: " + idMesa, false);

        ReservaMesa reservaMesa = vlglService.ConsultaReservaMesa(dataReserva, idMesa);
        List<ReservaMesa> MsgJson = new ArrayList<ReservaMesa>(Collections.singletonList(reservaMesa));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/json"))
                .body(MsgJson);
    }

}
