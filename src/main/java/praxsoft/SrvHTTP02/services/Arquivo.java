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

    public static boolean Existe(String Caminho, String NomeArquivo) {
        File Arquivo = new File(Caminho + NomeArquivo);
        return (Arquivo.exists());
    }

    public static int Tamanho(String Caminho, String NomeArquivo) {
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
    public static String LeTexto(String caminho, String nomeArquivo) {

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

    //******************************************************************************************************************
    // Nome do Método: EscreveTexto()                                                                                  *
    //	                                                                                                               *
    // Funcao: escreve um arquivo texto                                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do caminho e do arquivo e texto a ser escrito no arquivo                             *
    //                                                                                                                 *
    // Saida: = boolean operação realizada = true / operação não realizada = false                                     *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    public static boolean EscreveTexto(String caminho, String nomeArquivo, String conteudo) {
        boolean resultado;
        try {
            PrintWriter out = new PrintWriter(new FileWriter(caminho + nomeArquivo));
            out.write(conteudo);
            out.close();
            resultado = true;
        } catch (IOException e) {

            resultado = false;
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: Tipo()                                                                                          *
    //	                                                                                                               *
    // Funcao: verifica o tipo do arquivo pela extensão                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do arquivo                                                                           *
    //                                                                                                                 *
    // Saida: = string com o tipo do arquivo                                                             *
    //	                                                                                                               *
    //******************************************************************************************************************
    //
    String Tipo(String NomeArquivo) {
        String tipo = null;

        if (NomeArquivo.endsWith(".htm")  ||  NomeArquivo.endsWith(".html")) { tipo = "text/html"; }
        if (NomeArquivo.endsWith(".js")) { tipo = "text/javascript"; }
        if (NomeArquivo.endsWith(".css")) { tipo = "text/css"; }
        if (NomeArquivo.endsWith(".jpg")  ||  NomeArquivo.endsWith(".jpeg")) { tipo = "image/jpeg"; }
        if (NomeArquivo.endsWith(".gif")) { tipo = "image/gif"; }
        if (NomeArquivo.endsWith(".png")) { tipo = "image/png"; }
        if (NomeArquivo.endsWith(".bmp")) { tipo = "image/bmp"; }
        if (NomeArquivo.endsWith(".txt")) { tipo = "text/plain"; }

        return(tipo);
    }

    //******************************************************************************************************************
    // Nome do Método: Renomeia()                                                                                      *
    //	                                                                                                               *
    // Funcao: renomeia um arquivo                                                                                     *
    //                                                                                                                 *
    // Entrada: string com o caminho, string com o nome velho do arquivo e string com o nome novo do arquivo           *
    //                                                                                                                 *
    // Saida: = boolean se a operação foi realizada = true / se não foi realizada = false                              *
    //******************************************************************************************************************
    //
    public static boolean Renomeia(String caminho, String nomeVelho, String nomeNovo) {
        boolean resultado;
        File arquivo1 = new File(caminho + nomeVelho);
        File arquivo2 = new File(caminho + nomeNovo);

        if (arquivo2.exists()) {
            resultado = false;
        }
        else {
            resultado = arquivo1.renameTo(arquivo2);
        }
        return resultado;
    }

}
