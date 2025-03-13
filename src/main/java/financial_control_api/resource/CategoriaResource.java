/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.resource;

import financial_control_api.domain.model.Categoria;
import financial_control_api.service.CategoriaService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaService service;

    @GetMapping
    public ResponseEntity<?> getLista() {
        return !service.lista().isEmpty()? ResponseEntity.ok(service.lista()) : ResponseEntity.noContent().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Categoria> addCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSaved = service.criarCategoria(categoria);
        /* Todo substituir por RecursoCriadoEvent*/
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(categoriaSaved.getId()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoriaSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Valid @PathVariable("id") Long id) {
        return service.buscarPorId(id)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }

}
