/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package financial_control_api.repository;

import financial_control_api.domain.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository  extends JpaRepository<Lancamento, Long> {
}
