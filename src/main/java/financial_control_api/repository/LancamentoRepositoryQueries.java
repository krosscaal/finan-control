/*
 * Author: Krossby Adhemar Camacho Alviz
 *
 */

package financial_control_api.repository;

import financial_control_api.domain.model.Lancamento;
import financial_control_api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepositoryQueries {

    List<Lancamento> buscarLancamentoFiltro(LancamentoFilter filter);
    List<Lancamento> buscarLancamentoFiltroParams(String descricao, LocalDate dataVencimentoDe, LocalDate dataVenciementoAte);

    Page<Lancamento> buscarLancamentoFiltroPaginado(LancamentoFilter filter, Pageable pageable);
}
