package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.StringTokenizer;

@Component
public class Inicia {

    private static boolean OpLocal;
    private static boolean Verbose;
    private static String EndIpConc;

    public static boolean isOpLocal() {
        return OpLocal;
    }

    public static boolean isVerbose() {
        return Verbose;
    }

    public static String getEndIpConc() {
        return EndIpConc;
    }

    @PostConstruct
    public void ProcedimentosInicializacao() {

        String ArquivoConf = SupService.BuscaArquivoConfiguracao();

        StringTokenizer parseArqConf = new StringTokenizer(ArquivoConf);
        String modoOp = parseArqConf.nextToken();
        String verbose = parseArqConf.nextToken();
        String endIp = parseArqConf.nextToken();

        if (modoOp.equals("local")) {
            OpLocal = true;
        }
        else {
            OpLocal = false;
        }

        if (verbose.equals("true")) {
            Verbose = true;
        }
        else {
            Verbose = false;
        }

        EndIpConc = endIp;

        System.out.println("");
        SupService.Terminal("Procedimentos de Inicialização Completados", true, true);
        System.out.println("Modo Op: " + modoOp + " - Verbose: " + verbose + " - End IP: " + endIp);

    }
}
