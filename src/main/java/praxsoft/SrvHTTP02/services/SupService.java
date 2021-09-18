package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Dados001;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.StringTokenizer;

@Service
public class SupService {

    private static boolean opLocal;
    private static boolean verbose;
    private static String endIpConc;

    public static boolean isOpLocal() {
        return opLocal;
    }
    public static boolean isVerbose() {
        return verbose;
    }
    public static String getEndIpConc() {
        return endIpConc;
    }

    private static int ContMsgCoAP = 0;

    //******************************************************************************************************************
    // Nome do Método: LeArquivoConfiguracao()                                                                         *
    //	                                                                                                               *
    // Funcao: lê o arquivo de configuração                                                                            *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: String com o arquivo de configuração                                                                     *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    public boolean LeArquivoConfiguracao() {
        boolean lidoArqConf = true;
        String ArquivoConf = null;
        String caminho = "recursos/";
        String nomeArquivo = "srvhttp02.cnf";

        try {
            Arquivo arquivo = new Arquivo();
            ArquivoConf = arquivo.LeTexto(caminho, nomeArquivo);

            String ModoOp = LeParametroArqConf(ArquivoConf, "ModoOp:");
            String Verbose = LeParametroArqConf(ArquivoConf, "Verbose:");
            String EndIpConcArduino = LeParametroArqConf(ArquivoConf, "EndIpConcArduino:");

            if (ModoOp.equals("local")) { opLocal = true; } else { opLocal = false; }
            if (Verbose.equals("true")) { verbose = true; } else { verbose = false; }
            endIpConc = EndIpConcArduino;

            System.out.println("\nLido Arquivo de Configuração\n");
            if (opLocal) { System.out.println("Modo de Operação Local"); }
            else { System.out.println("Modo de Operação Remoto (Nuvem)"); }
            System.out.println("Verbose: " + verbose);
            System.out.println("Endereço IP do Concentrador Arduino: " + endIpConc);
            System.out.println("");

        } catch (Exception e) {
            Terminal("Arquivo de Configuração nao encontrado.", false);
            lidoArqConf = false;
        }

