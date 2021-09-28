package praxsoft.SrvHTTP02.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Cliente;

@Service
public class VlglService {

    //private static String[] cliente = new String[10];
    private static String[][] mesa = new String[20][3];

    // Array usada para montagem da mensagem XML
    private static String MsgXMLArray[][][][] = new String[1][4][60][2];

    // Variáveis de estado do Cliente
    private static String clienteCadastrado;
    private static String nomeUsuarioCliente;
    private static String nomeCliente;
    private static String celularCliente;
    private static String observacao1Cliente;
    private static String observacao2Cliente;
    private static String idosoCliente;
    private static String locomocaoCliente;
    private static String exigenteCliente;
    private static String generoCliente;
    private static String adminRespCliente;

    // Variáveis de estado da Reserva
    private static String confirmaReserva;
    private static String dataReserva;
    private static String numeroPessoas;
    private static String horarioChegada;
    private static String mesaSelecionada;

    public static void setNomeUsuarioCliente(String nomeUsuarioCliente) {
        VlglService.nomeUsuarioCliente = nomeUsuarioCliente;
    }

    public static void setDataReserva(String dataReserva) {
        VlglService.dataReserva = dataReserva;
    }

    private static final String admin1 = "Ingrid";
    private static final String admin2 = "Guto";
    private static final String admin3 = "Bernardo";

    public static void IniciaVariaveis() {
        clienteCadastrado = "null";
        nomeUsuarioCliente = "null";
        celularCliente = "null";
        observacao1Cliente = "null";
        observacao2Cliente = "null";
        idosoCliente = "null";
        locomocaoCliente = "null";
        exigenteCliente = "null";
        generoCliente = "null";
        adminRespCliente = "null";

        confirmaReserva = "null";
        dataReserva = "null";
        numeroPessoas = "null";
        horarioChegada = "null";
        mesaSelecionada = "null";
    }

    //******************************************************************************************************************
    // Nome do Método: ConfirmaReserva                                                                                 *
    //	                                                                                                               *
    // Data: 28/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: recebe os dados da mensagem POST solicitando a confirmação da reserva, atualiza as variáveis e          *
    //         grava o registro.                                                                                       *
    //                                                                                                                 *
    // Entrada: string com os dados da mensagem recebida no método POST                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static void ConfirmaReserva(String dadosMensagem ) {

