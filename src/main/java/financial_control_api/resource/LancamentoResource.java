/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.resource;

import financial_control_api.domain.dto.LancamentoDTO;
import financial_control_api.domain.model.Lancamento;
import financial_control_api.event.RecursoCriadoEvent;
import financial_control_api.repository.filter.LancamentoFilter;
import financial_control_api.service.LancamentoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @Transactional
    @PostMapping
    public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody LancamentoDTO dto, HttpServletResponse response) {
        Lancamento lancamentoPersisted = service.novoLancamento(dto);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoPersisted.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoPersisted);
    }

    @GetMapping("/lista-filtro-param")
    public ResponseEntity<?> buscarPorFiltroParam(
            String descricao,
            LocalDate dataVencimentoDe,
            LocalDate dataVencimentoAte) {

        return ResponseEntity.ok(service.buscarPorFiltroParams(descricao, dataVencimentoDe, dataVencimentoAte));
    }

    @GetMapping("/lista-filtro")
    public ResponseEntity<?> buscarPorFiltro(LancamentoFilter filter) {
        return ResponseEntity.ok(service.buscarPorFiltro(filter));
    }

    @GetMapping("/lista-filtro-paginado")
    public Page<Lancamento> buscarPorFiltroPaginado(LancamentoFilter filter, Pageable pageable) {
        return service.buscarPorFiltroPaginado(filter, pageable);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarLancamento(@PathVariable("id") final Long id) {
        service.apagarLancamento(id);
    }

    @GetMapping("/lista-paginado")
    public Page<Lancamento> buscarTodosPaginado(Pageable pageable) {
        return service.buscarTodosPaginado(pageable);
    }

    @GetMapping("/lista-paginado-v2")
    public ResponseEntity<Page<Lancamento>> buscarTodosPaginadoV2(@PageableDefault(sort = "descricao", direction = Sort.Direction.ASC, page = 0, size = 3) Pageable pageable) {
        return ResponseEntity.ok(service.buscarTodosPaginado(pageable));
    }
}
