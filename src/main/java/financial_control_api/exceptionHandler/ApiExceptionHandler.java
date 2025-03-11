/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.exceptionHandler;

import financial_control_api.exception.BusinessException;
import financial_control_api.exception.EntidadeNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorRequest erro = new ApiErrorRequest(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().format(dataFormatada),
                messageSource.getMessage("error.request", null, LocaleContextHolder.getLocale()),
                ex.getMessage());
        return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrors apiErrors = new ApiErrors(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now().format(dataFormatada),
                messageSource.getMessage("error.parameters", null, LocaleContextHolder.getLocale()),
                criarListaDeErros(ex.getBindingResult()));

        return super.handleExceptionInternal(ex, apiErrors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<ApiErrors.Campo> criarListaDeErros(BindingResult bindingResult) {
        List<ApiErrors.Campo> campos = new ArrayList<>();
        for (FieldError error: bindingResult.getFieldErrors()) {
            String chave = error.getField();
            String valor = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            campos.add(new ApiErrors.Campo(chave, valor));
        }
        return campos;
    }
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        ApiErrorRequest apiErrorRequest = new ApiErrorRequest(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now().format(dataFormatada),
                messageSource.getMessage("error.recurso.not.found", null, LocaleContextHolder.getLocale()),
                ex.getMessage());
        return handleExceptionInternal(ex, apiErrorRequest, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ApiErrorRequest apiErrorRequest = new ApiErrorRequest(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now().format(dataFormatada),
                messageSource.getMessage("error.pessoa.not.active", null, LocaleContextHolder.getLocale()),
                ex.getMessage());
        return handleExceptionInternal(ex, apiErrorRequest, new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

}
