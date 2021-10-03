package praxsoft.SrvHTTP02.services;

import java.time.LocalDateTime;
import java.util.StringTokenizer;

public class Auxiliar {

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
    public static byte[] LeDataHora() {

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
    // Nome do Método: ImpHoraData                                                                                     *
    //                                                                                                                 *
    // Funcao: gera uma string com a data e a hora recebida em um array de bytes                                       *
    //                                                                                                                 *
    // Entrada: Array[6] [0] = Hora, [1] = Minutos, [2] = Segundos, [3] = Dia, [4] = Mês, [5] = Ano, [6] = Ano         *
    //                                                                                                                 *
    // Saida: string no formato HH:MM:SS - DD/MM/AAAA                                                                  *                                                                                 *
    //                                                                                                                 *
    //******************************************************************************************************************
    //
    public static String ImpHoraData(byte[] DH) {

        String Msg = "";
        if (DH[0] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[0] + ":";
        if (DH[1] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[1] + ":";
        if (DH[2] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[2] + "  ";

        if (DH[3] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[3] + "/";
        if (DH[4] < 10) { Msg = Msg + "0"; }
        Msg = Msg + DH[4] + "/" + DH[5] + DH[6];

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
    public static void Terminal(String Msg, boolean Opcao) {
        if (Arquivo.isVerbose()) {
            System.out.println(ImprimeHoraData(LeDataHora(), Opcao) + " - " + Msg);
        }
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
    public static String[] EntTagValue(String tag, String value) {
        String[] tagvalue = new String[2];
        tagvalue[0] = tag;
        tagvalue[1] = value;

        return (tagvalue);
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
    public static String LeParametroArquivo(String arquivo, String token){
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
    // Nome do Método: LeCampoArquivo                                                                                  *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token até o próximo CR/LF        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    public static String LeCampoArquivo(String arquivo, String token) {
        String campo;
        try {
            int indiceToken = arquivo.indexOf(token);
            if (indiceToken > 0) {
                int indiceAposToken = indiceToken + token.length();

                String arquivoAposToken = arquivo.substring(indiceAposToken, arquivo.length());
                int indiceCRLF = arquivoAposToken.indexOf("\n");

                return arquivoAposToken.substring(1, indiceCRLF);
            }
            else {
                return "null";
            }
        } catch (Exception e) {
            return "null";
        }
    }

    //******************************************************************************************************************
    // Nome da Rotina: BytetoInt                                                                                       *
    //                                                                                                                 *
    // Funcao: converte um valor byte para inteiro (conversao sem sinal)                                               *
    //                                                                                                                 *
    // Entrada: valor byte sem sinal de 0 a 255                                                                        *
    //                                                                                                                 *
    // Saida: valor (inteiro) sempre positivo de 0 a 255                                                               *
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
    //                                                                                                                 *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                               *
    //                                                                                                                 *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                             *
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
    //                                                                                                                 *
    // Entrada: dois bytes consecutivos (LSB e MSB) sem sinal de 0 a 255                                               *
    //                                                                                                                 *
    // Saida: valor (inteiro) sempre positivo de 0 a 65535                                                             *
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
    public static String IntToStr4(int valInt) {
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
}
