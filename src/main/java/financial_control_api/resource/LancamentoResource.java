/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.resource;

import financial_control_api.domain.dto.LancamentoDTO;
import financial_control_api.domain.model.Lancamento;
import financial_control_api.event.RecursoCriadoEvent;
import financial_control_api.service.LancamentoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

    @Autowired
    private LancamentoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/lista")
    public ResponseEntity<?> getListaLancamento() {
        return !service.buscarTodos().isEmpty()? ResponseEntity.ok(service.buscarTodos()) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarPorId(@PathVariable final Long id) {
        return ResponseEntity.ok(service.buscarLancamentoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody LancamentoDTO dto, HttpServletResponse response) {
        Lancamento lancamentoPersisted = service.novoLancamento(dto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoPersisted.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoPersisted);
    }
}
