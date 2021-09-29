package praxsoft.SrvHTTP02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.StringTokenizer;

@Component
public class Inicia {

    //@Autowired
    //private SupService supService;

    private static boolean opLocal;
    private static boolean verbose;
    private static String endIpConc;

    private static String nomeUsuarioAdmin1;
    private static String senhaAdmin1;
    private static String nomeUsuarioAdmin2;
    private static String senhaAdmin2;
    private static String nomeUsuarioAdmin3;
    private static String senhaAdmin3;

    public static boolean isOpLocal() {
        return opLocal;
    }
    public static boolean isVerbose() {
        return verbose;
    }
    public static String getEndIpConc() {
        return endIpConc;
    }

    public static String getNomeUsuarioAdmin1() {
        return nomeUsuarioAdmin1;
    }

    public static String getSenhaAdmin1() {
        return senhaAdmin1;
    }

    public static String getNomeUsuarioAdmin2() {
        return nomeUsuarioAdmin2;
    }

    public static String getSenhaAdmin2() {
        return senhaAdmin2;
    }

    public static String getNomeUsuarioAdmin3() {
        return nomeUsuarioAdmin3;
    }

    public static String getSenhaAdmin3() {
        return senhaAdmin3;
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
            ArquivoConf = arquivo.LeTexto(caminho, nomeArquivo);

            String ModoOp = Auxiliar.LeParametroArquivo(ArquivoConf, "ModoOp:");
            String Verbose = Auxiliar.LeParametroArquivo(ArquivoConf, "Verbose:");
            String EndIpConcArduino = Auxiliar.LeParametroArquivo(ArquivoConf, "EndIpConcArduino:");

            nomeUsuarioAdmin1 = Auxiliar.LeParametroArquivo(ArquivoConf, "NomeUsuarioAdmin1:");
            senhaAdmin1 = Auxiliar.LeParametroArquivo(ArquivoConf, "SenhaAdmin1:");
            nomeUsuarioAdmin2 = Auxiliar.LeParametroArquivo(ArquivoConf, "NomeUsuarioAdmin2:");
            senhaAdmin2 = Auxiliar.LeParametroArquivo(ArquivoConf, "SenhaAdmin2:");
            nomeUsuarioAdmin3 = Auxiliar.LeParametroArquivo(ArquivoConf, "NomeUsuarioAdmin3:");
            senhaAdmin3 = Auxiliar.LeParametroArquivo(ArquivoConf, "SenhaAdmin3:");

            if (ModoOp.equals("local")) { opLocal = true; } else { opLocal = false; }
            if (Verbose.equals("true")) { verbose = true; } else { verbose = false; }
            endIpConc = EndIpConcArduino;

            System.out.println("\nLido Arquivo de Configuração\n");
            if (opLocal) { System.out.println("Modo de Operação Local"); }
            else { System.out.println("Modo de Operação Remoto (Nuvem)"); }
            System.out.println("Verbose: " + verbose);
            System.out.println("Endereço IP do Concentrador Arduino: " + endIpConc);
            System.out.println("");
            System.out.println("Administrador Vlgl 1:" + nomeUsuarioAdmin1);
            System.out.println("Senha Administrador Vlgl 1:" + senhaAdmin1);
            System.out.println("Administrador Vlgl 2:" + nomeUsuarioAdmin2);
            System.out.println("Senha Administrador Vlgl 2:" + senhaAdmin2);
            System.out.println("Administrador Vlgl 3:" + nomeUsuarioAdmin3);
            System.out.println("Senha Administrador Vlgl 3:" + senhaAdmin3);
            System.out.println("");

        } catch (Exception e) {
            Auxiliar.Terminal("Arquivo de Configuração nao encontrado.", false);
            lidoArqConf = false;
        }

        return lidoArqConf;
    }

}
