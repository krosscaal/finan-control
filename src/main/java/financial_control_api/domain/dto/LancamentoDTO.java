/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import financial_control_api.domain.enums.TipoLancamento;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoDTO {
    @NotNull
    @NotBlank
    @Size(max = 150)
    private String descricao;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;

    @NotNull
    @Positive()
    private BigDecimal valor;

    @Size(max = 100)
    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    //@Size(max = 100)
    private TipoLancamento tipo;

    @JsonAlias("codigo_categoria")
    @NotNull
    private Long codigoCategoria;

    @JsonProperty("codigo_pessoa")
    @NotNull
    private Long codigoPessoa;

    public LancamentoDTO() {
    }

    public LancamentoDTO(String descricao, LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor, String observacao, TipoLancamento tipo, Long codigoCategoria, Long codigoPessoa) {
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.observacao = observacao;
        this.tipo = tipo;
        this.codigoCategoria = codigoCategoria;
        this.codigoPessoa = codigoPessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Long getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Long codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }
}
