/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.resource;

import financial_control_api.event.RecursoCriadoEvent;
import financial_control_api.model.Endereco;
import financial_control_api.model.Pessoa;
import financial_control_api.model.dto.PessoaDTO;
import financial_control_api.repository.PessoaRepository;
import financial_control_api.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaService service;
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/lista")
    public ResponseEntity<?> getListaPessoa() {
        return !service.buscarTodos().isEmpty() ? ResponseEntity.ok(service.buscarTodos()) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPessoa(@Valid @PathVariable("id") final Long id) {
        return service.buscarPorId(id)
                .map(pessoa -> ResponseEntity.ok(pessoa))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody PessoaDTO dto, HttpServletResponse response) {
        Pessoa pessoaPersisted = service.criarPessoa(dto);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaPersisted.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaPersisted);

    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarPessoa(@PathVariable("id") Long id) {
        service.removerPessoa(id);
    }

    @Transactional
    @PutMapping("/{id}/endereco")
    public ResponseEntity<Pessoa> modificarEndereco(@PathVariable("id") final Long id, @Valid @RequestBody final Endereco endereco) {
        return ResponseEntity.ok(service.modificarEndereco(id, endereco));

    }

    @Transactional
    @PatchMapping("/{id}/ativo")
    public ResponseEntity<Pessoa> modifcarStatusAtivo(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.modificarStatusAtivo(id), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarPessoa(@PathVariable("id") final Long id, @Valid @RequestBody PessoaDTO pessoaDTO) {
        return ResponseEntity.ok(service.atualizarPessoa(id, pessoaDTO));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable("id") final Long id, @RequestBody Map<String, Object> campos) {
        return ResponseEntity.ok(service.atualizarPessoaParcial(id, campos));

    }
}
