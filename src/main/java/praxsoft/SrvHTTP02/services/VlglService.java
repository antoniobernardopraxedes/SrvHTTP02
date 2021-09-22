package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;

@Service
public class VlglService {

    public static String[] BuscaUsuario(String userName) {
        String[] dadosUsuario = new String[3];

        switch (userName) {
            case ("Ingrid") :
                dadosUsuario[0] = "0001";
                dadosUsuario[1] = "admin";
                dadosUsuario[2] = "Ingrid Loyane F. Silva";
                break;

            case ("Bernardo") :
                dadosUsuario[0] = "0002";
                dadosUsuario[1] = "admin";
                dadosUsuario[2] = "Antonio Bernardo Praxedes";
                break;

            case ("IsisDias") :
                dadosUsuario[0] = "0003";
                dadosUsuario[1] = "cliente";
                dadosUsuario[2] = "Isis Dias Vieira";
                break;

            case ("LuccaBorges") :
                dadosUsuario[0] = "0004";
                dadosUsuario[1] = "cliente";
                dadosUsuario[2] = "Lucca Borges Câmara Dias";
                break;

            case ("SofiaDias") :
                dadosUsuario[0] = "0005";
                dadosUsuario[1] = "cliente";
                dadosUsuario[2] = "Sofia Borges Câmara Dias";
                break;
        }
        if (dadosUsuario[0] == null) { dadosUsuario[0] = "null"; }
        if (dadosUsuario[1] == null) { dadosUsuario[1] = "null"; }
        if (dadosUsuario[2] == null) { dadosUsuario[2] = "null"; }

        return dadosUsuario;
    }

}
