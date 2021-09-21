package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;

@Service
public class VlglService {

    public static String[] BuscaUsuario(String userName) {
        String[] dadosUsuario = new String[2];

        switch (userName) {
            case ("IsisDias") :
                dadosUsuario[0] = "0001";
                dadosUsuario[1] = "Isis Dias Vieira";
                break;

            case ("LuccaBorges") :
                dadosUsuario[0] = "0002";
                dadosUsuario[1] = "Lucca Borges Câmara Dias";
                break;

            case ("SofiaDias") :
                dadosUsuario[0] = "0003";
                dadosUsuario[1] = "Sofia Borges Câmara Dias";
                break;
        }
        if (dadosUsuario[0] == null) {
            dadosUsuario[0] = "null";
        }
        if (dadosUsuario[1] == null) {
            dadosUsuario[1] = "null";
        }

        return dadosUsuario;
    }

}
