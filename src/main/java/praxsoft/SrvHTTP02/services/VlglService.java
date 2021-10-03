package praxsoft.SrvHTTP02.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Cliente;
import praxsoft.SrvHTTP02.domain.DadosMesa;
import praxsoft.SrvHTTP02.domain.ReservaMesa;
import java.time.LocalDateTime;

@Service
public class VlglService {

    private String[][][][] MsgXMLArray = new String[1][4][120][2];

    //******************************************************************************************************************
    // Nome do Método: GeraCadastroCliente                                                                             *
    //	                                                                                                               *
    // Data: 28/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: recebe os dados de uma mensagem POST solicitando o cadastro de um cliente, atualiza as variáveis        *
    //         e grava o registro.                                                                                     *
    //                                                                                                                 *
    // Entrada: string com os dados da mensagem recebida no método POST                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public boolean GeraCadastroCliente(Cliente cliente) {

        boolean confirma = false;
        cliente.MostraCamposTerminal();
        String caminho = Arquivo.getDiretorioBd() + "clientes/";
        String nomeArquivo = cliente.getNomeUsuario() + ".clt";

        String dadosArqNovo = "{\n";
        dadosArqNovo = dadosArqNovo + "  NomeUsuario: " + cliente.getNomeUsuario() + "\n";
        dadosArqNovo = dadosArqNovo + "  Nome: " + cliente.getNome() + "\n";
        dadosArqNovo = dadosArqNovo + "  Celular: " + cliente.getCelular() + "\n";
        dadosArqNovo = dadosArqNovo + "  Obs1: " + cliente.getObs1() + "\n";
        dadosArqNovo = dadosArqNovo + "  Obs2: " + cliente.getObs2() + "\n";
        dadosArqNovo = dadosArqNovo + "  Idoso: " + cliente.getIdoso() + "\n";
        dadosArqNovo = dadosArqNovo + "  Locomocao: " + cliente.getLocomocao() + "\n";
        dadosArqNovo = dadosArqNovo + "  Exigente: " + cliente.getExigente() + "\n";
        dadosArqNovo = dadosArqNovo + "  Genero: " + cliente.getGenero() + "\n";
        dadosArqNovo = dadosArqNovo + "  AdminResp: " + cliente.getAdminResp() + "\n";
        dadosArqNovo = dadosArqNovo + "}\n";

