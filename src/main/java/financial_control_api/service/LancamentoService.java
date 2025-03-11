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
import financial_control_api.repository.LancamentoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
