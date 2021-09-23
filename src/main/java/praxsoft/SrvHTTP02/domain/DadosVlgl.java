package praxsoft.SrvHTTP02.domain;

import praxsoft.SrvHTTP02.services.Arquivo;
import praxsoft.SrvHTTP02.services.Auxiliar;

public class DadosVlgl {

    private static final String admin1 = "Ingrid";
    private static final String admin2 = "Guto";
    private static final String admin3 = "Bernardo";

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
    // Nome do Método: MontaXMLcliente()                                                                               *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes ao cliente que está fazendo a reserva.               *
    //         As informações do cliente são lidas de um arquivo texto que tem o nome igual ao id do cliente.          *
    //                                                                                                                 *
    // Entrada: string com o nome de usuário do cliente (idCliente). O idCliente não deve ter espaços nem caracteres   *
    //          especiais.                                                                                             *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static String MontaXMLcliente(String idCliente) {

        String nomeCliente;
        String res1Cliente;
        String res2Cliente;
        String res3Cliente;
        String res4Cliente;
        String caminho = "recursos/vlgl/clientes/";
        String registroCliente = Arquivo.LeArquivoTxt(caminho, idCliente);

        if (registroCliente == null) {
            nomeCliente = "null";
            res1Cliente = "null";
            res2Cliente = "null";
            res3Cliente = "null";
            res4Cliente = "null";
        }
        else {
            nomeCliente = Auxiliar.LeParametroArquivo(registroCliente, "Nome:");
            res1Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res1:");
            res2Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res2:");
            res3Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res3:");
            res4Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res4:");
        }

        // -------------------------------------------------------------------------------------------------------------
        // Monta a Mensagem XML - Carrega na StringXML Array os Tags de Níveis 0, 1,e 2 e as variáveis
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        boolean normal = true;
        String MsgXML = "";
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "01";

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 1: Variáveis de Informação do Cliente
        IdNv1 = 1;
        int i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";    // Carrega a Tag do Grupo 1
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("ID", idCliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("NOME", nomeCliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES1", res1Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES2", res2Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES3", res3Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES4", res4Cliente, normal);

        // Carrega o número de elementos do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = Auxiliar.StringXML(MsgXMLArray);
        return(MsgXML);
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLclienteData()                                                                           *
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
    public static String MontaXMLclienteData(String codigo, String dataReserva, String userName) {

        String caminho = "recursos/vlgl/";
        String nomeCliente = "null";
        String res1Cliente = "null";
        String res2Cliente = "null";
        String res3Cliente = "null";
        String res4Cliente = "null";

        if (codigo.equals("Cliente") || codigo.equals("DataCliente")) {
            String nomeArquivo = userName + ".txt";
            String registroCliente = Arquivo.LeArquivoTxt(caminho + "clientes/", nomeArquivo);

            if (registroCliente == null) {
                userName = "null";
                nomeCliente = "naocadastrado";
            }
            else {
                nomeCliente = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
                res1Cliente = Auxiliar.LeCampoArquivo(registroCliente, "Res1:");
                res2Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res2:");
                res3Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res3:");
                res4Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res4:");
            }
        }

        String OA00 = "null";    String horaA00 = "null";    String numpA00 = "null";
        String OA01 = "null";    String horaA01 = "null";    String numpA01 = "null";
        String OA02 = "null";    String horaA02 = "null";    String numpA02 = "null";
        String OA03 = "null";    String horaA03 = "null";    String numpA03 = "null";
        String OA04 = "null";    String horaA04 = "null";    String numpA04 = "null";
        String OA05 = "null";    String horaA05 = "null";    String numpA05 = "null";
        String OA06 = "null";    String horaA06 = "null";    String numpA06 = "null";
        String OA07 = "null";    String horaA07 = "null";    String numpA07 = "null";
        String OA08 = "null";    String horaA08 = "null";    String numpA08 = "null";

        String OB09 = "null";    String horaB09 = "null";
        String OB10 = "null";    String horaB10 = "null";
        String OB11 = "null";    String horaB11 = "null";
        String OB12 = "null";    String horaB12 = "null";
        String OB13 = "null";    String horaB13 = "null";
        String OB14 = "null";    String horaB14 = "null";
        String OB15 = "null";    String horaB15 = "null";
        String OB16 = "null";    String horaB16 = "null";

        if (codigo.equals("Data") || codigo.equals("DataCliente")) {

            String registroMesas = Arquivo.LeArquivoTxt(caminho + "reservas/", "25-09-2021.txt");
            if (registroMesas != null) {
                OA00 = Auxiliar.LeParametroArquivo(registroMesas, "OA00:");
                OA01 = Auxiliar.LeParametroArquivo(registroMesas, "OA01:");
                OA02 = Auxiliar.LeParametroArquivo(registroMesas, "OA02:");
                OA03 = Auxiliar.LeParametroArquivo(registroMesas, "OA03:");
                OA04 = Auxiliar.LeParametroArquivo(registroMesas, "OA04:");
                OA05 = Auxiliar.LeParametroArquivo(registroMesas, "OA05:");
                OA06 = Auxiliar.LeParametroArquivo(registroMesas, "OA06:");
                OA07 = Auxiliar.LeParametroArquivo(registroMesas, "OA07:");
                OA08 = Auxiliar.LeParametroArquivo(registroMesas, "OA08:");

                OB09 = Auxiliar.LeParametroArquivo(registroMesas, "OB09:");
                OB10 = Auxiliar.LeParametroArquivo(registroMesas, "OB10:");
                OB11 = Auxiliar.LeParametroArquivo(registroMesas, "OB11:");
                OB12 = Auxiliar.LeParametroArquivo(registroMesas, "OB12:");
                OB13 = Auxiliar.LeParametroArquivo(registroMesas, "OB13:");
                OB14 = Auxiliar.LeParametroArquivo(registroMesas, "OB14:");
                OB15 = Auxiliar.LeParametroArquivo(registroMesas, "OB15:");
                OB16 = Auxiliar.LeParametroArquivo(registroMesas, "OB16:");
            }
        }
        // -------------------------------------------------------------------------------------------------------------
        // // Monta a Mensagem XML - Carrega na StringXML Array os Tags de Níveis 0,1,e 2 e as variáveis
        String MsgXMLArray[][][][] = new String[1][10][30][2];
        int IdNv0 = 0;
        int IdNv1 = 0;
        boolean normal = true;
        String MsgXML = "";
        MsgXMLArray[IdNv0][IdNv1][0][0] = "LOCAL001";
        MsgXMLArray[IdNv0][IdNv1][0][1] = "02";

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 1: Variáveis de Informação do Cliente
        IdNv1 = 1;
        int i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "CLIENTE";    // Carrega a Tag do Grupo 1
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("ID", userName, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("NOME", nomeCliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES1", res1Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES2", res2Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES3", res3Cliente, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("RES4", res4Cliente, normal);

        // Carrega o número de elementos do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 1: Variáveis de Informação da Disponibilidade das Mesas
        IdNv1 = 2;
        i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "MESAS";
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA00", OA00, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA01", OA01, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA02", OA02, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA03", OA03, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA04", OA04, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA05", OA05, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA06", OA06, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA07", OA07, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OA08", OA08, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB09", OB09, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB10", OB10, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB11", OB11, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB12", OB12, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB13", OB13, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB14", OB14, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB15", OB15, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("OB16", OB16, normal);

        // Carrega o número de elementos do Grupo 2
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = Auxiliar.StringXML(MsgXMLArray);
        return(MsgXML);
    }

}
