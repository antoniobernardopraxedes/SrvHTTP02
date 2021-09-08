package praxsoft.SrvHTTP02.services.exceptions;

public class ArquivoNaoEncontradoException extends RuntimeException {

    public ArquivoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ArquivoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
