package praxsoft.SrvHTTP02.services;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Arquivo {

    private static boolean opLocal = false;
    private static boolean verbose = false;
    private static String endIpConc = "192.168.0.170";
    private static String diretorioBd = "/home/bernardo/bd/";
    private static String diretorioRecursos = "recursos/";

    private static int numMaxUsuarios = 5;
    private static int numUsuarios = 3;
    private static String[] nomeUsuario = {"usuario=1", "Ingrid", "Guto", "Bernardo", "usuario=2"};
    private static String[] senhaUsuario = {"s3nh4=2", "moqueca", "270672", "cl030379", "s3nh4=1" };

    public static boolean isOpLocal() { return opLocal; }

    public static boolean isVerbose() { return verbose; }

    public static String getEndIpConc() { return endIpConc; }

    public static String getDiretorioBd() { return diretorioBd; }

    public static String getDiretorioRecursos() { return diretorioRecursos; }

    public static int getNumUsuarios() { return numUsuarios; }

    public static String getNomeUsuario(int i) { return nomeUsuario[i]; }

    public static String getSenhaUsuario(int i) { return senhaUsuario[i]; }


    //******************************************************************************************************************
    // Nome do Método: LeConfiguracao()                                                                                *
    //	                                                                                                               *
    // Funcao: lê o arquivo de configuração e carrega nos atributos                                                    *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static boolean LeConfiguracao() {
        boolean resultado = false;
        String caminho = "recursos/";
        String nomeArquivo = "srvhttp02.cnf";

        String arquivoConf = Arquivo.LeTexto(caminho, nomeArquivo);

        if (arquivoConf != null) {

            opLocal = false;
            if (LeParametro(arquivoConf, "ModoOp:").equals("local")) { opLocal = true; }

            verbose = false;
            if (LeParametro(arquivoConf, "Verbose:").equals("true")) { verbose = true; }

            endIpConc = LeParametro(arquivoConf, "EndIpConcArduino:");
            diretorioBd = LeParametro(arquivoConf, "DiretorioBD:");
            diretorioRecursos = LeParametro(arquivoConf, "DiretorioRecursos:");
            resultado = true;
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: LeUsuarios()                                                                                    *
    //	                                                                                                               *
    // Funcao: lê o arquivo de usuarios e carrega nos atributos                                                        *
    //                                                                                                                 *
    // Entrada: não tem                                                                                                *
    //                                                                                                                 *
    // Saida: não tem                                                                                                  *
    //******************************************************************************************************************
    //
    public static boolean LeUsuarios() {
        boolean resultado = false;
        String caminho = "recursos/";
        String nomeArquivo = "srvhttp02.usr";

        String arquivoConf = Arquivo.LeTexto(caminho, nomeArquivo);

        if (arquivoConf != null) {
            numUsuarios = Integer.parseInt(LeParametro(arquivoConf, "NumUsuarios:"));
            if ((numUsuarios > 0) && (numUsuarios < numMaxUsuarios)) {
                for (int i = 1; i <= numUsuarios; i++) {
                    nomeUsuario[i] = LeParametro(arquivoConf, "NomeUsuario" + i + ":");
                }
                resultado = true;
            }
        }
        return resultado;
    }

    //******************************************************************************************************************
    // Nome do Método: LeTexto                                                                                         *
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
            return arquivoLido;

        } catch (IOException e) {
            return null;
        }
    }

    //******************************************************************************************************************
    // Nome do Método: LeByte                                                                                          *
    //	                                                                                                               *
    // Funcao: lê um arquivo binário (sequência de bytes) do diretório recursos dentro do diretŕorio principal do      *
    //         servidor.                                                                                               *
    //                                                                                                                 *
    // Entrada: string com o caminho do arquivo e string com o nome do arquivo                                         *
    //                                                                                                                 *
    // Saida: array com a sequência de bytes do arquivo lido. Se ocorrer falha na leitura, o método retorna null.      *
    //******************************************************************************************************************
    //
    public static byte[] LeByte(String caminho, String nomeArquivo) {

        try {
            File arquivo = new File(caminho + nomeArquivo);
            FileInputStream arquivoByte = null;
            int numBytesArquivo = (int)arquivo.length();
            byte[] arrayByteArquivo = new byte[numBytesArquivo];

            arquivoByte = new FileInputStream(arquivo);
            arquivoByte.read(arrayByteArquivo);
            arquivoByte.close();
            return arrayByteArquivo;

        } catch (IOException e) {
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

    //******************************************************************************************************************
    // Nome do Método: Existe                                                                                          *
    //	                                                                                                               *
    // Funcao: verifica se o arquivo existe                                                                            *
    //                                                                                                                 *
    // Entrada: string com o caminho e string com o nome do arquivo                                                    *
    //                                                                                                                 *
    // Saida: = boolean - true se o arquivo existe                                                                     *
    //******************************************************************************************************************
    //
    public static boolean Existe(String Caminho, String NomeArquivo) {
        File Arquivo = new File(Caminho + NomeArquivo);
        return (Arquivo.exists());
    }

    //******************************************************************************************************************
    // Nome do Método: Apaga                                                                                           *
    //	                                                                                                               *
    // Funcao: apaga um arquivo                                                                                        *
    //                                                                                                                 *
    // Entrada: string com o caminho e string com o nome do arquivo                                                    *
    //                                                                                                                 *
    // Saida: = boolean - true se a operação foi realizada                                                             *
    //******************************************************************************************************************
    //
    public static boolean Apaga(String Caminho, String NomeArquivo) {
        File arquivo = new File(Caminho + NomeArquivo);
        return (arquivo.delete());
    }

    //******************************************************************************************************************
    // Nome do Método: Tipo()                                                                                          *
    //	                                                                                                               *
    // Funcao: verifica o tipo do arquivo pela extensão                                                                *
    //                                                                                                                 *
    // Entrada: string com o nome do arquivo                                                                           *
    //                                                                                                                 *
    // Saida: = string com o tipo do arquivo                                                             *
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
    // Nome do Método: LeParametro                                                                                     *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o parâmetro que está após o token                        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    public static String LeParametro(String arquivo, String token){
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
    // Nome do Método: LeCampo                                                                                         *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token até o próximo CR/LF        *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o parâmetro lido após o token                                                                 *
    //******************************************************************************************************************
    //
    public static String LeCampo(String arquivo, String token) {
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
    // Nome do Método: LeCampoHTML                                                                                     *
    //                                                                                                                 *
    // Funcao: procura um token em um arquivo texto e retorna o campo que está após o token entre os caracteres < e >  *
    //                                                                                                                 *
    // Entrada: string com o arquivo texto e string com o token                                                        *
    //                                                                                                                 *
    // Saida: string com o campo lido após o token                                                                     *
    //******************************************************************************************************************
    //
    public static String LeCampoHTML(String arquivo, String token) {
        String campo = null;
        int fimArquivo = arquivo.length();
        int indiceToken = arquivo.indexOf(token);
        if (indiceToken > 0) {
            int indiceAposToken = indiceToken + token.length();
            for (int i = indiceAposToken; i < fimArquivo; i++) {
                if (arquivo.charAt(i) == '<') {
                    //System.out.println("i = " + i);
                    for (int k = i + 1; k < fimArquivo; k++) {
                        if (arquivo.charAt(k) == '>') {
                            //System.out.println("k = " + k);
                            campo = arquivo.substring(i, k + 1);
                            //System.out.println("Campo: " + campo);
                            k = fimArquivo;
                            i = fimArquivo;
                        }
                    }
                }
            }
        }
        return campo;
    }

    public static void MostraDadosConfiguracao() {
        if (verbose) {
            System.out.println("\nInformações do arquivo de configuração");
            if (opLocal) {
                System.out.println("Modo de Operação Local");
            } else {
                System.out.println("Modo de Operação Remoto (Nuvem)");
            }
            System.out.println("Endereço IP do Concentrador: " + endIpConc);
            System.out.println("Diretorio do banco de dados: " + diretorioBd);
            System.out.println("Diretorio de recursos: " + diretorioRecursos);
            System.out.println("");
        }
    }

    public static void MostraUsuarios() {
        if (verbose) {
            System.out.print("\nInformações dos usuários: ");
            System.out.println(numUsuarios + " usuários");
            for (int i = 1; i <= numUsuarios; i++) {
                System.out.println("Usuário " + i + ": " + nomeUsuario[i] + " - Senha: " + senhaUsuario[i]);
            }
            System.out.println("");
        }
    }

}
