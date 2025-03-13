/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.service;

import financial_control_api.domain.model.Categoria;
import financial_control_api.exception.EntidadeNaoEncontradaException;
import financial_control_api.repository.CategoriaRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repository;

    public List<Categoria> lista() {
        return repository.findAll();
    }

    public Categoria criarCategoria(@Valid Categoria categoria) {
        return repository.save(categoria);
    }

    public Optional<Categoria> buscarPorId(final Long id) {
        return repository.findById(id);
    }

    public Categoria buscarCategoriaPorId(final Long id) {
        return repository.findById(id).orElseThrow(()->new EntidadeNaoEncontradaException(String.format("Não existe Categoria com id %d", id)));
    }
}
