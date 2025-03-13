/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.domain.dto;

import financial_control_api.domain.model.Endereco;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PessoaDTO {

    @Size(max = 200)
    @NotNull
    @NotBlank
    private String nome;

    private Endereco endereco;

    @NotNull
    private Boolean ativo;

    public PessoaDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
