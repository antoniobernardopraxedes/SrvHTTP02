package praxsoft.SrvHTTP02.services;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//**********************************************************************************************************************
//                                                                                                                     *
// Nome da Classe: Arquivo                                                                                             *
//                                                                                                                     *
// Atributos: Se o Arquivo Existe (boolean), Tamanho em Caracteres ou Bytes (int), Tipo do Arquivo (código MIME)       *
//                                                                                                                     *
// Métodos: Lê arquivo texto (sequência de caracteres), Lê arquivo binário (sequência de bytes), escreve               *
//          arquivo texto                                                                                              *
//                                                                                                                     *
//**********************************************************************************************************************
//
public class Arquivo {

    public static void EscreveArqTxt(String caminho, String nomeArquivo, boolean b) {
    }

    boolean Existe(String Caminho, String NomeArquivo) {
        File Arquivo = new File(Caminho + NomeArquivo);
        return (Arquivo.exists());
    }

    int Tamanho(String Caminho, String NomeArquivo) {
        File Arquivo = new File(Caminho + NomeArquivo);
        return ((int)Arquivo.length());
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
    public static String LeArquivoTxt(String caminho, String nomeArquivo) {

        File Arquivo = new File(caminho + nomeArquivo);
        String arquivoLido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(Arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                arquivoLido = arquivoLido + linha + "\n";
            }
            Auxiliar.Terminal("Lido Arquivo " + nomeArquivo, false);
            return arquivoLido;

        } catch (IOException e) {
            Auxiliar.Terminal("Arquivo " + nomeArquivo + " nao encontrado.", false);
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
    public static byte[] LeArquivoByte(String caminho, String nomeArquivo) {

        try {
            File arquivo = new File(caminho + nomeArquivo);
            FileInputStream arquivoByte = null;
            int numBytesArquivo = (int)arquivo.length();
            byte[] arrayByteArquivo = new byte[numBytesArquivo];

            arquivoByte = new FileInputStream(arquivo);
            arquivoByte.read(arrayByteArquivo);

            Auxiliar.Terminal("Lido Arquivo " + nomeArquivo, false);
            arquivoByte.close();
            return arrayByteArquivo;

        } catch (IOException e) {
            Auxiliar.Terminal("Arquivo " + nomeArquivo + " nao encontrado.", false);
            byte[] arrayErro = new byte[0];
            return arrayErro;
        }

    }

    //byte[] LeByte(String Caminho, String NomeArquivo) throws IOException {
    //    File Arquivo = new File(Caminho + NomeArquivo);
    //    FileInputStream ArquivoLido = null;
    //    int TamArquivo = (int)Arquivo.length();
    //    byte[] DadosArquivo = new byte[TamArquivo];

    //    try {
    //        ArquivoLido = new FileInputStream(Arquivo);
    //        ArquivoLido.read(DadosArquivo);
    //    } finally {
    //        if (ArquivoLido != null)
    //            ArquivoLido.close();
    //    }
    //    return DadosArquivo;
    //}

    //******************************************************************************************************************
    // Nome do Método: EscreveArqTxt()                                                                                 *
    //	                                                                                                               *
    // Funcao: escreve um arquivo texto                                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do caminho e do arquivo e texto a ser escrito no arquivo                             *
    //                                                                                                                 *
    // Saida: = 1 leu arquivo / = 0 falha ao ler o arquivo                                                             *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    public static boolean EscreveArqTxt(String Caminho, String NomeArquivo, String Texto, boolean Verbose) {

        try {
            PrintWriter out = new PrintWriter(new FileWriter(Caminho + NomeArquivo));
            out.write(Texto);
            out.close();
            return (true);
        } catch (IOException e) {
            if (Verbose) {
                System.out.print("Erro ao escrever arquivo texto ");
            }
            return (false);
        }
    }

    String Tipo(String NomeArquivo) {
        String tipo = "";

        if (NomeArquivo.endsWith(".htm")  ||  NomeArquivo.endsWith(".html")) {
            tipo = "text/html";
        }

        if (NomeArquivo.endsWith(".js")) {
            tipo = "text/javascript";
        }

        if (NomeArquivo.endsWith(".css")) {
            tipo = "text/css";
        }

        if (NomeArquivo.endsWith(".jpg")  ||  NomeArquivo.endsWith(".jpeg")) {
            tipo = "image/jpeg";
        }

        if (NomeArquivo.endsWith(".gif")) {
            tipo = "image/gif";
        }

        if (NomeArquivo.endsWith(".png")) {
            tipo = "image/png";
        }

        if (NomeArquivo.endsWith(".bmp")) {
            tipo = "image/bmp";
        }

        if (NomeArquivo.endsWith(".txt")) {
            tipo = "text/plain";
        }

        return(tipo);
    }

}