        return lidoArqConf;
    }

    private static String LeParametroArqConf (String arquivo, String token){
        int Indice = arquivo.lastIndexOf(token);
        int indiceF = arquivo.length() - 1;
        String parametro = null;
        if (Indice >= 0) {
            Indice = Indice + token.length() + 1;
            String Substring = arquivo.substring(Indice, indiceF);
            StringTokenizer parseToken = new StringTokenizer(Substring);
            parametro = parseToken.nextToken();
        }
        return parametro;
    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoTxt                                                                                    *
    //	                                                                                                               *
    // Funcao: lê um arquivo texto (sequência de caracteres) do diretório recursos dentro do diretŕorio principal do   *
    //         servidor.                                                                                               *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    //                                                                                                                 *
    // Saida: String com o arquivo texto lido. Se ocorrer falha na leitura, o método retorna null                      *
    //******************************************************************************************************************
    //
    public String LeArquivoTxt(String caminhoSite, String nomeArquivo) {
        String caminho = "recursos/" + caminhoSite;
        File Arquivo = new File(caminho + nomeArquivo);
        String arquivoLido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(Arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                arquivoLido = arquivoLido + linha + "\n";
            }
            Terminal("Lido Arquivo " + nomeArquivo, false);
            return arquivoLido;

        } catch (IOException e) {
            Terminal("Arquivo " + nomeArquivo + " nao encontrado.", false);
            return null;
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeArquivoByte                                                                                   *
    //	                                                                                                               *
    // Funcao: lê um arquivo binário (sequência de bytes) do diretório recursos dentro do diretŕorio principal do      *
    //         servidor.                                                                                               *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    //                                                                                                                 *
    // Saida: array com a sequência de bytes do arquivo lido. Se ocorrer falha na leitura, o método retorna null.      *
    //******************************************************************************************************************
    //
    public byte[] LeArquivoByte(String caminhoSite, String nomeArquivo) {
        String caminho = "recursos/" + caminhoSite;
        try {
            Arquivo arquivo = new Arquivo();
            byte[] arquivoByte = arquivo.LeByte(caminho, nomeArquivo);
            Terminal("Lido Arquivo " + nomeArquivo, false);
            return arquivoByte;
        } catch (Exception e) {
            Terminal("Arquivo " + nomeArquivo + " nao encontrado.", false);
            byte[] arrayErro = new byte[0];
            return arrayErro;
        }

    }

    //******************************************************************************************************************
    //                                                                                                                 *
    // Nome do Método: CoAPUDP                                                                                         *
    //                                                                                                                 *
    // Funcao: envia uma mensagem de requisição e recebe a mensagem de resposta do Controlador Arduino Mega            *
    //         em Protocolo CoAP                                                                                       *
    //                                                                                                                 *
    // Byte |           0         |      1       |      2      |        3        |        4        |        5        | *
    // Bit  | 7 6 | 5 4 | 3 2 1 0 |  7654  3210  |  7654 3210  | 7 6 5 4 3 2 1 0 | 7 6 5 4 3 2 1 0 | 7 6 5 4 3 2 1 0 | *
    //      | Ver |Tipo |  Token  | Código (c.m) | Message ID  |      Option     |   Payload ID    |                   *
    //                                                                                                                 *
    // Ver (Versão) = 01 (O número da versão do protocolo CoAP é fixo)  / TKL (Token) = 0000 (não é usado)             *
    // Tipo (de Mensagem): 00 Confirmável (CON) / 01 Não-Confirmável (NON) / 10 Reconhecimento (ACK) / 11 Reset (RST)  *
    //                                                                                                                 *
    // Códigos de Solicitação: 0000 0000 EMPTY / 0000 0001 GET   / 0000 0010 POST / 0000 0011 PUT / 0000 0100 DELETE   *
    //                                                                                                                 *
    // Códigos de Resposta (Sucesso): 0100 0001 Created / 0100 0010 Deleted / 0100 0011 Valid / 0100 0100 Changed      *
    //                                0100 0101 Content                                                                *
    //                                                                                                                 *
    // Códigos de Erro Cliente: 1000 0000 Bad Request / 1000 0001 Unauthorized / 1000 0010 Bad Option                  *
    //                          1000 0011 Forbidden / 1000 0100 Not Found / 1000 0101 Method Not Allowed               *
    //                          1000 0110 Not Acceptable / 1000 1100 Request Entity Incomplete                         *
    //                                                                                                                 *
    // Códigos de Erro Servidor: 1010 0000 Internal Server Error / 1010 0001 Not Implemented / 1010 0010 Bad Gateway   *
    //                           1010 0011 Service Unavailable / 1010 0100 Gateway Timeout                             *
    //                           1010 0101 Proxying Not Supported                                                      *
    //                                                                                                                 *
    // Message ID (Identificação da mensagem): inteiro de 16 bits sem sinal Mensagem Enviada e Mensagem de Resposta    *
    //                                         com mesmo ID                                                            *
    //                                                                                                                 *
    // Option (Opções) = 0000 0000 (não é usado) / Identificador de Início do Payload: 1111 1111                       *
    //******************************************************************************************************************
    //
    public byte[] ClienteCoAPUDP(String EndIP, String URI, int Comando) {

        int Porta = 5683; // Porta padrão para conexões CoAP em UDP

        int TamMsgRspCoAP = 320;
        int TamMsgSrv = 320;
        byte[] MsgRecCoAP = new byte[TamMsgRspCoAP];
        byte[] MsgEnvSrv = new byte[TamMsgSrv];

        try {
            byte[] MsgReqCoAP = new byte[32];

            int TamURI = URI.length();
            byte DH[] = new byte[6];

            DH = LeDataHora();

            MsgReqCoAP[0] = 0x40;                     // Versão = 01 / Tipo = 00 / Token = 0000
            MsgReqCoAP[1] = 0x01;                     // Código de Solicitação: 0.01 GET
            MsgReqCoAP[2] = ByteHigh(ContMsgCoAP);    // Byte Mais Significativo do Identificador da Mensagem
            MsgReqCoAP[3] = ByteLow(ContMsgCoAP);     // Byte Menos Significativo do Identificador da Mensagem
            ContMsgCoAP = ContMsgCoAP + 1;            // Incrementa o Identificador de mensagens
            MsgReqCoAP[4] = (byte) (0xB0 + TamURI);   // Delta: 11 - Primeira Opcao 11: Uri-path e Núm. Bytes da URI
            int j = 5;
            for (int i = 0; i < TamURI; i++) {        // Carrega os codigos ASCII da URI
                char Char = URI.charAt(i);
                int ASCII = (int) Char;
                MsgReqCoAP[i + 5] = (byte) ASCII;
                j = j + 1;
            }
            MsgReqCoAP[j] = (byte) 0x11;    // Delta: 1 - Segunda Opcao (11 + 1 = 12): Content-format e Núm. Bytes (1)
            j = j + 1;
            MsgReqCoAP[j] = 42;             // Codigo da Opcao Content-format: application/octet-stream
            j = j + 1;
            MsgReqCoAP[j] = -1;             // Identificador de Inicio do Payload (255)
            j = j + 1;
            MsgReqCoAP[j] = (byte) Comando;  // Carrega o Código do Comando no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[0];          // Carrega a Hora do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[1];          // Carrega a Minuto do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[2];          // Carrega a Segundo do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[3];          // Carrega a Dia do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[4];          // Carrega a Mes do Computador no Payload
            j = j + 1;
            MsgReqCoAP[j] = DH[5];          // Carrega a Ano do Computador no Payload
            j = j + 1;
            int TamCab = j;                 // Carrega o número de bytes do cabeçalho

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(EndIP);
            clientSocket.setSoTimeout(1000);
            DatagramPacket sendPacket = new DatagramPacket(MsgReqCoAP, TamCab, IPAddress, Porta);
            DatagramPacket receivePacket = new DatagramPacket(MsgRecCoAP, TamMsgRspCoAP);

            clientSocket.send(sendPacket);
            Terminal("Enviada Requisicao CoAP para o Concentrador", false);

            // Espera a Mensagem CoAP de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
            try {
                clientSocket.receive(receivePacket);
                MsgRecCoAP[30] = 1;
                Dados001.LeEstMedsPayload(MsgRecCoAP);
                Terminal("Recebida Mensagem CoAP do Concentrador", false);
            } catch (java.net.SocketTimeoutException e) {
                MsgRecCoAP[0] = 0x40;
                MsgRecCoAP[1] = 1;
                MsgRecCoAP[30] = 0;
                Terminal(" - Erro: o Concentrador nao Respondeu " + MsgRecCoAP[14], false);
            }
            clientSocket.close();
        } catch (IOException err) {
            Terminal("Erro na Rotina EnvRecMsgSrv: " + err, false);
        }
        return (MsgRecCoAP);
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
    public static String StringXML(String MsgXMLArray[][][][]) {
        String MsgXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        MsgXML = MsgXML + "<" + MsgXMLArray[0][0][0][0] + ">\n";         // Imprime a Tag de Nivel 0

        char Dezena = MsgXMLArray[0][0][0][1].charAt(0);
        char Unidade = MsgXMLArray[0][0][0][1].charAt(1);

        // Obtem o Numero de Tags de Nivel 1
        int NmTagNv1 = SupService.TwoCharToInt(Dezena, Unidade);

        // Repete até imprimir todas as Tags de Nível 1 e Nível 2
        for (int i = 1; i <= NmTagNv1; i++) {

            // Imprime a Tag de Nivel 1 de Início do Grupo
            MsgXML = MsgXML + "  <" + MsgXMLArray[0][i][0][0] + ">\n";
            char DzNumVar = MsgXMLArray[0][i][0][1].charAt(0);
            char UnNumVar = MsgXMLArray[0][i][0][1].charAt(1);

            // Obtém o Número de Variáveis do Grupo
            int NumVar = SupService.TwoCharToInt(DzNumVar, UnNumVar);

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
    // Nome do Método: LeHoraData                                                                                      *
    //                                                                                                                 *
    // Funcao: le a data e hora do relogio do computador usando ZonedDateTime no formato string                        *
    //          "2020-01-01T10:38:17.240-03:00[America/Araguaina]"                                                     *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: array com 6 Bytes: [0] = Hora, [1] = Minuto, [2] = Segundo, [3] = Dia, [4] = Mês, [5] = Ano              *                                                                                 *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public byte[] LeDataHora() {

        LocalDateTime datahora = LocalDateTime.now();
        int Dia = datahora.getDayOfMonth();
        int Mes = datahora.getMonthValue();
        int Ano = datahora.getYear();
        int Anoc = Ano / 100;
        int Anou = Ano - 100 * Anoc;
        int Hora = datahora.getHour();
        int Minuto = datahora.getMinute();
        int Segundo = datahora.getSecond();
        byte DH[] = new byte[7];

        DH[0] = (byte) ByteLow(Hora);         // Hora
        DH[1] = (byte) ByteLow(Minuto);       // Minuto
        DH[2] = (byte) ByteLow(Segundo);      // Segundo
        DH[3] = (byte) ByteLow(Dia);          // Dia
        DH[4] = (byte) ByteLow(Mes);          // Mes
        DH[5] = (byte) ByteLow(Anoc);         // Ano (Unidades)
        DH[6] = (byte) ByteLow(Anou);         // Ano (Centenas)

        return (DH);
    }

    //******************************************************************************************************************
    // Nome do Método: ImprimeHoraData                                                                                 *
    //                                                                                                                 *
    // Funcao: gera uma string com a data e a hora recebida em um array de bytes                                       *
    //                                                                                                                 *
    // Entrada: Array[6] [0] = Hora, [1] = Minutos, [2] = Segundos, [3] = Dia, [4] = Mês, [5] = Ano, [6] = Ano         *
    //          Se Opcao = true imprime a hora e a data / Se Opcao = false imprime só a hora                           *
    //                                                                                                                 *
    // Saida: string no formato HH:MM:SS - DD/MM/AAAA                                                                  *                                                                                 *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String ImprimeHoraData(byte[] DH, boolean Opcao) {

        String Msg = "";
        if (DH[0] < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + DH[0] + ":";
        if (DH[1] < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + DH[1] + ":";
        if (DH[2] < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + DH[2];

        if (Opcao) {
            Msg = Msg + " - ";
            if (DH[3] < 10) {
                Msg = Msg + "0";
            }
            Msg = Msg + DH[3] + "/";
            if (DH[4] < 10) {
                Msg = Msg + "0";
            }
            Msg = Msg + DH[4] + "/" + DH[5] + DH[6];
        }

        return (Msg);
    }

    //******************************************************************************************************************
    // Nome do Método: Terminal                                                                                        *
    //                                                                                                                 *
    // Funcao: imprime uma mensagem no Terminal precedidsa pela hora e a data                                          *
    //                                                                                                                 *
    // Entrada: string com a mensagem, a flag Opcao e a flag de habilitação (Verbose)                                  *
    //          Se Opcao = true imprime a hora e a data / Se Opcao = false imprime só a hora                           *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public void Terminal(String Msg, boolean Opcao) {
        if (verbose) {
            System.out.println(ImprimeHoraData(LeDataHora(), Opcao) + " - " + Msg);
        }
    }

    //******************************************************************************************************************
    // Nome do Método: EntTagValue                                                                                     *
    //                                                                                                                 *
    // Funcao: monta um array de duas strings a partir de duas strings (Tag e Value). Se a flag falha = true,          *
    //         preenche o campo Value com ---------- indicando falha.                                                  *
    //                                                                                                                 *
    // Entrada: string com a Tag, string com o Value e boolean falha                                                   *
    //                                                                                                                 *
    // Saida: array[2] com a string Tag na posição 0 e a string Values na posição 1.                                   *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String[] EntTagValue(String tag, String value, boolean normal) {
        String[] tagvalue = new String[2];
        tagvalue[0] = tag;
        if (normal) {
            tagvalue[1] = value;
        } else {
            tagvalue[1] = "----------";
        }
        return (tagvalue);
    }

    //******************************************************************************************************************
    // Nome da Rotina: BytetoInt                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um valor byte para inteiro (conversao sem sinal)                                               *
    // Entrada: valor byte sem sinal de 0 a 255                                                                        *
    // Saida: valor (inteiro) sempre positivo de 0 a 255                                                               *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int BytetoInt(int valor) {
        if (valor < 0) {
            return (256 + valor);
        } else {
            return (valor);
        }
    }

    //******************************************************************************************************************
    // Nome da Rotina: TwoBytetoInt                                                                                    *
    //                                                                                                                 *
    // Funcao: converte dois bytes em sequencia de um array para inteiro (conversao sem sinal)                         *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                               *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                             *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int DoisBytesInt(int LSByte, int MSByte) {
        int lsb;
        int msb;
        if (LSByte < 0) {
            lsb = LSByte + 256;
        } else {
            lsb = LSByte;
        }
        if (MSByte < 0) {
            msb = MSByte + 256;
        } else {
            msb = MSByte;
        }
        return (lsb + 256 * msb);
    }

    //******************************************************************************************************************
    // Nome da Rotina: ThreeBytetoInt                                                                                  *
    //                                                                                                                 *
    // Funcao: converte tres bytes em sequencia de um array para inteiro (conversao sem sinal)                         *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                               *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                             *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int ThreeBytetoInt(int LSByte, int MSByte, int HSByte) {
        int lsb;
        int msb;
        int hsb;
        if (LSByte < 0) {
            lsb = LSByte + 256;
        } else {
            lsb = LSByte;
        }
        if (MSByte < 0) {
            msb = MSByte + 256;
        } else {
            msb = MSByte;
        }
        if (HSByte < 0) {
            hsb = HSByte + 256;
        } else {
            hsb = HSByte;
        }
        return (lsb + 256 * msb + 65536 * hsb);
    }

    //******************************************************************************************************************
    // Nome da Rotina: FormAnaHora                                                                                     *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo para uma string no formato HH:MM:SS  (hora:minuto:segundo)                 *
    // Entrada: valor inteiro em segundos                                                                              *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                        *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String FormAnaHora(int valor) {
        int Hora = valor / 3600;
        int Minuto = ((valor - (Hora * 3600)) / 60);
        int Segundo = valor - (Minuto * 60) - (Hora * 3600);
        String HMS = "";
        if (Hora < 10) {
            HMS = "0";
        }
        HMS = (HMS + Hora + ":");
        if (Minuto < 10) {
            HMS = HMS + "0";
        }
        HMS = (HMS + Minuto + ":");
        if (Segundo < 10) {
            HMS = HMS + "0";
        }
        HMS = HMS + Segundo;

        return (HMS);
    }

    //******************************************************************************************************************
    // Nome da Rotina: CharToByte                                                                                      *
    //                                                                                                                 *
    // Funcao: converte um caracter numerico em um valor numerico de 0 a 9                                             *
    // Entrada: caracter: '0' a '9'                                                                                    *
    // Saida: byte (valor numerico de 0 a 9)                                                                           *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int CharToByte(char caracter) {
        byte Num = 10;
        switch (caracter) {
            case '0':
                Num = 0;
                break;
            case '1':
                Num = 1;
                break;
            case '2':
                Num = 2;
                break;
            case '3':
                Num = 3;
                break;
            case '4':
                Num = 4;
                break;
            case '5':
                Num = 5;
                break;
            case '6':
                Num = 6;
                break;
            case '7':
                Num = 7;
                break;
            case '8':
                Num = 8;
                break;
            case '9':
                Num = 9;
                break;
        }
        return (Num);
    }

    //******************************************************************************************************************
    // Nome da Rotina: CharToInt                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um caracter numerico em um valor numerico de 0 a 9                                             *
    // Entrada: caracter: '0' a '9'                                                                                    *
    // Saida: int (valor numerico de 0 a 9)                                                                            *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int ChToInt(char caracter) {
        int Num = 10;
        switch (caracter) {
            case '0':
                Num = 0;
                break;
            case '1':
                Num = 1;
                break;
            case '2':
                Num = 2;
                break;
            case '3':
                Num = 3;
                break;
            case '4':
                Num = 4;
                break;
            case '5':
                Num = 5;
                break;
            case '6':
                Num = 6;
                break;
            case '7':
                Num = 7;
                break;
            case '8':
                Num = 8;
                break;
            case '9':
                Num = 9;
                break;
        }
        return (Num);
    }

    //******************************************************************************************************************
    // Nome da Rotina: TwoCharToByte                                                                                   *
    //                                                                                                                 *
    // Funcao: converte dois caracteres numericos em um valor numerico de 00 a 99                                      *
    // Entrada: caracteres dezena e unidade ('0' a '9')                                                                *
    // Saida: byte (valor numerico de 00 a 99)                                                                         *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int TwoCharToByte(char Ch10, char Ch1) {
        int Num = 10 * CharToByte(Ch10) + CharToByte(Ch1);
        return ((byte) Num);
    }

    //******************************************************************************************************************
    // Nome da Rotina: TwoCharToInt                                                                                    *
    //                                                                                                                 *
    // Funcao: converte dois caracteres numericos em um valor numerico de 00 a 99                                      *
    // Entrada: caracteres dezena e unidade ('0' a '9')                                                                *
    // Saida: int (valor numerico de 00 a 99)                                                                          *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int TwoCharToInt(char Ch10, char Ch1) {
        int Num = 10 * CharToByte(Ch10) + CharToByte(Ch1);
        return (Num);
    }

    //******************************************************************************************************************
    // Nome da Rotina: FourCharToInt                                                                                   *
    //                                                                                                                 *
    // Funcao: converte quatro caracteres numericos em um valor numerico de 0000 a 9999                                *
    // Entrada: caracteres milhar, centena, dezena e unidade ('0' a '9')                                               *
    // Saida: int (valor numerico de 0000 a 9999)                                                                      *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int FourCharToInt(char Ch1000, char Ch100, char Ch10, char Ch1) {
        int Num = 1000 * CharToByte(Ch1000) + 100 * CharToByte(Ch100) + 10 * CharToByte(Ch10) + CharToByte(Ch1);
        return (Num);
    }

    //******************************************************************************************************************
    // Nome da Rotina: StringToInt                                                                                     *
    //                                                                                                                 *
    // Funcao: converte uma string de até quatro caracteres numericos em um valor numerico de 0000 a 9999              *
    // Entrada: string com um numero entre 0 e 9999                                                                    *
    // Saida: int (valor numerico de 0000 a 9999 correspondente à string de entrada)                                   *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static int StringToInt(String StNm) {
        int Num = 0;
        int TamNum = StNm.length();

        if (TamNum == 1) {
            Num = ChToInt(StNm.charAt(0));
        }
        if (TamNum == 2) {
            Num = 10 * ChToInt(StNm.charAt(0)) + ChToInt(StNm.charAt(1));
        }
        if (TamNum == 3) {
            Num = 100 * ChToInt(StNm.charAt(0)) + 10 * ChToInt(StNm.charAt(1)) + ChToInt(StNm.charAt(2));
        }
        if (TamNum == 4) {
            Num = 1000 * ChToInt(StNm.charAt(0)) + 100 * ChToInt(StNm.charAt(1)) + 10 * ChToInt(StNm.charAt(2)) + ChToInt(StNm.charAt(3));
        }
        return (Num);
    }

    //*****************************************************************************************************************
    // Nome da Rotina: ByteLow                                                                                        *
    //                                                                                                                *
    // Funcao: obtem o byte menos significativo de um valor inteiro                                                   *
    // Entrada: valor inteiro                                                                                         *
    // Saida: byte menos significativo                                                                                *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    public static byte ByteLow(int valor) {
        int BH = valor / 256;
        int BL = valor - 256 * BH;
        return ((byte) BL);
    }


    //*****************************************************************************************************************
    // Nome da Rotina: ByteHigh                                                                                       *
    //                                                                                                                *
    // Funcao: obtem o byte mais significativo de um valor inteiro                                                    *
    // Entrada: valor inteiro                                                                                         *
    // Saida: byte mais significativo                                                                                 *
    //                                                                                                                *
    //*****************************************************************************************************************
    //
    public static byte ByteHigh(int valor) {
        int BH = valor / 256;
        return ((byte) BH);
    }

    //******************************************************************************************************************
    // Nome da Rotina: ImpHora                                                                                         *
    //                                                                                                                 *
    // Funcao: gera umna string com hora:minuto:segundo dia/mes/ano                                                    *
    // Entrada: valor hora, minuto, segundo, dia, mes, ano                                                             *
    // Saida: byte mais significativo                                                                                  *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String ImpHora(int hora, int minuto, int segundo) {

        String Msg = "";
        if (hora < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + hora + ":";

        if (minuto < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + minuto + ":";

        if (segundo < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + segundo;

        return (Msg);
    }

    //******************************************************************************************************************
    // Nome da Rotina: ImpData                                                                                         *
    //                                                                                                                 *
    // Funcao: gera umna string com hora:minuto:segundo dia/mes/ano                                                    *
    // Entrada: valor hora, minuto, segundo, dia, mes, ano                                                             *
    // Saida: byte mais significativo                                                                                  *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String ImpData(int dia, int mes, int ano) {

        String Msg = "";
        if (dia < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + dia + "/";

        if (mes < 10) {
            Msg = Msg + "0";
        }
        Msg = Msg + mes + "/" + ano + " ";

        return (Msg);
    }

    //******************************************************************************************************************
    // Nome da Rotina: FrmAna                                                                                          *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo em formato x100 para uma string com parte inteira e duas casas decimais    *
    // Entrada: valor inteiro no formato x100 e unidade em string                                                      *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                        *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String FrmAna(int valor) {
        int inteiro;
        int decimal;
        inteiro = valor / 100;
        decimal = valor - 100 * inteiro;
        String Valor = (inteiro + ".");
        if (decimal > 9) {
            Valor = Valor + decimal;
        } else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor);
    }

    //******************************************************************************************************************
    // Nome da Rotina: FrmAna3                                                                                         *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo em formato x1000 para uma string com parte inteira e tres casas decimais   *
    // Entrada: valor inteiro no formato x1000 e unidade em string                                                     *
    // Saida: string do numero no formato "parte inteira","tres casas decimais"                                        *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String FrmAna3(int valor) {
        int inteiro;
        int decimal;
        inteiro = valor / 1000;
        decimal = valor - 1000 * inteiro;
        String Valor = (inteiro + ".");
        if (decimal > 90) {
            Valor = Valor + decimal;
        } else {
            Valor = Valor + "0" + decimal;
        }
        return (Valor);
    }

    //******************************************************************************************************************
    // Nome da Rotina: FormAna3                                                                                        *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo em formato x1000 para uma string com parte inteira e tres casas decimais   *
    // Entrada: valor inteiro no formato x1000                                                                         *
    // Saida: string do numero no formato "parte inteira","tres casas decimais"                                        *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    //public static String FormAna3(int valor) {
    //    int inteiro;
    //    int decimal;
    //    inteiro = valor / 1000;
    //    decimal = valor - 1000 * inteiro;
    //    String Valor = (inteiro + ".");
    //    if (decimal > 90) {
    //        Valor = Valor + decimal;
    //    }
    //    else {
    //        Valor = Valor + "0" + decimal;
    //    }
    //    return (Valor);
    //}

    //******************************************************************************************************************
    // Nome da Rotina: FormAnaInt                                                                                      *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo para uma string                                                            *
    // Entrada: valor inteiro no formato x100                                                                          *
    // Saida: string do numero no formato "parte inteira","duas casas decimais"                                        *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    //public static String FormAnaInt(int valor) {

    //    String Valor = (valor + "");

    //    return (Valor);
    //}

    //******************************************************************************************************************
    // Nome da Rotina: FrmAnaInt                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um inteiro positivo para uma string                                                            *
    // Entrada: valor inteiro                                                                                          *
    // Saida: string do numero no formato inteiro                                                                      *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String FrmAnaInt(int valor) {

        String Valor = (valor + "");

        return (Valor);
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
    public static String IntToStr2(int valInt) {
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

    public static String EstadoSimples(boolean estado, String estadoTrue, String estadoFalse) {
        String strEstado = "";
        if (estado) {
            strEstado = estadoTrue;
        } else {
            strEstado = estadoFalse;
        }
        return strEstado;
    }

    public static String EstFonteEnergia(boolean fonte, boolean habCg, String feTrue, String feFalse, String feStby) {
        String strFe = feFalse;
        if (fonte) {
            strFe = feTrue;
        } else {
            if (habCg) {
                strFe = feStby;
            }
        }
        return strFe;
    }

    public static String EstadoCaixaAzul(byte estado) {
        String estcxaz = "";
        switch (estado) {

            case 0:  //  EstadoCxAz = 0 => Estado da Caixa Azul = Indefinido
                estcxaz = "Indefinido";
                break;

            case 1:  //  EstadoCxAz = 1 => Estado da Caixa Azul = Precisa Encher Nivel Baixo
                estcxaz = "Precisa Encher";
                break;

            case 2:  //  EstadoCxAz = 2 => Estado da Caixa Azul = Precisa Encher Nivel Normal
                estcxaz = "Precisa Encher";
                break;

            case 3:  //  EstadoCxAz = 3 => Estado da Caixa Azul = Cheia
                estcxaz = "Cheia";
                break;

            case 4:  //  EstadoCxAz = 4 => Estado da Caixa Azul = Falha de Sinalizacao 1

            case 5:  // EstadoCxAz = 5 => Estado da Caixa Azul = Falha de Sinalizacao 2
                estcxaz = "Falha Boia";
                break;
        }
        return estcxaz;
    }

    public static String NivelCaixaAzul(byte estado) {
        String nivcxaz = "";
        switch (estado) {

            case 0:  //  EstadoCxAz = 0 => Estado da Caixa Azul = Indefinido
                nivcxaz = "Indefinido";
                break;

            case 1:  //  EstadoCxAz = 1 => Estado da Caixa Azul = Precisa Encher Nivel Baixo
                nivcxaz = "Baixo";
                break;

            case 2:  //  EstadoCxAz = 2 => Estado da Caixa Azul = Precisa Encher Nivel Normal
                nivcxaz = "Normal";
                break;

            case 3:  //  EstadoCxAz = 3 => Estado da Caixa Azul = Cheia
                nivcxaz = "Normal";
                break;

            case 4:  //  EstadoCxAz = 4 => Estado da Caixa Azul = Falha de Sinalizacao 1

            case 5:  // EstadoCxAz = 5 => Estado da Caixa Azul = Falha de Sinalizacao 2
                nivcxaz = "";
                break;
        }
        return nivcxaz;
    }

    public static String EstadoDepRede(boolean estRede, boolean estado, String strTrue, String strFalse) {
        String strEst = "";
        if (estRede) {
            if (estado) {
                strEst = strTrue;
            } else {
                strEst = strFalse;
            }
        } else {
            strEst = "Falta CA";
        }
        return strEst;
    }

    public static String EstadoRede(boolean estRede, int tensaoRede, int limite) {
        String estVrd = "";
        if(estRede) {
            if (tensaoRede > 19000) {
                estVrd = "Normal";
            } else {
                estVrd = "(Baixa)";
            }
        }
        else {
            estVrd = "Falta CA";
        }
        return estVrd;
    }

    public static int CalcEficienciaInversor(int weInv, int wsInv) {
        int EfIv2 = 0;
        if (weInv > 2000) {
            EfIv2 = (100 * wsInv) / weInv;
        }
        else {
            EfIv2 = 0;
        }
        return EfIv2;
    }

}
