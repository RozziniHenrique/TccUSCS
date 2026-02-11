package uscs.STEFER.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErros {
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(new DadosErroMensagem(ex.getMessage()));
    }

    private record DadosErroMensagem(String mensagem) {
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro404(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new DadosErroMensagem("Formato de data inválido. Use o padrão: yyyy-MM-ddTHH:mm"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }
}
