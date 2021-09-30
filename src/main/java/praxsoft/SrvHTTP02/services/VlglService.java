package praxsoft.SrvHTTP02.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Cliente;
import praxsoft.SrvHTTP02.domain.ReservaMesa;

@Service
public class VlglService {

    private String MsgXMLArray[][][][] = new String[1][4][120][2];

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
    public boolean VerificaAdmin(String idUsuario) {
        boolean adminOK = false;

        if(Inicia.getNomeUsuarioAdmin1().equals(idUsuario)) { adminOK = true; }
        if(Inicia.getNomeUsuarioAdmin2().equals(idUsuario)) { adminOK = true; }
        if(Inicia.getNomeUsuarioAdmin3().equals(idUsuario)) { adminOK = true; }

        return adminOK;
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

        Auxiliar.Terminal("Obs 1: " + cliente.getObs1(), false);
        Auxiliar.Terminal("Obs 2: " + cliente.getObs2(), false);

        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = cliente.getNomeUsuario() + ".clt";

        // Se o arquivo existe, renomeia para a.nomearquivo.clt e grava o novo arquivo de cadastro de cliente
        if (Arquivo.Existe(caminho, "a." + nomeArquivo)) {
            Arquivo.Apaga(caminho, "a." + nomeArquivo);
            if (Arquivo.Renomeia(caminho, nomeArquivo, "a." + nomeArquivo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " renomeado", false);
                if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                    Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
                }
            }
        }
        else { // Se o arquivo não existe, grava o novo arquivo de cadastro de cliente
            if (Arquivo.EscreveTexto(caminho, nomeArquivo, dadosArqNovo)) {
                Auxiliar.Terminal("Arquivo de cliente " + nomeArquivo + " modificado e salvo", false);
            }
        }
    }

    //******************************************************************************************************************
    // Nome do Método: AtualizaArquivo                                                                                 *
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
    public static boolean AtualizaArquivo(ReservaMesa reservaMesa) {

        boolean confirma = false;
        String caminho = "recursos/vlgl/reservas/";
        String nomeArquivo = reservaMesa.getDataReserva() + ".res";

        String dadosArquivo = Arquivo.LeTexto(caminho, nomeArquivo);
        if (dadosArquivo != null) {

            int numMesas = 17;
            String nomeUsuarioMesa[] = new String[numMesas];
            String nomeCompletoMesa[] = new String[numMesas];
            String numeroPessoasMesa[] = new String[numMesas];
            String horaChegadaMesa[] = new String[numMesas];
            String adminResponsavelMesa[] = new String[numMesas];
            String horaDataRegistroMesa[] = new String[numMesas];

            String indice;
            String letra = "A";
            String token;

            for (int i = 0; i < numMesas; i++) {
                if (i > 8) { letra = "B"; }
                indice = Auxiliar.IntToStr2(i);
                token = "NOU" + letra + indice + ":";   // Nome de usuário
                nomeUsuarioMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "NOC" + letra + indice + ":";   // Nome Completo
                nomeCompletoMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "NUP" + letra + indice + ":";   // Número de pessoas
                numeroPessoasMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "HOC" + letra + indice + ":";   // Hora chegada
                horaChegadaMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "ADR" + letra + indice + ":";   // Admin responsável
                adminResponsavelMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
                token = "HDR" + letra + indice + ":";   // Data e Hora de registro da reserva
                horaDataRegistroMesa[i] = Auxiliar.LeParametroArquivo(dadosArquivo, token);
            }

            char ch10 = reservaMesa.getMesaSelecionada().charAt(1);
            char ch1 = reservaMesa.getMesaSelecionada().charAt(2);
            int indiceMesa = Auxiliar.TwoCharToInt(ch10, ch1);

            Auxiliar.Terminal("Indice mesa: " + indiceMesa, false);
            reservaMesa.MostraCamposTerminal();

            Auxiliar.LeDataHora();
            if ((indiceMesa >= 0) && (indiceMesa < numMesas)) {
                nomeUsuarioMesa[indiceMesa] = reservaMesa.getNomeUsuario();     // Nome de usuário
                nomeCompletoMesa[indiceMesa] = reservaMesa.getNomeCliente();    // Nome Completo
                numeroPessoasMesa[indiceMesa] = reservaMesa.getNumPessoas();    // Número de pessoas
                horaChegadaMesa[indiceMesa] = reservaMesa.getHoraChegada();     // Hora chegada
                adminResponsavelMesa[indiceMesa] = reservaMesa.getAdminResp();  // Admin responsável
                String horaData = Auxiliar.ImprimeHoraData(Auxiliar.LeDataHora(), true);
                horaDataRegistroMesa[indiceMesa] = horaData;                    // Hora e data de registro da reserva
            }
            else {
                nomeUsuarioMesa[indiceMesa] = "null";
                nomeCompletoMesa[indiceMesa] = "null";
                numeroPessoasMesa[indiceMesa] = "null";
                horaChegadaMesa[indiceMesa] = "null";
                adminResponsavelMesa[indiceMesa] = "null";
                horaDataRegistroMesa[indiceMesa] = "null";
            }

            letra = "A";
            String dadosArqNovo = "{\n";
            for (int i = 0; i < numMesas; i++) {
                if (i > 8) { letra = "B"; }
                indice = Auxiliar.IntToStr2(i);

                token = "  NOU" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + nomeUsuarioMesa[i] + "\n";

                token = "  NOC" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + nomeCompletoMesa[i] + "\n";

                token = "  NUP" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + numeroPessoasMesa[i] + "\n";

                token = "  HOC" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + horaChegadaMesa[i] + "\n";

                token = "  ADR" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + adminResponsavelMesa[i] + "\n";

                token = "  HDR" + letra + indice + ": ";
                dadosArqNovo = dadosArqNovo + token + horaDataRegistroMesa[i] + "\n";
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
                    confirma = true;
                }
            }

            System.out.println("");
            System.out.println(dadosArqNovo);
            System.out.println("");

        }
        else {
            Auxiliar.Terminal("Arquivo de reservas do dia " + nomeArquivo + " não encontrado", false);
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
    // Saida:                                                                                                          *
    //******************************************************************************************************************
    //
    public static void EscreveArquivoReservaNovo(String dataRes) {

        String caminho = "recursos/vlgl/reservas/";
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
            dadosArqNovo = dadosArqNovo + "  HDR" + letra + Indice + ": null\n";
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

        String nomeAdmin = "null";
        if (Inicia.getNomeUsuarioAdmin1().equals(idAdmin)) { nomeAdmin = "Ingrid Loyane F. Silva"; }
        if (Inicia.getNomeUsuarioAdmin2().equals(idAdmin)) { nomeAdmin = "Gouthier Dias"; }
        if (Inicia.getNomeUsuarioAdmin3().equals(idAdmin)) { nomeAdmin = "Antonio Bernardo Praxedes"; }

        int IdNv0 = 0;
        MsgXMLArray[0][0][0][0] = "LOCAL001";
        MsgXMLArray[0][0][0][1] = "01";

        int IdNv1 = 1;  // Grupo 1: Variáveis de Informação do Administrador
        MsgXMLArray[IdNv0][IdNv1][0][0] = "ADMIN";    // Carrega a Tag do Grupo 1
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("ID", idAdmin);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("NOME", nomeAdmin);
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(2);

        String msgRsp = StringXML();

        return(msgRsp);
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

        MsgXMLArray[0][0][0][0] = "LOCAL001";    // Identificador do Local
        MsgXMLArray[0][0][0][1] = "01";          // Número de Grupos (ESTADO e CLIENTE)

        CarregaClienteArray(nomeUsuario,0, 1);

        return(StringXML());
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
    public void CarregaClienteArray(String nomeUsuario, int IdNv0, int IdNv1) {

        int numCampos = 10;
        String[] cliente = new String[numCampos];

        String caminho = "recursos/vlgl/clientes/";
        String nomeArquivo = nomeUsuario + ".clt";
        String registroCliente = Arquivo.LeTexto(caminho, nomeArquivo);

        if (registroCliente == null) {
            for (int i = 0; i < numCampos; i++) {
                cliente[i] = "null";
            }
        }
        else {
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
    public String MontaXMLData(String dataReserva) {

        MsgXMLArray[0][0][0][0] = "LOCAL001";
        MsgXMLArray[0][0][0][1] = "01";

        CarregaDataArray(dataReserva,0, 1);

        return(StringXML());
    }

    //******************************************************************************************************************
    // Nome do Método: MontaXMLConfirma                                                                                *
    //	                                                                                                               *
    // Data: 29/09/2021                                                                                                *
    //                                                                                                                 *
    // Funcao: monta uma string XML com as informações referentes aos dados de reserva de mesa e também as             *
    //         informações de disponibilidade das mesas lidas de um arquivo do tipo texto que tem o nome no            *
    //         formato DD-MM-AAAA.res                                                                                  *
    //                                                                                                                 *
    // Entrada: objeto da classe ReservaMesa                                                                           *
    //                                                                                                                 *
    // Saida: string com a mensagem XML                                                                                *
    //******************************************************************************************************************
    //
    public String MontaXMLConfirma(ReservaMesa reservaMesa, boolean confirma) {

        MsgXMLArray[0][0][0][0] = "LOCAL001";
        MsgXMLArray[0][0][0][1] = "02";

        CarregaEstadoArray(reservaMesa, confirma, 0, 1);
        CarregaDataArray(reservaMesa.getDataReserva(),0, 2);

        return(StringXML());
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
    public void CarregaEstadoArray(ReservaMesa reservaMesa, boolean confirma, int IdNv0, int IdNv1) {

        String confirmaStr = "não";
        if (confirma) { confirmaStr = "sim"; }

        MsgXMLArray[IdNv0][IdNv1][0][0] = "ESTADO";
        MsgXMLArray[IdNv0][IdNv1][1] = Auxiliar.EntTagValue("CONFIRMA", confirmaStr);
        MsgXMLArray[IdNv0][IdNv1][2] = Auxiliar.EntTagValue("MESA", reservaMesa.getMesaSelecionada());
        MsgXMLArray[IdNv0][IdNv1][3] = Auxiliar.EntTagValue("DATA", reservaMesa.getDataReserva());
        MsgXMLArray[IdNv0][IdNv1][4] = Auxiliar.EntTagValue("ID", reservaMesa.getNomeUsuario());
        MsgXMLArray[IdNv0][IdNv1][5] = Auxiliar.EntTagValue("NOME", reservaMesa.getNomeCliente());
        MsgXMLArray[IdNv0][IdNv1][6] = Auxiliar.EntTagValue("NUMPES", reservaMesa.getNumPessoas());
        MsgXMLArray[IdNv0][IdNv1][7] = Auxiliar.EntTagValue("HORARES", reservaMesa.getHoraChegada());
        MsgXMLArray[IdNv0][IdNv1][8] = Auxiliar.EntTagValue("ADMINRESP", reservaMesa.getAdminResp());
        MsgXMLArray[IdNv0][IdNv1][0][1] = Auxiliar.IntToStr2(8);
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
    public void CarregaDataArray(String dataReserva, int IdNv0, int IdNv1) {

        String caminho = "recursos/vlgl/reservas/";
        String nomeArquivo = dataReserva + ".res";
        if (!Arquivo.Existe(caminho, nomeArquivo)) {
            EscreveArquivoReservaNovo(dataReserva);
        }

        String dadosArquivo = Arquivo.LeTexto(caminho, nomeArquivo);
        //String[][] mesa = new String[20][4];

        if (dadosArquivo != null) {

            System.out.println("Leu o arquivo: " + caminho + nomeArquivo);

            int numMesas = 17;
            String nomeUsuarioMesa[] = new String[numMesas];
            String nomeCompletoMesa[] = new String[numMesas];
            String numeroPessoasMesa[] = new String[numMesas];
            String horaChegadaMesa[] = new String[numMesas];
            String adminResponsavelMesa[] = new String[numMesas];
            String horaDataRegistroMesa[] = new String[numMesas];

            String indice;
            String letra = "A";
            String tag;

            for (int k = 0; k < numMesas; k++) {
                if (k > 8) { letra = "B"; }
                indice = Auxiliar.IntToStr2(k);
                tag = "NOU" + letra + indice + ":";   // Nome de usuário
                nomeUsuarioMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
                tag = "NOC" + letra + indice + ":";   // Nome Completo
                nomeCompletoMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
                tag = "NUP" + letra + indice + ":";   // Número de pessoas
                numeroPessoasMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
                tag = "HOC" + letra + indice + ":";   // Hora chegada
                horaChegadaMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
                tag = "ADR" + letra + indice + ":";   // Admin responsável
                adminResponsavelMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
                tag = "HDR" + letra + indice + ":";   // Data e Hora de registro da reserva
                horaDataRegistroMesa[k] = Auxiliar.LeParametroArquivo(dadosArquivo, tag);
            }

            MsgXMLArray[IdNv0][IdNv1][0][0] = "MESAS";
            int i = 1;
            letra = "A";

            for (int k = 0; k < numMesas; k++) {
                indice = Auxiliar.IntToStr2(k);
                if (k > 8) { letra = "B"; }

                tag = "NOU" + letra + indice;
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, nomeUsuarioMesa[k]);

                tag = "NOC" + letra + indice;   // Nome Completo
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, nomeCompletoMesa[k]);

                tag = "NUP" + letra + indice;   // Número de pessoas
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, numeroPessoasMesa[k]);

                tag = "HOC" + letra + indice;   // Hora chegada
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, horaChegadaMesa[k]);

                tag = "ADR" + letra + indice;   // Admin responsável
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, adminResponsavelMesa[k]);

                tag = "HDR" + letra + indice;   // Data e Hora de registro da reserva
                MsgXMLArray[IdNv0][IdNv1][i++] = Auxiliar.EntTagValue(tag, horaDataRegistroMesa[k]);
            }
            String numElementosGrupo = Auxiliar.IntToStr4(i - 1);
            MsgXMLArray[IdNv0][IdNv1][0][1] = numElementosGrupo;

            System.out.println("Número de elementos: " + MsgXMLArray[IdNv0][IdNv1][0][1]);
        }
    }

    //******************************************************************************************************************
    //                                                                                                                 *
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
    public String StringXML() {
        String MsgXML = "";
        MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";         // Imprime a Tag de Nivel 0

        char Dezena = MsgXMLArray[0][0][0][1].charAt(0);
        char Unidade = MsgXMLArray[0][0][0][1].charAt(1);

        // Obtem o Numero de Tags de Nivel 1
        int NmTagNv1 = Auxiliar.TwoCharToInt(Dezena, Unidade);

        // Repete até imprimir todas as Tags de Nível 1 e Nível 2
        for (int i = 1; i <= NmTagNv1; i++) {

            // Imprime a Tag de Nivel 1 de Início do Grupo
            MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";
            //char DzNumVar = MsgXMLArray[0][i][0][1].charAt(0);
            //char UnNumVar = MsgXMLArray[0][i][0][1].charAt(1);

            // Obtém o Número de Variáveis do Grupo
            //int NumVar = Auxiliar.TwoCharToInt(DzNumVar, UnNumVar);
            int NumVar = Auxiliar.StringToInt(MsgXMLArray[0][i][0][1]);

            // Repeta até imprimir todas as Tags de Nível 2 e suas variáveis
            for (int j = 1; j <= NumVar; j++) {

                // Imprime as Tags de Nível 2 e os Valores das Variáveis
                MsgXML = MsgXML + "    <" + MsgXMLArray[0][i][j][0] + ">"
                        + MsgXMLArray[0][i][j][1]
                        + "</" + MsgXMLArray[0][i][j][0] + ">\n";
            }

            // Imprime a Tag de Nivel 1 de Fim de Grupo
            MsgXML = MsgXML + "  </" + MsgXMLArray[0][i][0][0] + ">\n";
        }

        // Imprime a Tag de Nivel 0 de Fim
        MsgXML = MsgXML + "</" + MsgXMLArray[0][0][0][0] + ">";

        return (MsgXML);
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
            byte[] arquivoByte = Arquivo.LeArquivoByte(caminho, nomeArquivo);
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

    private String msgArqNaoEncontrado(String nomeArquivo) {

        return ("<p></p><h3>File not found: " + nomeArquivo + "</h3>");
    }

    //******************************************************************************************************************
    //                                                                                                                 *
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
    public String MontaJson() {

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

}
