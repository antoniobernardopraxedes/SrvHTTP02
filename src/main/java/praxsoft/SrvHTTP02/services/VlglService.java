package praxsoft.SrvHTTP02.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VlglService {

    private static final String admin1 = "Ingrid";
    private static final String admin2 = "Guto";
    private static final String admin3 = "Bernardo";

    // Arrays que guardam as informações do cliente e das mesas
    private static String[] cliente = new String[6];
    private static String[][] mesa = new String[18][3];

    // Array usada para montagem da mensagem XML
    private static String MsgXMLArray[][][][] = new String[1][4][60][2];

    // Variáveis de estado
    private static String confirmaReserva;
    private static String dataReserva;
    private static String nomeUsuarioCliente;
    private static String clienteCadastrado;
    private static String numeroPessoas;
    private static String horarioChegada;
    private static String mesaSelecionada;

    public static void IniciaVariaveis() {
        confirmaReserva = "null";
        dataReserva = "null";
        nomeUsuarioCliente = "null";
        clienteCadastrado = "null";
        numeroPessoas = "null";
        horarioChegada = "null";
        mesaSelecionada = "null";
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

    //******************************************************************************************************************
    // Nome do Método: ExecutaComandos                                                                                 *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: recebe a mensagem enviada pelo cliente no modo administrador e executa os comandos solicitados.         *
    //                                                                                                                 *
    // Entrada: string com a mensagem recebida do cliente e o nome do usuário que fez login.                           *
    //          especiais.                                                                                             *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static ResponseEntity<?> ExecutaComandos(String dadosCliente, String idUsuario) {
        String MsgXML = "";
        String codigo = Auxiliar.LeParametroArquivo(dadosCliente, "Codigo:");
        dataReserva = Auxiliar.LeParametroArquivo(dadosCliente, "DataReserva:");
        nomeUsuarioCliente = Auxiliar.LeParametroArquivo(dadosCliente, "NomeUsuario:");
        numeroPessoas = Auxiliar.LeParametroArquivo(dadosCliente, "NumPessoas:");
        horarioChegada = Auxiliar.LeParametroArquivo(dadosCliente,"HorarioCheg:");
        mesaSelecionada = Auxiliar.LeParametroArquivo(dadosCliente,"MesaSelec: ");

        Auxiliar.Terminal("Recebida Requisição do Cliente - Código: " + codigo, false);
        Auxiliar.Terminal("Data da Reserva: " + dataReserva, false);
        Auxiliar.Terminal("Nome de Usuário: " + nomeUsuarioCliente, false);
        Auxiliar.Terminal("Número de Pessoas: " + numeroPessoas, false);
        Auxiliar.Terminal("Horário de Chegada: " + horarioChegada, false);
        Auxiliar.Terminal("Mesa Selecionada: " + mesaSelecionada, false);

        // Se o Administrador fez login - está fazendo a reserva
        if (VerificaAdmin(idUsuario)) {
            confirmaReserva = "semInf";

            switch (codigo) {
                case "carregaAdmin" :
                    MsgXML = MontaXMLadmin(idUsuario);
                    break;

                case "Data" :
                    MsgXML = MontaXMLData();
                    break;

                case "Cliente" :
                    MsgXML = MontaXMLCliente();
                    break;

                case "DataCliente" :
                    MsgXML = MontaXMLdataCliente();
                    break;

                case "Confirma" :
                    AtualizaArqData();
                    MsgXML = MontaXMLdataCliente();
                    break;
            }
        }

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);

    }

    //******************************************************************************************************************
    // Nome do Método: AtualizaArqData                                                                                 *
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
    public static void AtualizaArqData() {

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
    public static void CarregaClienteArray(int IdNv0, int IdNv1) {
        if (nomeUsuarioCliente == null) { nomeUsuarioCliente = "null"; }
        int numCampos = 6;

        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = nomeUsuarioCliente + ".clt";
        String registroCliente = Arquivo.LeTexto(caminho, nomeArquivo);

        if (registroCliente == null) {
            clienteCadastrado = "nao";
            for (int i = 0; i < numCampos; i++) {
                cliente[i] = "null";
            }
        }
        else {
            clienteCadastrado = "sim";
            cliente[0] = Auxiliar.LeCampoArquivo(registroCliente, "Id:");
            cliente[1] = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
            cliente[2] = Auxiliar.LeCampoArquivo(registroCliente, "Info1:");
            cliente[3] = Auxiliar.LeParametroArquivo(registroCliente, "Info2:");
            cliente[4] = Auxiliar.LeParametroArquivo(registroCliente, "Info3:");
            cliente[5] = Auxiliar.LeParametroArquivo(registroCliente, "Info4:");
        }

        // Grupo IdNv1: Variáveis de Informação do Cliente
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", cliente[0]);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", cliente[1]);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("INFO1", cliente[2]);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("INFO2", cliente[3]);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("INFO3", cliente[4]);
        MsgXMLArray[IdNv0][IdNv1][6] = Auxiliar.EntTagValue("INFO4", cliente[5]);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(6); // Número de elementos do Grupo

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

        String nomeAdmin = "null";
        String res1Admin = "null";
        String res2Admin = "null";
        String res3Admin = "null";

        switch (idAdmin) {
            case admin1:
                nomeAdmin = "Ingrid Loyane F. Silva";
                res1Admin = "Gerente";
                res2Admin = "Reserva";
                res3Admin = "Reserva";
                break;

            case admin2:
                nomeAdmin = "Gouthier Dias";
                res1Admin = "Gerente";
                res2Admin = "Reserva";
                res3Admin = "Reserva";
                break;

            case admin3:
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
    public static String MontaXMLCliente() {

        MsgXMLArray[0][0][0][0] = "LOCAL001";    // Identificador do Local
        MsgXMLArray[0][0][0][1] = "02";          // Número de Grupos (ESTADO e CLIENTE)

        CarregaClienteArray(0, 2);
        CarregaEstadoArray(0, 1);

        return(Auxiliar.StringXML(MsgXMLArray));
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

        CarregaClienteArray(0, 2);               // As informações do cliente são carregadas no grupo 2
        CarregaDataArray(0, 3);                  // As informações de data são carregadas no grupo 3
        CarregaEstadoArray(0, 1);                // As informações de estado são carregadas no grupo 1

        return(Auxiliar.StringXML(MsgXMLArray));
    }


    /*
    public static String MontaXMLdataCliente() {

        String caminho = "";
        if (!nomeUsuario.equals("null")) {

            caminho = "recursos/vlgl/clientes/";
            String nomeArquivo = nomeUsuario + ".clt";
            String registroCliente = Arquivo.LeTexto(caminho, nomeArquivo);

            if (registroCliente == null) {
                nomeUsuario = "null";
            }
            else {
                cliente[0] = Auxiliar.LeCampoArquivo(registroCliente, "Id:");
                cliente[1] = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
                cliente[2] = Auxiliar.LeCampoArquivo(registroCliente, "Info1:");
                cliente[3] = Auxiliar.LeParametroArquivo(registroCliente, "Info2:");
                cliente[4] = Auxiliar.LeParametroArquivo(registroCliente, "Info3:");
                cliente[5] = Auxiliar.LeParametroArquivo(registroCliente, "Info4:");
            }
        }

        if (!dataReserva.equals("null")) {

            caminho = "recursos/vlgl/reservas/";
            String nomeArquivo = dataReserva + ".res";
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
        }

        // Monta a Mensagem XML
        int IdNv0 = 0;
        int IdNv1 = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";    // |Identificador do Local
        MsgXMLArray[IdNv0][IdNv1][0][1] = "03";          // Número de Grupos

        IdNv1 = 1;  // Grupo 1: Variáveis de Informação de Estado
        MsgXMLArray[IdNv0][IdNv1][0][0] = "ESTADO";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("CONF", confirmaReserva);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("CLTCD", clienteCadastrado);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("INFO2", "Reservado");
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("INFO3", "Reservado");
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(4); // Número de elementos do Grupo 1

        IdNv1 = 2;  // Grupo 2: Variáveis de Informação do Cliente
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", cliente[0]);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", cliente[1]);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("INFO1", cliente[2]);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("INFO2", cliente[3]);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("INFO3", cliente[4]);
        MsgXMLArray[IdNv0][IdNv1][6] = Auxiliar.EntTagValue("INFO4", cliente[5]);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(6); // Número de elementos do Grupo 2

        IdNv1 = 3; // Grupo 3: Variáveis de Informação das Mesas
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
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i); // Carrega o número de elementos do Grupo 3

        return(Auxiliar.StringXML(MsgXMLArray));
    }
*/
}
