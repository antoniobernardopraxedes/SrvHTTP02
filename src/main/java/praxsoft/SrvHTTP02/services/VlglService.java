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

    private static String[][] mesa = new String[18][3];

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

        String MsgXML = "null";
        String codigo = Auxiliar.LeParametroArquivo(dadosCliente, "Codigo:");
        String dataReserva = Auxiliar.LeParametroArquivo(dadosCliente, "DataReserva:");
        String userName = Auxiliar.LeParametroArquivo(dadosCliente, "NomeUsuario:");
        String numPessoas = Auxiliar.LeParametroArquivo(dadosCliente, "NumPessoas:");
        String horarioChegada = Auxiliar.LeParametroArquivo(dadosCliente,"HorarioChegada:");

        System.out.println("Código: " + codigo);
        System.out.println("Data da Reserva: " + dataReserva);
        System.out.println("Nome de Usuário: " + userName);
        System.out.println("Número de Pessoas: " + numPessoas);
        System.out.println("Horário de Chegada: " + horarioChegada);

        // Se o Administrador fez login - está fazendo a reserva
        if (VerificaAdmin(idUsuario)) {
            if (codigo.equals("carregaAdmin")) {
                MsgXML = MontaXMLadmin(idUsuario);
            }
            else {
                MsgXML = MontaXMLdataCliente(dataReserva, userName);
            }
        }

        System.out.println(MsgXML);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/xml"))
                .body(MsgXML);

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
                throw new IllegalStateException("Unexpected value: " + idAdmin);
        }

        // -------------------------------------------------------------------------------------------------------------
        // Monta a Mensagem XML - Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        boolean normal = true;
        String MsgXML = "";
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "01";

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 1: Variáveis de Informação do Administrador
        IdNv1 = 1;
        int i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "ADMIN";    // Carrega a Tag do Grupo 1
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("ID", idAdmin, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("NOME", nomeAdmin, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES1", res1Admin, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES2", res2Admin, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES3", res3Admin, normal);

        // Carrega o número de elementos do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = Auxiliar.StringXML(MsgXMLArray);
        return(MsgXML);
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
    public static String MontaXMLdataCliente(String dataReserva, String nomeUsuario) {

        String caminho = "";

        String nomeCliente = "null";
        String res1Cliente = "null";
        String res2Cliente = "null";
        String res3Cliente = "null";
        String res4Cliente = "null";

        if (!nomeUsuario.equals("null")) {

            caminho = "recursos/vlgl/clientes/";
            String nomeArquivo = nomeUsuario + ".clt";
            String registroCliente = Arquivo.LeArquivoTxt(caminho, nomeArquivo);

            if (registroCliente == null) {
                nomeUsuario = "null";
            }
            else {
                nomeCliente = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
                res1Cliente = Auxiliar.LeCampoArquivo(registroCliente, "Res1:");
                res2Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res2:");
                res3Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res3:");
                res4Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res4:");
            }
        }

        String OA00 = "null";    String NA00 = "null";    String HA00 = "null";
        String OA01 = "null";    String NA01 = "null";    String HA01 = "null";
        String OA02 = "null";    String NA02 = "null";    String HA02 = "null";
        String OA03 = "null";    String NA03 = "null";    String HA03 = "null";
        String OA04 = "null";    String NA04 = "null";    String HA04 = "null";
        String OA05 = "null";    String NA05 = "null";    String HA05 = "null";
        String OA06 = "null";    String NA06 = "null";    String HA06 = "null";
        String OA07 = "null";    String NA07 = "null";    String HA07 = "null";
        String OA08 = "null";    String NA08 = "null";    String HA08 = "null";

        String OB09 = "null";    String NB09 = "null";    String HB09 = "null";
        String OB10 = "null";    String NB10 = "null";    String HB10 = "null";
        String OB11 = "null";    String NB11 = "null";    String HB11 = "null";
        String OB12 = "null";    String NB12 = "null";    String HB12 = "null";
        String OB13 = "null";    String NB13 = "null";    String HB13 = "null";
        String OB14 = "null";    String NB14 = "null";    String HB14 = "null";
        String OB15 = "null";    String NB15 = "null";    String HB15 = "null";
        String OB16 = "null";    String NB16 = "null";    String HB16 = "null";

        if (!dataReserva.equals("null")) {

            caminho = "recursos/vlgl/reservas/";
            String nomeArquivo = dataReserva + ".res";
            String registroMesas = Arquivo.LeArquivoTxt(caminho, nomeArquivo);
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

        // // Monta a Mensagem XML - Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis
        String MsgXMLArray[][][][] = new String[1][10][60][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        boolean normal = true;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "02";

        // Grupo 1: Variáveis de Informação do Cliente
        IdNv1 = 1;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", nomeUsuario, normal);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", nomeCliente, normal);
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("RES1", res1Cliente, normal);
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("RES2", res2Cliente, normal);
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("RES3", res3Cliente, normal);
        MsgXMLArray[IdNv0][IdNv1][6] = Auxiliar.EntTagValue("RES4", res4Cliente, normal);

        // Carrega o número de elementos do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(6);

        // Grupo 1: Variáveis de Informação da Disponibilidade das Mesas
        IdNv1 = 2;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "MESAS";
        int i = 0;
        int numTags1 = 9;
        for (int k = 0; k < numTags1; k++) {
            String token = "OA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][0], normal);
            token = "NA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][1], normal);
            token = "HA" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][2], normal);
        }

        int numTags2 = 8;
        for (int k = numTags1; k < (numTags1 + numTags2); k++) {
            String token = "OB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][0], normal);
            token = "NB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][1], normal);
            token = "HB" + Auxiliar.IntToStr2(k);
            i = i + 1;
            MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue(token, mesa[k][2], normal);
        }

        // Carrega o número de elementos do Grupo 2 e retorna a Mensagem XML completa em formato de String
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);
        return(Auxiliar.StringXML(MsgXMLArray));
    }

}
