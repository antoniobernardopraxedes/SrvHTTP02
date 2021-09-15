package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Component;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

import javax.annotation.PostConstruct;
import java.util.StringTokenizer;

@Component
public class Inicia {

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
        SupService.Terminal("Procedimentos de Inicialização Servidor HTTP 02 V 1.0", true, true);

        try {
            String ArquivoConf = SupService.LeArquivoConfiguracao();
            int IndiceF = ArquivoConf.length() - 1;

            String ModoOp = LeParametroArqConf(ArquivoConf, "ModoOp:");
            String Verbose = LeParametroArqConf(ArquivoConf, "Verbose:");
            String EndIpConcArduino = LeParametroArqConf(ArquivoConf, "EndIpConcArduino:");

            if (ModoOp.equals("local")) {
                opLocal = true;
            } else {
                opLocal = false;
            }

            if (Verbose.equals("true")) {
                verbose = true;
            } else {
                verbose = false;
            }

            endIpConc = EndIpConcArduino;

            System.out.println("\nLido Arquivo de Configuração\n");
            System.out.println("Modo de Operação Local: " + opLocal);
            System.out.println("Verbose: " + verbose);
            System.out.println("Endereço IP do Concentrador Arduino: " + endIpConc);
            System.out.println("");
        } catch (ArquivoNaoEncontradoException e) {
            System.out.println("\nArquivo de Configuração não Encontrado\n");
        }

        }

        private String LeParametroArqConf (String arquivo, String token){
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
