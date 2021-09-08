package praxsoft.SrvHTTP02.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import praxsoft.SrvHTTP02.domain.Artigo;
import praxsoft.SrvHTTP02.services.ArtigosService;
import praxsoft.SrvHTTP02.services.exceptions.ArquivoNaoEncontradoException;

@RestController
public class ArtigoResources {

    @Autowired
    private ArtigosService artigosService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inicio() {

        return artigosService.buscaIndex();
    }

    @RequestMapping(value = "/artigos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Artigo> listar() {

        Artigo l1 = new Artigo("A Sabedoria dos Sonhos");
        l1.setAutor("Isis Dias Vieira");
        l1.setPublicacao("16/08/2016");
        l1.setConteudo("O artigo fala sobre os sonhos");

        Artigo l2 = new Artigo("O Encontro dos Rios");
        l2.setAutor("Isis Dias Vieira");
        l2.setPublicacao("15/07/2020");
        l2.setConteudo("O artigo fala sobre os Rios de Vila Verde");

        Artigo[] artigos = {l1, l2};

        return Arrays.asList(artigos);
    }

    @RequestMapping(value = "/sair", method = RequestMethod.GET)
    public String sair() {

        //Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        //String usuario = auth1.getName();

        return "Sair da Autenticação de Usuário";
    }
}
