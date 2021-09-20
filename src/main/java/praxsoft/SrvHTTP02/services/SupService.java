package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;
import praxsoft.SrvHTTP02.domain.Dados001;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


@Service
public class SupService {

    private static int ContMsgCoAP = 0;

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

            DH = Auxiliar.LeDataHora();

            MsgReqCoAP[0] = 0x40;                            // Versão = 01 / Tipo = 00 / Token = 0000
            MsgReqCoAP[1] = 0x01;                            // Código de Solicitação: 0.01 GET
            MsgReqCoAP[2] = Auxiliar.ByteHigh(ContMsgCoAP);  // Byte Mais Significativo do Identificador da Mensagem
            MsgReqCoAP[3] = Auxiliar.ByteLow(ContMsgCoAP);   // Byte Menos Significativo do Identificador da Mensagem
            ContMsgCoAP = ContMsgCoAP + 1;                   // Incrementa o Identificador de mensagens
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
            Auxiliar.Terminal("Enviada Requisicao CoAP para o Concentrador", false);

            // Espera a Mensagem CoAP de Resposta. Se a mensagem de resposta  for recebida, carrega nas variáveis
            try {
                clientSocket.receive(receivePacket);
                MsgRecCoAP[30] = 1;
                Dados001.LeEstMedsPayload(MsgRecCoAP);
                Auxiliar.Terminal("Recebida Mensagem CoAP do Concentrador", false);
            } catch (java.net.SocketTimeoutException e) {
                MsgRecCoAP[0] = 0x40;
                MsgRecCoAP[1] = 1;
                MsgRecCoAP[30] = 0;
                Auxiliar.Terminal(" - Erro: o Concentrador nao Respondeu " + MsgRecCoAP[14], false);
            }
            clientSocket.close();
        } catch (IOException err) {
            Auxiliar.Terminal("Erro na Rotina EnvRecMsgSrv: " + err, false);
        }
        return (MsgRecCoAP);
    }

}
