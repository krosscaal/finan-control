/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.resource;

import financial_control_api.model.Categoria;
import financial_control_api.service.CategoriaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Categoria> addCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSaved = service.criarCategoria(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{id}").buildAndExpand(categoriaSaved.getId()).toUri();
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