        dataReserva = Auxiliar.LeParametroArquivo(dadosMensagem, "DataReserva:");
        Auxiliar.Terminal("Data da Reserva: " + dataReserva, false);
        nomeUsuarioCliente = Auxiliar.LeParametroArquivo(dadosMensagem, "NomeUsuario:");
        Auxiliar.Terminal("Nome de Usuário: " + nomeUsuarioCliente, false);
        numeroPessoas = Auxiliar.LeParametroArquivo(dadosMensagem, "NumPessoas:");
        Auxiliar.Terminal("Número de Pessoas: " + numeroPessoas, false);
        horarioChegada = Auxiliar.LeParametroArquivo(dadosMensagem,"HorarioCheg:");
        Auxiliar.Terminal("Horário de Chegada: " + horarioChegada, false);
        mesaSelecionada = Auxiliar.LeParametroArquivo(dadosMensagem,"MesaSelec: ");
        Auxiliar.Terminal("Mesa Selecionada: " + mesaSelecionada, false);
        AtualizaArquivoReserva();

    }

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
    public static void GeraCadastroCliente(Cliente cliente) {

        //Auxiliar.Terminal("Nome de usuário do cliente: " + cliente.getNomeUsuario(), false);
        //Auxiliar.Terminal("Nome do cliente: " + cliente.getNome(), false);
        //Auxiliar.Terminal("Celular do cliente: " + cliente.getCelular(), false);
        //Auxiliar.Terminal("Observação 1: " + cliente.getObs1(), false);
        //Auxiliar.Terminal("Observação 2: " + cliente.getObs2(), false);
        //Auxiliar.Terminal("O cliente é idoso? " + cliente.getIdoso(), false);
        //Auxiliar.Terminal("O cliente tem problema de locomoção? " + cliente.getLocomocao(), false);
        //Auxiliar.Terminal("O cliente é exigente? " + cliente.getExigente(), false);
        //Auxiliar.Terminal("Gênero: " + cliente.getGenero(), false);
        //Auxiliar.Terminal("Admin responsável pelo cadastro: " + cliente.getAdminResp(), false);

        cliente.MostraCamposTerminal();

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

        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = cliente.getNomeUsuario() + ".clt";

        // Se o arquivo existe, renomeia para a.nomearquivo.clt e grava o novo arquivo de cadastro de cliente
        if (Arquivo.Existe(caminho, "a." + nomeArquivo)) {
            Arquivo.Apaga(caminho, "a." + nomeArquivo);
            if (Arquivo.Renomeia(caminho, nomeArquivo, "a." + nomeArquivo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " renomeado", false);
                if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                    Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
                    clienteCadastrado = "sim";
                } else {
                    clienteCadastrado = "nao";
                }

            }
        }
        else { // Se o arquivo não existe, grava o novo arquivo de cadastro de cliente
            if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
                clienteCadastrado = "sim";
            } else {
                clienteCadastrado = "nao";
            }
        }
    }

    //******************************************************************************************************************
    // Nome do Método: AtualizaReserva                                                                                 *
    //	                                                                                                               *
    // Data: 24/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: lê um arquivo de data com as informações das mesas, atualiza a reserva confirmada e grava novamente     *
    //         o arquivo.                                                                                              *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA, string com o nome de usuário do cliente,           *
    //          string com o número de pessoas e string com a hora de chegada                                          *
    //                                                                                                                 *
    // Saida:                                                                                                          *
    //******************************************************************************************************************
    //
    public static void AtualizaArquivoReserva() {

        String caminho = "recursos/vlgl/reservas/";
        String nomeArquivo = dataReserva + ".res";

        String dadosArquivo = Arquivo.LeTexto(caminho, nomeArquivo);
        if (dadosArquivo != null) {
            String[][] dadosMesas = new String[17][3];
            for (int i = 0; i < 9; i++) {
                String token = "OA" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][0] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "NA" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][1] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "HA" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][2] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
            }
            for (int i = 9; i < 17; i++) {
                String token = "OB" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][0] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "NB" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][1] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "HB" + Auxiliar.IntToStr2(i) + ":";
                dadosMesas[i][2] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
            }

            int indice = Auxiliar.StringToInt(mesaSelecionada);
            System.out.println("Índice mesa = " + indice);
            dadosMesas[indice][0] = nomeUsuarioCliente;
            dadosMesas[indice][1] = numeroPessoas;
            dadosMesas[indice][2] = horarioChegada;

            String dadosArqNovo = "{\n";
            for (int i = 0; i < 9; i++) {
                String Indice = Auxiliar.IntToStr2(i);
                dadosArqNovo = dadosArqNovo + "  OA" + Indice + ": " + dadosMesas[i][0] + "\n";
                dadosArqNovo = dadosArqNovo + "  NA" + Indice + ": " + dadosMesas[i][1] + "\n";
                dadosArqNovo = dadosArqNovo + "  HA" + Indice + ": " + dadosMesas[i][2] + "\n";
            }
            for (int i = 9; i < 17; i++) {
                String Indice = Auxiliar.IntToStr2(i);
                dadosArqNovo = dadosArqNovo + "  OB" + Indice + ": " + dadosMesas[i][0] + "\n";
                dadosArqNovo = dadosArqNovo + "  NB" + Indice + ": " + dadosMesas[i][1] + "\n";
                dadosArqNovo = dadosArqNovo + "  HB" + Indice + ": " + dadosMesas[i][2] + "\n";
            }
            dadosArqNovo = dadosArqNovo + "}";


            Auxiliar.Terminal("Arquivo de reservas " + nomeArquivo + " lido e modificado", false);
            if (Arquivo.Existe(caminho, "a." + nomeArquivo)) {
                Arquivo.Apaga(caminho, "a." + nomeArquivo);
            }
            if (Arquivo.Renomeia(caminho, nomeArquivo, "a." + nomeArquivo)) {
                Auxiliar.Terminal("Arquivo de reservas " + nomeArquivo + " renomeado", false);
                if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                    Auxiliar.Terminal("Arquivo de reservas " + nomeArquivo + " modificado e salvo", false);
                    confirmaReserva = "sim";
                } else {
                    confirmaReserva = "nao";
                }
            }

            System.out.println("");
            System.out.println(dadosArqNovo);
            System.out.println("");

        }
        else {
            Auxiliar.Terminal("Arquivo de reservas do dia " + nomeArquivo + " não encontrado", false);
        }
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
    // Saida:                                                                                                          *
    //******************************************************************************************************************
    //
    public static void EscreveArquivoReservaNovo(String dataRes) {

        String caminho = "recursos/vlgl/reservas/";
        String nomeArquivo = dataRes + ".res";

        String dadosArqNovo = "{\n";
        for (int i = 0; i < 9; i++) {
            String Indice = Auxiliar.IntToStr2(i);
            dadosArqNovo = dadosArqNovo + "  OA" + Indice + ": livre\n";
            dadosArqNovo = dadosArqNovo + "  NA" + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  HA" + Indice + ": null\n";
        }
        for (int i = 9; i < 17; i++) {
            String Indice = Auxiliar.IntToStr2(i);
            dadosArqNovo = dadosArqNovo + "  OB" + Indice + ": livre\n";
            dadosArqNovo = dadosArqNovo + "  NB" + Indice + ": null\n";
            dadosArqNovo = dadosArqNovo + "  HB" + Indice + ": null\n";
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
    public static void CarregaEstadoArray(int IdNv0, int IdNv1) {

        MsgXMLArray[IdNv0][IdNv1][0][0] = "ESTADO";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("CONFIRMA", confirmaReserva);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("DATA", dataReserva);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("CADASTRO", clienteCadastrado);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("NUMPES", numeroPessoas);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("HORARIO", horarioChegada);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(5);
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaDataArray                                                                                *
    //	                                                                                                               *
    // Data: 25/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: carrega as informações das reservas das mesas na data no array usado para montar a mensagem XML.        *
    //                                                                                                                 *
    // Entrada: string com a data da reserva, int com o índice do local e int com o índice do grupo                    *
    //                                                                                                                 *
    // Saida: boolean (retorna false se o registro do cliente não for encontrado)                                      *
    //******************************************************************************************************************
    //
    public static void CarregaDataArray(int IdNv0, int IdNv1) {

        if (dataReserva == null) { dataReserva = "null"; }

        String caminho = "recursos/vlgl/reservas/";
        String nomeArquivo = dataReserva + ".res";
        if (!Arquivo.Existe(caminho, nomeArquivo)) {
            EscreveArquivoReservaNovo(dataReserva);
        }

        String registroMesas = Arquivo.LeTexto(caminho, nomeArquivo);

        if (registroMesas != null) {
            for (int i = 0; i < 9; i++) {
                String token = "OA" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][0] = Auxiliar.LeParametroArquivo(registroMesas, token);
                token = "NA" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][1] = Auxiliar.LeParametroArquivo(registroMesas, token);
                token = "HA" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][2] = Auxiliar.LeParametroArquivo(registroMesas, token);
            }
            for (int i = 9; i < 17; i++) {
                String token = "OB" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][0] = Auxiliar.LeParametroArquivo(registroMesas, token);
                token = "NB" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][1] = Auxiliar.LeParametroArquivo(registroMesas, token);
                token = "HB" + Auxiliar.IntToStr2(i) + ":";
                mesa[i][2] = Auxiliar.LeParametroArquivo(registroMesas, token);
            }
        }

        MsgXMLArray[IdNv0][IdNv1][0][0] = "MESAS";
        int i = 0;
        int numTags1 = 9;
        for (int k = 0; k < numTags1; k++) {
            String token = "OA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][0]);
            token = "NA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][1]);
            token = "HA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][2]);
        }

        int numTags2 = 8;
        for (int k = numTags1; k < (numTags1 + numTags2); k++) {
            String token = "OB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][0]);
            token = "NB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][1]);
            token = "HB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][2]);
        }
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i); // Carrega o número de elementos do Grupo
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
    public static String MontaXMLadmin(String idAdmin) {

        Auxiliar.Terminal("idAdmin: " + idAdmin, false);

        String nomeAdmin;
        String res1Admin;
        String res2Admin;
        String res3Admin;

        switch (idAdmin) {
            case admin1 :
                nomeAdmin = "Ingrid Loyane F. Silva";
                res1Admin = "Gerente";
                res2Admin = "Reserva";
                res3Admin = "Reserva";
                break;

            case admin2 :
                nomeAdmin = "Gouthier Dias";
                res1Admin = "Gerente";
                res2Admin = "Reserva";
                res3Admin = "Reserva";
                break;

            case admin3 :
                nomeAdmin = "Antonio Bernardo Praxedes";
                res1Admin = "Técnico";
                res2Admin = "Reserva";
                res3Admin = "Reserva";
                break;

            default:
                nomeAdmin = "null";
                res1Admin = "null";
                res2Admin = "null";
                res3Admin = "null";
        }

        // Monta a Mensagem XML
        int IdNv0 = 0;
        MsgXMLArray[0][0][0][0] = "LOCAL001";
        MsgXMLArray[0][0][0][1] = "01";

        int IdNv1 = 1;  // Grupo 1: Variáveis de Informação do Administrador
        MsgXMLArray[IdNv0][IdNv1][0][0] = "ADMIN";    // Carrega a Tag do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", idAdmin);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", nomeAdmin);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("RES1", res1Admin);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("RES2", res2Admin);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("RES3", res3Admin);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(5); // Carrega o número de elementos do Grupo 1

        return(Auxiliar.StringXML(MsgXMLArray));
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
    public static String MontaXMLCliente(String nomeUsuario) {

        MsgXMLArray[0][0][0][0] = "LOCAL001";    // Identificador do Local
        MsgXMLArray[0][0][0][1] = "02";          // Número de Grupos (ESTADO e CLIENTE)

        CarregaClienteArray(nomeUsuario,0, 2);
        CarregaEstadoArray(0, 1);

        return(Auxiliar.StringXML(MsgXMLArray));
    }

    //******************************************************************************************************************
    // Nome do Método: CarregaClienteArray                                                                             *
    //	                                                                                                               *
    // Data: 25/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: carrega as informações do cliente no array usado para montar a mensagem XML.                            *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente, int com o índice do local e int com o índice do grupo         *
    //                                                                                                                 *
    // Saida: boolean (retorna false se o registro do cliente não for encontrado)                                      *
    //******************************************************************************************************************
    //
    public static void CarregaClienteArray(String nomeUsuario, int IdNv0, int IdNv1) {

        int numCampos = 10;
        String[] cliente = new String[numCampos];

        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = nomeUsuario + ".clt";
        String registroCliente = Arquivo.LeTexto(caminho, nomeArquivo);

        if (registroCliente == null) {
            clienteCadastrado = "nao";
            for (int i = 0; i < numCampos; i++) {
                cliente[i] = "null";
            }
        }
        else {
            clienteCadastrado = "sim";
            cliente[0] = Auxiliar.LeCampoArquivo(registroCliente, "NomeUsuario:");
            cliente[1] = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
            cliente[2] = Auxiliar.LeCampoArquivo(registroCliente, "Celular:");
            cliente[3] = Auxiliar.LeCampoArquivo(registroCliente, "Obs1:");
            cliente[4] = Auxiliar.LeCampoArquivo(registroCliente, "Obs2:");
            cliente[5] = Auxiliar.LeCampoArquivo(registroCliente, "Idoso:");
            cliente[6] = Auxiliar.LeCampoArquivo(registroCliente, "Locomocao:");
            cliente[7] = Auxiliar.LeCampoArquivo(registroCliente, "Exigente:");
            cliente[8] = Auxiliar.LeCampoArquivo(registroCliente, "Genero:");
            cliente[9] = Auxiliar.LeCampoArquivo(registroCliente, "AdminResp:");
        }

        // Grupo IdNv1: Variáveis de Informação do Cliente
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", cliente[0]);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", cliente[1]);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("CELULAR", cliente[2]);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("OBS1", cliente[3]);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("OBS2", cliente[4]);
        MsgXMLArray[IdNv0][IdNv1][6] = Auxiliar.EntTagValue("IDOSO", cliente[5]);
        MsgXMLArray[IdNv0][IdNv1][7] = Auxiliar.EntTagValue("LOCOMOCAO", cliente[6]);
        MsgXMLArray[IdNv0][IdNv1][8] = Auxiliar.EntTagValue("EXIGENTE", cliente[7]);
        MsgXMLArray[IdNv0][IdNv1][9] = Auxiliar.EntTagValue("GENERO", cliente[8]);
        MsgXMLArray[IdNv0][IdNv1][10] = Auxiliar.EntTagValue("ADMINRSP", cliente[9]);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(10); // Número de elementos do Grupo

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
    public static String MontaXMLData() {

        MsgXMLArray[0][0][0][0] = "LOCAL001";    // Identificador do Local
        MsgXMLArray[0][0][0][1] = "02";          // Número de Grupos (ESTADO e CLIENTE)

        CarregaDataArray(0, 2);
        CarregaEstadoArray(0, 1);

        return(Auxiliar.StringXML(MsgXMLArray));
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLdataCliente()                                                                           *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes ao cliente e à disponibilidade de mesas na data      *
    //         especificada. As informações do cliente são lidas de um arquivo texto que tem o nome igual ao nome      *
    //         de usuário. As informações da disponibilidade de mesas são lidas de um arquivo que tem o nome igual     *
    //         à data.                                                                                                 *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA) e string com o nome de usuário do cliente          *
    //          (idCliente)                                                                                            *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static String MontaXMLdataCliente() {

        MsgXMLArray[0][0][0][0] = "LOCAL001";    // Identificador do Local
        MsgXMLArray[0][0][0][1] = "03";          // Número de Grupos (ESTADO, CLIENTE e DATA)

        CarregaClienteArray(nomeUsuarioCliente, 0, 2);
        CarregaDataArray(0, 3);                  // As informações de data são carregadas no grupo 3
        CarregaEstadoArray(0, 1);                // As informações de estado são carregadas no grupo 1

        return(Auxiliar.StringXML(MsgXMLArray));
    }

    //******************************************************************************************************************
    // Nome do Método: VerificaAdmin                                                                                   *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: verifica se o usuário que fez o login é administrador.                                                  *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário que fez login.                                                            *
    //                                                                                                                 *
    // Saida: boolean se o usuário é administrador = true / se o usuário não é administrador = false                   *
    //******************************************************************************************************************
    //
    public static boolean VerificaAdmin(String idUsuario) {
        boolean adminOK;

        switch (idUsuario) {
            case admin1:
                adminOK = true;
                break;

            case admin2:
                adminOK = true;
                break;

            case admin3:
                adminOK = true;
                break;

            default:
                adminOK = false;
        }
        return adminOK;
    }

}
