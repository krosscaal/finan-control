/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.service;

import financial_control_api.domain.dto.LancamentoDTO;
import financial_control_api.domain.model.Categoria;
import financial_control_api.domain.model.Lancamento;
import financial_control_api.domain.model.Pessoa;
import financial_control_api.exception.BusinessException;
import financial_control_api.exception.EntidadeNaoEncontradaException;
import financial_control_api.exception.EntidadeemUsoException;
import financial_control_api.repository.filter.LancamentoFilter;
import financial_control_api.repository.LancamentoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PessoaService pessoaService;

    public List<Lancamento> buscarTodos() {
        return repository.findAll();
    }

    public List<Lancamento> buscarPorFiltro(LancamentoFilter filter) {
        return repository.buscarLancamentoFiltro(filter);
    }

    public List<Lancamento> buscarPorFiltroParams(final String descricao, final LocalDate dataVencimentoDe, final LocalDate dataVencimentoAte) {
        return repository.buscarLancamentoFiltroParams(descricao, dataVencimentoDe, dataVencimentoAte);
    }

    public Page<Lancamento> buscarPorFiltroPaginado(LancamentoFilter filter, Pageable pageable) {
        return repository.buscarLancamentoFiltroPaginado(filter, pageable);
    }

    public Lancamento buscarLancamentoPorId(final Long id) {
        return repository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Não existe Lancamento com id %d", id)));
    }

    public Lancamento novoLancamento(@Valid LancamentoDTO dto) {
        Lancamento lancamentoNovo = montarLancamento(dto);
        return repository.save(lancamentoNovo);
    }

    private Lancamento montarLancamento(final LancamentoDTO dto) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(dto.getCodigoCategoria());
        Pessoa pessoa = pessoaService.buscarPessoaPorId(dto.getCodigoPessoa());
        if (!pessoa.getAtivo()) {
            throw new BusinessException("Não é permitido novo lançamento com Pessoa inativa");
        }
        return new Lancamento(
                dto.getDescricao(),
                dto.getDataVencimento(),
                dto.getDataPagamento(),
                dto.getValor(),
                dto.getObservacao(),
                dto.getTipo(),
                categoria,
                pessoa);
    }
    public void apagarLancamento(final Long id){
        try {
            buscarLancamentoPorId(id);
            repository.deleteById(id);

        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeemUsoException(String.format("Lançamento com id %d, não pode ser removido pois está em uso", id));
        }
    }

    public Page<Lancamento> buscarTodosPaginado(Pageable pageable) {

        return new PageImpl<>(repository.findAll(pageable).stream().toList(), pageable, repository.findAll().size());
    }
}
