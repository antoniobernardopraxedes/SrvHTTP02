package praxsoft.SrvHTTP02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.StringTokenizer;

@Component
public class Inicia {

    @Autowired
    private SupService supService;
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

    @PostConstruct
    public void ProcedimentosInicializacao() {

        System.out.println("");
        System.out.println("Procedimentos de Inicialização Servidor HTTP 02 V 1.0");

            LeArquivoConfiguracao();

    }

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
            ArquivoConf = arquivo.LeArquivoTxt(caminho, nomeArquivo);

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
            Auxiliar.Terminal("Arquivo de Configuração nao encontrado.", false);
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

}
