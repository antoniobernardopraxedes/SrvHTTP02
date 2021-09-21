package praxsoft.SrvHTTP02.services;

import org.springframework.stereotype.Service;

@Service
public class VlglService {

    public static String[] BuscaUsuario(String userName) {
        String[] dadosUsuario = new String[2];

        switch (userName) {
            case ("Isis Dias Vieirs") :
                dadosUsuario[0] = "0001";
                dadosUsuario[1] = "Isis Dias Vieira";
                break;

            case ("Lucca Borges") :
                dadosUsuario[0] = "0002";
                dadosUsuario[1] = "Lucca Borges C. Dias";
                break;

            case ("Sofia Dias") :
                dadosUsuario[0] = "0003";
                dadosUsuario[1] = "Sofia Borges C. Dias";
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
