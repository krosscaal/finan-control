/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package financial_control_api.repository;

import financial_control_api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByEmail(@NotNull String email);
}
