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
    // Nome do Método: MontaXMLclienteMesas()                                                                          *
    //	                                                                                                               *
    // Data: 22/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes ao cliente e à disponibilidade de mesas na data      *
    //         especificada. As informações são lidas de um arquivo texto que tem o nome igual à data.                 *
    //                                                                                                                 *
    // Entrada: string com a data da reserva no formato DD-MM-AAAA) e string com o nome de usuário do cliente          *
    //          (idCliente)                                                                                            *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public static String MontaXMLclienteMesas(String dataReserva, String idCliente) {

        String nomeCliente;
        String res1Cliente;
        String res2Cliente;
        String res3Cliente;
        String res4Cliente;
        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = idCliente + ".txt";
        String registroCliente = Arquivo.LeArquivoTxt(caminho, nomeArquivo);

        if (registroCliente == null) {
            nomeCliente = "null";
            res1Cliente = "null";
            res2Cliente = "null";
            res3Cliente = "null";
            res4Cliente = "null";
        }
        else {
            nomeCliente = Auxiliar.LeCampoArquivo(registroCliente, "Nome:");
            res1Cliente = Auxiliar.LeCampoArquivo(registroCliente, "Res1:");
            res2Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res2:");
            res3Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res3:");
            res4Cliente = Auxiliar.LeParametroArquivo(registroCliente, "Res4:");
        }

        String mesaA00 = "null";
        String mesaA01 = "null";
        String mesaA02 = "null";
        String mesaA03 = "null";
        String mesaA04 = "null";
        String mesaA05 = "null";
        String mesaA06 = "null";
        String mesaA07 = "null";
        String mesaA08 = "null";

        String mesaB09 = "null";
        String mesaB10 = "null";
        String mesaB11 = "null";
        String mesaB12 = "null";
        String mesaB13 = "null";
        String mesaB14 = "null";
        String mesaB15 = "null";
        String mesaB16 = "null";

        caminho = "recursos/vlgl/reservas/";
        String registroMesas = Arquivo.LeArquivoTxt(caminho, "25-09-2021.txt");
        if (registroMesas != null) {
            mesaA00 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA00:");
            mesaA01 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA01:");
            mesaA02 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA02:");
            mesaA03 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA03:");
            mesaA04 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA04:");
            mesaA05 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA05:");
            mesaA06 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA06:");
            mesaA07 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA07:");
            mesaA08 = Auxiliar.LeParametroArquivo(registroMesas, "mesaA08:");

            mesaB09 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB09:");
            mesaB10 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB10:");
            mesaB11 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB11:");
            mesaB12 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB12:");
            mesaB13 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB13:");
            mesaB14 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB14:");
            mesaB15 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB15:");
            mesaB16 = Auxiliar.LeParametroArquivo(registroMesas, "mesaB16:");
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

        // -------------------------------------------------------------------------------------------------------------
        // Grupo 1: Variáveis de Informação da Disponibilidade das Mesas
        IdNv1 = 2;
        i = 0;
        MsgXMLArray[IdNv0][IdNv1][0][0] = "MESAS";
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A00", mesaA00, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A01", mesaA01, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A02", mesaA02, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A03", mesaA03, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A04", mesaA04, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A05", mesaA05, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A06", mesaA06, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A07", mesaA07, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("A08", mesaA08, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B09", mesaB09, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B10", mesaB10, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B11", mesaB11, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B12", mesaB12, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B13", mesaB13, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B14", mesaB14, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B15", mesaB15, normal);
        i = i + 1;
        MsgXMLArray[IdNv0][IdNv1][i] = Auxiliar.EntTagValue("B16", mesaB16, normal);

        // Carrega o número de elementos do Grupo 2
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(i);

        // Retorna a Mensagem XML completa em formato de String
        MsgXML = Auxiliar.StringXML(MsgXMLArray);
        return(MsgXML);
    }

}
