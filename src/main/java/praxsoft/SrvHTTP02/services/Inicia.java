package praxsoft.SrvHTTP02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class Inicia {

    @Autowired
    private SupService supService;

    @PostConstruct
    public void ProcedimentosInicializacao() {

        System.out.println("");
        System.out.println("Procedimentos de Inicialização Servidor HTTP 02 V 1.0");

            supService.LeArquivoConfiguracao();

        }

}