        // Se o arquivo existe, renomeia para a.nomearquivo.clt e grava o novo arquivo de cadastro de cliente
        if (Arquivo.Existe(caminho, "a." + nomeArquivo)) {
            Arquivo.Apaga(caminho, "a." + nomeArquivo);
            if (Arquivo.Renomeia(caminho, nomeArquivo, "a." + nomeArquivo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " renomeado", false);
                if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                    Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
                    confirma = true;
                }
            }
        }
        else { // Se o arquivo não existe, grava o novo arquivo de cadastro de cliente
            if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
                confirma = true;
            }
        }
        return confirma;
    }

    //******************************************************************************************************************
    // Nome do Método: ExcluiCadastroCliente                                                                           *
    //	                                                                                                               *
    // Data: 03/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: recebe o nome de usuário de um cliente em uma mensagem DELETE solicitando a exclusão do cadastro        *
    //          deste cliente. Se o cliente existir, apaga o arquivo de cadastro correspondente.                       *                                                                                     *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente                                                                *
    //                                                                                                                 *
    // Saida: boolean - true se a operação foi realizada corretamente                                                  *
    //******************************************************************************************************************
    //
    public boolean ExcluiCadastroCliente(String nomeUsuario) {

        boolean confirma = false;
        String caminho = Arquivo.getDiretorioBd() + "clientes/";
        String nomeArquivo = nomeUsuario + ".clt";

        if (Arquivo.Existe(caminho, nomeArquivo)) {
            if (Arquivo.Apaga(caminho, nomeArquivo)) {
                confirma = true;
            }
        }
        return confirma;
    }

    //******************************************************************************************************************
    // Nome do Método: ReservaMesa                                                                                     *
    //	                                                                                                               *
    // Data: 24/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo de data com as informações das mesas, atualiza a reserva confirmada e grava novamente     *
    //         o arquivo.                                                                                              *
    //                                                                                                                 *
    // Entrada: objeto da classe ReservaMesa com as informações sobre a reserva solicitada                             *
    //                                                                                                                 *
    // Saida: boolean true se a operação foi realizada corretamente                                                    *
    //******************************************************************************************************************
    //
    public boolean ReservaMesa(ReservaMesa reservaMesa) {

        DadosMesa dadosMesa = LeArquivoReservaMesa(reservaMesa.getDataReserva());

        int indiceMesa = Integer.parseInt(reservaMesa.getMesaSelecionada().substring(1,3));

        dadosMesa.setNomeUsuario(reservaMesa.getNomeUsuario(), indiceMesa);
        dadosMesa.setNomeCompleto(reservaMesa.getNomeCliente(), indiceMesa);
        dadosMesa.setNumeroPessoas(reservaMesa.getNumPessoas(), indiceMesa);
        dadosMesa.setHoraChegada(reservaMesa.getHoraChegada(), indiceMesa);
        dadosMesa.setAdminResponsavel(reservaMesa.getAdminResp(), indiceMesa);
        dadosMesa.setHoraRegistro(ImpHora(), indiceMesa);
        dadosMesa.setDataRegistro(ImpData(), indiceMesa);

        return EscreveArquivoReservaMesa(reservaMesa.getDataReserva(), dadosMesa);
    }

    //******************************************************************************************************************
    // Nome do Método: ExcluiReservaMesa                                                                               *
    //	                                                                                                               *
    // Data: 03/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo de data com as informações de reserva das mesas, exclui a reserva da mesa selecionada     *
    //         e grava novamente o arquivo.                                                                            *
    //                                                                                                                 *
    // Entrada: string com a data da reserva e identificador da mesa                                                   *
    //                                                                                                                 *
    // Saida: boolean true se a operação foi realizada corretamente                                                    *
    //******************************************************************************************************************
    //
    public boolean ExcluiReservaMesa(String dataReserva, String idMesa) {

        DadosMesa dadosMesa = LeArquivoReservaMesa(dataReserva);

        int indiceMesa = Integer.parseInt(idMesa.substring(1,3));

        dadosMesa.setNomeUsuario("livre", indiceMesa);
        dadosMesa.setNomeCompleto("null", indiceMesa);
        dadosMesa.setNumeroPessoas("null", indiceMesa);
        dadosMesa.setHoraChegada("null", indiceMesa);
        dadosMesa.setAdminResponsavel("null", indiceMesa);
        dadosMesa.setHoraRegistro("null", indiceMesa);
        dadosMesa.setDataRegistro("null", indiceMesa);

        return EscreveArquivoReservaMesa(dataReserva, dadosMesa);
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLadmin()                                                                                 *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes ao administrador que está fazendo as reservas.       *
    //         As informações do cliente são lidas de um arquivo texto que tem o nome igual ao id do administrador.    *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do administrador (idAdmin). O idAdmin não deve ter espaços nem caracteres *
    //          especiais.                                                                                             *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLadmin(String idAdmin) {

        String nomeAdmin = idAdmin;

        int i = 0;
        int IdNv0 = 0;
        int IdNv1 = 1;
        IniciaMsgXML("LOCAL001", 1);

        MsgXMLArray[IdNv0][IdNv1][i++][0] = "ADMIN";
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ID", idAdmin);
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NOME", nomeAdmin);
        MsgXMLArray[IdNv0][IdNv1][0][1] = IntToStr2(i - 1);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLCliente()                                                                               *
    //	                                                                                                               *
    // Data: 2%/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes do cliente. As informações do cliente são lidas      *
    //         de um arquivo texto que tem o nome igual ao nome de usuário.                                            *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente                                                                *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLCliente(String nomeUsuario) {

        IniciaMsgXML("LOCAL001", 1);
        CarregaClienteArray(nomeUsuario, 1);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLData()                                                                                  *
    //	                                                                                                               *
    // Data: 25/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes à disponibilidade das mesas em uma data. As          *
    //         informações são lidas de um arquivo do tipo texto que tem o nome no formato DD-MM-AAAA.res              *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD/MM/AAAA                                                     *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLData(String dataReserva) {

        IniciaMsgXML("LOCAL001", 1);
        CarregaDataArray(dataReserva, 1);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLReserva                                                                                 *
    //	                                                                                                               *
    // Data: 29/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações de estado e as informações de disponibilidade das mesas na      *
    //         data especificada                                                                                       *
    //                                                                                                                 *
    // Entrada: objeto da classe ReservaMesa e boolean confirma                                                        *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLReserva(ReservaMesa reservaMesa, boolean confirma) {

        IniciaMsgXML("LOCAL001", 2);
        CarregaEstadoArray(reservaMesa, confirma, false, 1);
        CarregaDataArray(reservaMesa.getDataReserva(), 2);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLExclui                                                                                  *
    //	                                                                                                               *
    // Data: 29/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações de estado e as informações de disponibilidade das mesas na      *
    //         data especificada                                                                                       *
    //                                                                                                                 *
    // Entrada: string com a data da reserva, string com o identificador da mesa e string com o nome de usuário do     *
    //          administrador que está fazendo a exclusão da reserva                                                   *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLExclui(String dataReserva, String idMesa, String idAdmin, boolean confirma) {

        ReservaMesa reservaMesa = new ReservaMesa();
        reservaMesa.setNomeUsuario("null");
        reservaMesa.setNomeCliente("null");
        reservaMesa.setDataReserva(dataReserva);
        reservaMesa.setNumPessoas("null");
        reservaMesa.setHoraChegada("null");
        reservaMesa.setMesaSelecionada(idMesa);
        reservaMesa.setAdminResp(idAdmin);
        reservaMesa.setNomeCliente("null");

        IniciaMsgXML("LOCAL001", 2);
        CarregaEstadoArray(reservaMesa, false, confirma, 1);
        CarregaDataArray(dataReserva, 2);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoMontaResposta                                                                          *
    //	                                                                                                               *
    // Data: 29/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo e monta a mensagem de resposta com o conteúdo do arquivo e o tipo. Este método é          *
    //         destinado a ler arquivos do tipo HTML (.html e .htm, CSS (.css), Javascript (.js) e de imagem           *
    //         (.jpg e .ico). Se o cliente é u dispositivo móvel, o método muda o diretório de leitura dos arquivos    *
    //         .css e .js                                                                                              *
    //                                                                                                                 *
    // Entrada: string com o caminho, string com o nome do arquivo e string com o UserAgent do cabeçalho HTTP          *
    //                                                                                                                 *
    // Saida: ResponseEntity de resposta HTML                                                                          *
    //******************************************************************************************************************
    //
    public ResponseEntity<?> LeArquivoMontaResposta(String caminho, String nomeArquivo, String userAgent) {

        String tipo = "text/plain";
        if (nomeArquivo.endsWith(".html")) {
            tipo = "text/html";
        }
        if (nomeArquivo.endsWith(".css")) {
            tipo = "text/css";
            if (userAgent.toLowerCase().contains("mobile")) {
                caminho = caminho + "css_m/";
            }
            else {
                caminho = caminho + "css/";
            }
        }
        if (nomeArquivo.endsWith(".js")) {
            tipo = "text/javascript";
            if (userAgent.toLowerCase().contains("mobile")) {
                caminho = caminho + "js_m/";
            }
            else {
                caminho = caminho + "js/";
            }
        }
        if (nomeArquivo.endsWith(".jpg") || nomeArquivo.endsWith(".ico")) {
            tipo = "image/jpeg";
            caminho = caminho + "img/";
        }
        if (nomeArquivo.endsWith(".png")) {
            tipo = "image/png";
            caminho = caminho + "img/";
        }

        if (tipo.equals("image/jpeg") || tipo.equals("image/png")) {
            byte[] arquivoByte = Arquivo.LeByte(caminho, nomeArquivo);
            if (arquivoByte.length == 0) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND )
                        .contentType(MediaType.valueOf("text/html"))
                        .body(msgArqNaoEncontrado(nomeArquivo));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(tipo))
                        .body(arquivoByte);
            }
        }
        else {
            String arquivoTxt = Arquivo.LeTexto(caminho, nomeArquivo);

            if (arquivoTxt == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND )
                        .contentType(MediaType.valueOf("text/html"))
                        .body(msgArqNaoEncontrado(nomeArquivo));
            }
            else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(tipo))
                        .body(arquivoTxt);
            }
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoReservaMesa                                                                            *
    //	                                                                                                               *
    // Data: 02/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo de registro de reserva das mesas. Se o arquivo não for encontrado, cria um novo arquivo   *
    //
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA                                                     *
    //                                                                                                                 *
    // Saida: objeto da classe DadosMesa com as informações das reservas das mesas na data especificada                *
    //******************************************************************************************************************
    //
    private DadosMesa LeArquivoReservaMesa(String dataReserva) {

        String caminho = Arquivo.getDiretorioBd() + "reservas/";
        String nomeArquivo = dataReserva + ".res";
        if (!Arquivo.Existe(caminho, nomeArquivo)) {
            EscreveArquivoReservaNovo(dataReserva);
        }
        String dadosArquivo = Arquivo.LeTexto(caminho, nomeArquivo);
        DadosMesa dadosMesa = new DadosMesa();

        if (dadosArquivo != null) {

            int numMesas = 17;
            String sufixo;
            String letra = "A";

            for (int i = 0; i < numMesas; i++) {
                if (i > 8) { letra = "B"; }
                sufixo = letra + IntToStr2(i);
                dadosMesa.setNomeUsuario(Arquivo.LeParametro(dadosArquivo, "NOU" + sufixo + ":"), i);
                dadosMesa.setNomeCompleto(Arquivo.LeCampo(dadosArquivo, "NOC" + sufixo + ":"), i);
                dadosMesa.setNumeroPessoas(Arquivo.LeParametro(dadosArquivo, "NUP" + sufixo + ":"), i);
                dadosMesa.setHoraChegada(Arquivo.LeParametro(dadosArquivo, "HOC" + sufixo + ":"), i);
                dadosMesa.setAdminResponsavel(Arquivo.LeParametro(dadosArquivo, "ADR" + sufixo + ":"), i);
                dadosMesa.setHoraRegistro(Arquivo.LeParametro(dadosArquivo, "HOR" + sufixo + ":"), i);
                dadosMesa.setDataRegistro(Arquivo.LeParametro(dadosArquivo, "DTR" + sufixo + ":"), i);
            }
        }
        else {
            Auxiliar.Terminal("Arquivo de reservas do dia " + nomeArquivo + " não encontrado", false);
        }
        return dadosMesa;
    }

    //******************************************************************************************************************
    // Nome do Método: EscreveArquivoReservaMesa                                                                       *
    //	                                                                                                               *
    // Data: 02/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: escreve um arquivo de registro de reservas na data especificada                                         *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA e objeto da classe DadosMesa com as informações     *
    //          de reserva de todas as mesas para a data especificada                                                  *
    //                                                                                                                 *
    // Saida: boolean - true se a operação foi executada corretamente                                                  *
    //******************************************************************************************************************
    //
    private boolean EscreveArquivoReservaMesa(String DataReserva, DadosMesa dadosMesa) {

        boolean confirma = false;
        String caminho = Arquivo.getDiretorioBd() + "reservas/";
        String nomeArquivo = DataReserva + ".res";

        int numMesas = 17;
        String[] nomeUsuario = dadosMesa.getNomeUsuario();
        String[] nomeCompleto = dadosMesa.getNomeCompleto();
        String[] numeroPessoas = dadosMesa.getNumeroPessoas();
        String[] horaChegada = dadosMesa.getHoraChegada();
        String[] adminResponsavel = dadosMesa.getAdminResponsavel();
        String[] horaRegistro = dadosMesa.getHoraRegistro();
        String[] dataRegistro = dadosMesa.getDataRegistro();

        String sufixo;
        String letra = "A";
        String dadosArqNovo = "{\n";

        for (int i = 0; i < numMesas; i++) {
            if (i > 8) { letra = "B"; }
            sufixo = letra + IntToStr2(i);
            dadosArqNovo = dadosArqNovo + "  NOU" + sufixo + ": " + nomeUsuario[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  NOC" + sufixo + ": " + nomeCompleto[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  NUP" + sufixo + ": " + numeroPessoas[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  HOC" + sufixo + ": " + horaChegada[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  ADR" + sufixo + ": " + adminResponsavel[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  HOR" + sufixo + ": " + horaRegistro[i] + "\n";
            dadosArqNovo = dadosArqNovo + "  DTR" + sufixo + ": " + dataRegistro[i] + "\n";
        }
        dadosArqNovo = dadosArqNovo + "}";

        if (Arquivo.Existe(caminho, "a." + nomeArquivo)) {
            Arquivo.Apaga(caminho, "a." + nomeArquivo);
        }
        if (Arquivo.Renomeia(caminho, nomeArquivo, "a." + nomeArquivo)) {
            Auxiliar.Terminal("Arquivo de reservas " + nomeArquivo + " renomeado", false);
            if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                Auxiliar.Terminal("Arquivo de reservas " + nomeArquivo + " modificado e salvo", false);
                confirma = true;
            }
        }
        return confirma;
    }

    //******************************************************************************************************************
    // Nome do Método: EscreveArquivoReservaNovo                                                                       *
    //	                                                                                                               *
    // Data: 27/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: cria um arquivo de reservas novo para a data especificada                                               *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA                                                     *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    private static void EscreveArquivoReservaNovo(String dataRes) {

        //String caminho = "recursos/vlgl/reservas/";
        String caminho = Arquivo.getDiretorioBd() + "reservas/";
        String nomeArquivo = dataRes + ".res";

        int numMesas = 17;
        String Indice;
        String letra = "A";
        String dadosArqNovo = "{\n";
        for (int i = 0; i < numMesas; i++) {
            if (i > 8) { letra = "B"; }
            Indice = Auxiliar.IntToStr2(i);
            dadosArqNovo = dadosArqNovo + "  NOU" + letra + Indice + ": livre\n";
            dadosArqNovo = dadosArqNovo + "  NOC" + letra + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  NUP" + letra + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  HOC" + letra + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  ADR" + letra + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  HOR" + letra + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  DTR" + letra + Indice + ": null\n";
        }
        dadosArqNovo = dadosArqNovo + "}";

        if (!Arquivo.Existe(caminho, "a." + nomeArquivo)) {
            if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                Auxiliar.Terminal("Criado arquivo de reservas novo: " + nomeArquivo, false);
            } else {
                Auxiliar.Terminal("Falha ao criar o arquivo de reservas novo: " + nomeArquivo, false);
            }
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoCadastroCliente                                                                        *
    //	                                                                                                               *
    // Data: 02/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo de cadastro de cliente                                                                    *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente                                                                *
    //                                                                                                                 *
    // Saida: objeto da classe Cliente com as informações do cliente. Se o registro do cliente não for encontrado,     *
    //        retorna com todos os campos iguais a "null"                                                              *
    //******************************************************************************************************************
    //
    private Cliente LeArquivoCadastroCliente(String nomeUsuario) {

        String caminho = Arquivo.getDiretorioBd() + "clientes/";
        String nomeArquivo = nomeUsuario + ".clt";
        String registroCliente = Arquivo.LeTexto(caminho, nomeArquivo);

        Cliente cliente = new Cliente();

        if (registroCliente != null) {
            cliente.setNomeUsuario(Arquivo.LeCampo(registroCliente, "NomeUsuario:"));
            cliente.setNome(Arquivo.LeCampo(registroCliente, "Nome:"));
            cliente.setCelular(Arquivo.LeCampo(registroCliente, "Celular:"));
            cliente.setObs1(Arquivo.LeCampo(registroCliente, "Obs1:"));
            cliente.setObs2(Arquivo.LeCampo(registroCliente, "Obs2:"));
            cliente.setIdoso(Arquivo.LeCampo(registroCliente, "Idoso:"));
            cliente.setLocomocao(Arquivo.LeCampo(registroCliente, "Locomocao:"));
            cliente.setExigente(Arquivo.LeCampo(registroCliente, "Exigente:"));
            cliente.setGenero(Arquivo.LeCampo(registroCliente, "Genero:"));
            cliente.setAdminResp(Arquivo.LeCampo(registroCliente, "AdminResp:"));
        }
        else {
            cliente.setNomeUsuario("null");
            cliente.setNome("null");
            cliente.setCelular("null");
            cliente.setObs1("null");
            cliente.setObs2("null");
            cliente.setIdoso("null");
            cliente.setLocomocao("null");
            cliente.setExigente("null");
            cliente.setGenero("null");
            cliente.setAdminResp("null");
        }
        return cliente;
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaEstadoArray                                                                              *
    //	                                                                                                               *
    // Data: 25/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: carrega as informações de estado no array usado para montar a mensagem XML.                             *
    //                                                                                                                 *
    // Entrada: int com o índice do local e int com o índice do grupo                                                  *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    private void CarregaEstadoArray(ReservaMesa reservaMesa, boolean confirma, boolean exclui, int IdNv1) {

        String confirmaStr = "não"; if (confirma) { confirmaStr = "sim"; }
        String excluiStr = "não"; if (exclui) { excluiStr = "sim"; }

        int i = 0;
        int IdNv0 = 0;
        MsgXMLArray[IdNv0][IdNv1][i++][0] = "ESTADO";
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("RESERVA", confirmaStr);
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("EXCLUI", excluiStr);
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("MESA", reservaMesa.getMesaSelecionada());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("DATA", reservaMesa.getDataReserva());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ID", reservaMesa.getNomeUsuario());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NOME", reservaMesa.getNomeCliente());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NUMPES", reservaMesa.getNumPessoas());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("HORARES", reservaMesa.getHoraChegada());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ADMINRESP", reservaMesa.getAdminResp());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("HORAREG", ImpHora());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("DATAREG", ImpData());
        MsgXMLArray[IdNv0][IdNv1][0][1] = IntToStr2(i - 1);
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaClienteArray                                                                             *
    //	                                                                                                               *
    // Data: 02/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: carrega as informações do cliente no array usado para montar a mensagem XML.                            *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente, int com o índice do local e int com o índice do grupo         *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    private void CarregaClienteArray(String nomeUsuario, int IdNv1) {

        Cliente cliente = LeArquivoCadastroCliente(nomeUsuario);

        int i = 0;
        int IdNv0 = 0;
        MsgXMLArray[IdNv0][IdNv1][i++][0] = "CLIENTE";
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ID", cliente.getNomeUsuario());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NOME", cliente.getNome());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("CELULAR", cliente.getCelular());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("OBS1", cliente.getObs1());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("OBS2", cliente.getObs2());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("IDOSO", cliente.getIdoso());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("LOCOMOCAO", cliente.getLocomocao());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("EXIGENTE", cliente.getExigente());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("GENERO", cliente.getGenero());
        MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ADMINRSP", cliente.getAdminResp());
        MsgXMLArray[IdNv0][IdNv1][0][1] = IntToStr2(i - 1);

    }

    //******************************************************************************************************************
    // Nome do Método: CarregaDataArray                                                                                *
    //	                                                                                                               *
    // Data: 02/10/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: carrega as informações das reservas das mesas na data no array usado para montar a mensagem XML.        *
    //                                                                                                                 *
    // Entrada: string com a data da reserva, int com o índice do local e int com o índice do grupo                    *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    private void CarregaDataArray(String dataReserva, int IdNv1) {

        DadosMesa dadosMesa = LeArquivoReservaMesa(dataReserva);

        String[] nomeUsuario = dadosMesa.getNomeUsuario();
        String[] nomeCompleto = dadosMesa.getNomeCompleto();
        String[] numeroPessoas = dadosMesa.getNumeroPessoas();
        String[] horaChegada = dadosMesa.getHoraChegada();
        String[] adminResponsavel = dadosMesa.getAdminResponsavel();
        String[] horaRegistro = dadosMesa.getHoraRegistro();
        String[] dataRegistro = dadosMesa.getDataRegistro();

        int numMesas = 17;
        int i = 0;
        int IdNv0 = 0;
        MsgXMLArray[IdNv0][IdNv1][i++][0] = "MESAS";

        for (int k = 0; k < numMesas; k++) {
            String letra = "A";
            if (k > 8) { letra = "B"; }
            String sufixo = letra + IntToStr2(k);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NOU" + sufixo, nomeUsuario[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NOC" + sufixo, nomeCompleto[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("NUP" + sufixo, numeroPessoas[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("HOC" + sufixo, horaChegada[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("ADR" + sufixo, adminResponsavel[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("HOR" + sufixo, horaRegistro[k]);
            MsgXMLArray[IdNv0][IdNv1][i++] = EntTagValue("DTR" + sufixo, dataRegistro[k]);
        }
        String numElementosGrupo = IntToStr4(i - 1);
        MsgXMLArray[IdNv0][IdNv1][0][1] = numElementosGrupo;

    }

    //******************************************************************************************************************
    // Nome do Método: StringXML()                                                                                     *
    //	                                                                                                               *
    // Funcao: monta uma String com a mensagem XML de resposta inserindo o valor das variáveis                         *
    //                                                                                                                 *
    // Entrada: array String com as Tags dos Níveis 0, 1 e 2 e os valores das variáveis de supervisão                  *
    //                                                                                                                 *
    // Saida: String com a mensagem XML                                                                                *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    private String StringXML() {
        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";

        int NmTagNv1 = Integer.parseInt(MsgXMLArray[0][0][0][1]);
        for (int i = 1; i <= NmTagNv1; i++) {
            MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";

            int NumVar = Integer.parseInt(MsgXMLArray[0][i][0][1]);
            for (int j = 1; j <= NumVar; j++) {
                MsgXML = MsgXML + "    <" + MsgXMLArray[0][i][j][0] + ">"
                                          + MsgXMLArray[0][i][j][1]
                                   + "</" + MsgXMLArray[0][i][j][0] + ">\n";
            }
            MsgXML = MsgXML + "  </" + MsgXMLArray[0][i][0][0] + ">\n";
        }
        MsgXML = MsgXML + "</" + MsgXMLArray[0][0][0][0] + ">";

        return (MsgXML);
    }

    //******************************************************************************************************************
    // Nome do Método: LeParametroArquivo                                                                              *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o parâmetro que está após o token                        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    //private String LeParametroArquivo(String arquivo, String token){
    //    int Indice = arquivo.lastIndexOf(token);
    //    int indiceF = arquivo.length() - 1;
    //    String parametro = null;
    //    if (Indice >= 0) {
    //        Indice = Indice + token.length() + 1;
    //        String Substring = arquivo.substring(Indice, indiceF);
    //        StringTokenizer parseToken = new StringTokenizer(Substring);
    //        parametro = parseToken.nextToken();
    //    }
    //    return parametro;
    //}

    //******************************************************************************************************************
    // Nome do Método: LeCampoArquivo                                                                                  *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token até o próximo CR/LF        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    //private String LeCampoArquivo(String arquivo, String token) {
    //    String campo;
    //    try {
    //        int indiceToken = arquivo.indexOf(token);
    //        if (indiceToken > 0) {
    //            int indiceAposToken = indiceToken + token.length();

    //            String arquivoAposToken = arquivo.substring(indiceAposToken, arquivo.length());
    //            int indiceCRLF = arquivoAposToken.indexOf("\n");

    //            return arquivoAposToken.substring(1, indiceCRLF);
    //        }
    //        else {
    //            return "null";
    //        }
    //    } catch (Exception e) {
    //        return "null";
    //    }
    //}

    //******************************************************************************************************************
    // Nome do Método: ImpHora                                                                                         *
    //                                                                                                                 *
    // Funcao: gera uma string com a hora recebida em um array de bytes                                                *
    //                                                                                                                 *
    // Entrada: Array[6] [0] = Hora, [1] = Minutos, [2] = Segundos                                                     *
    //                                                                                                                 *
    // Saida: string no formato HH:MM:SS                                                                               *                                                                                 *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    private String ImpHora() {
        LocalDateTime datahora = LocalDateTime.now();
        int Hora = datahora.getHour();
        int Minuto = datahora.getMinute();
        int Segundo = datahora.getSecond();

        String Msg = "";
        if (Hora < 10) { Msg = Msg + "0"; }
        Msg = Msg + Hora + ":";
        if (Minuto < 10) { Msg = Msg + "0"; }
        Msg = Msg + Minuto + ":";
        if (Segundo < 10) { Msg = Msg + "0"; }
        Msg = Msg + Segundo;

        return (Msg);
    }

    //******************************************************************************************************************
    // Nome do Método: ImpData                                                                                         *
    //                                                                                                                 *
    // Funcao: gera uma string com a data recebida em um array de bytes                                                *
    //                                                                                                                 *
    // Entrada: Array[6] [3] = Dia, [4] = Mês, [5] = Ano centena, [6] = Ano unidade                                    *
    //                                                                                                                 *
    // Saida: string no formato HH:MM:SS - DD/MM/AAAA                                                                  *                                                                                 *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    private String ImpData() {
        LocalDateTime datahora = LocalDateTime.now();
        int Dia = datahora.getDayOfMonth();
        int Mes = datahora.getMonthValue();
        int Ano = datahora.getYear();

        String Msg = "";
        if (Dia < 10) { Msg = Msg + "0"; }
        Msg = Msg + Dia + "/";
        if (Mes < 10) { Msg = Msg + "0"; }
        Msg = Msg + Mes + "/" + Ano;

        return (Msg);
    }

    //******************************************************************************************************************
    // Nome da Rotina: IntToStr2                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo na faixa de 00 a 99 para uma string                                        *
    //                                                                                                                 *
    // Entrada: valor inteiro de 0 a 99                                                                                *
    //                                                                                                                 *
    // Saida: string de dois dígitos do número (formato 00 a 99). Se o valor estiver fora da faixa retorna 00          *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    private static String IntToStr2(int valInt) {
        String valStr = "00";
        if ((valInt >= 0) && (valInt < 100)) {
            if (valInt < 10) {
                valStr = ("0" + valInt);
            } else {
                valStr = (valInt + "");
            }
        }
        return (valStr);
    }

    //******************************************************************************************************************
    // Nome da Rotina: IntToStr4                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo na faixa de 0000 a 9999 para uma string                                    *
    //                                                                                                                 *
    // Entrada: valor inteiro de 0 a 99                                                                                *
    //                                                                                                                 *
    // Saida: string de dois dígitos do número (formato 00 a 99). Se o valor estiver fora da faixa retorna 00          *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    private String IntToStr4(int valInt) {
        String valStr = "0000";

        if ((valInt >= 0) && (valInt <= 9999)) {
            if (valInt >= 1000) {
                valStr = valInt + "";
            } else {
                if (valInt >= 100) {
                    valStr = "0" + valInt;
                } else {
                    if (valInt >= 10) {
                        valStr = "00" + valInt;
                    } else {
                        valStr = "000" + valInt;
                    }
                }
            }
        }
        return (valStr);
    }

    //******************************************************************************************************************
    // Nome do Método: EntTagValue                                                                                     *
    //                                                                                                                 *
    // Funcao: monta um array de duas strings a partir de duas strings (Tag e Value).                                  *
    //                                                                                                                 *
    // Entrada: string com a Tag e string com o Value                                                                  *
    //                                                                                                                 *
    // Saida: array[2] com a string Tag na posição 0 e a string Values na posição 1.                                   *
    //******************************************************************************************************************
    //
    private String[] EntTagValue(String tag, String value) {
        String[] tagvalue = new String[2];
        tagvalue[0] = tag;
        tagvalue[1] = value;

        return (tagvalue);
    }

    //******************************************************************************************************************
    // Nome do Método: MontaJson                                                                                       *
    //                                                                                                                 *
    // Funcao: monta uma mensagem em formato Json a partir das variáveis de supervisão e carrega em uma string         *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: String com a mensagem em formato Json                                                                    *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    private String MontaJson() {

        int numObj = 2;
        String[][] KeyValue = new String[numObj][2];

        KeyValue[0] = EntKeyValue("ID", "Ingrid");
        KeyValue[1] = EntKeyValue("NOME", "Ingrid Loyane F. Silva");

        return (JsonString(KeyValue, numObj));
    }

    private String JsonString(String[][] KeyValue, int numObj) {
        String MsgJson = "{\n";
        for (short i = 0; i < numObj; i++) {
            MsgJson = MsgJson + "\"" + KeyValue[i][0].toLowerCase() + "\"" + " : "
                    + "\"" + KeyValue[i][1] + "\"";

            if (i < (numObj - 1)) { MsgJson = MsgJson + ",";}
            MsgJson = MsgJson + "\n";
        }
        MsgJson = MsgJson + " }";

        System.out.println(MsgJson);

        return MsgJson;
    }

    private String[] EntKeyValue(String Key, String Value) {
        String[] keyvalue = new String[2];
        keyvalue[0] = Key;
        keyvalue[1] = Value;
        return (keyvalue);
    }

    private String msgArqNaoEncontrado(String nomeArquivo) {

        return ("<p></p><h3>File not found: " + nomeArquivo + "</h3>");
    }

    private void IniciaMsgXML(String tag0, int numGrupos) {
        MsgXMLArray[0][0][0][0] = tag0;
        MsgXMLArray[0][0][0][1] = IntToStr2(numGrupos);
    }

}
