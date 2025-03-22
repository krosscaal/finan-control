/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.service;

import financial_control_api.domain.model.Usuario;
import financial_control_api.exception.EntidadeNaoEncontradaException;
import financial_control_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Optional<Usuario> buscarporId(final Long id) {
        Optional<Usuario> optionalUsuario = repository.findById(id);
        return optionalUsuario;
    }

    public Usuario buscarUsuarioPorId(final Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Usuário com id %d não existe", id)));
    }

    public Usuario buscarPorEmail(final String email) {
        return repository.findUsuarioByEmail(email).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Usuário com e-mail: %s não existe", email)));
    }
}
