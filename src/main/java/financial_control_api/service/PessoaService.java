/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import financial_control_api.exception.EntidadeNaoEncontradaException;
import financial_control_api.exception.EntidadeemUsoException;
import financial_control_api.model.Endereco;
import financial_control_api.model.Pessoa;
import financial_control_api.model.dto.PessoaDTO;
import financial_control_api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.yaml.snakeyaml.events.Event;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;

    public Optional<Pessoa> buscarPorId(final Long id) {
        return repository.findById(id);
    }

    public List<Pessoa> buscarTodos() {
        return repository.findAll();
    }

    public Pessoa criarPessoa(PessoaDTO dto) {
        Pessoa pessoa = montarPessoa(dto);
        pessoa.setAtivo(true);
        return repository.save(pessoa);
    }

    private Pessoa montarPessoa(final PessoaDTO dto) {
        Endereco endereco = new Endereco(
                dto.getEndereco().getLogradouro(),
                dto.getEndereco().getNumero(),
                dto.getEndereco().getComplemento(),
                dto.getEndereco().getBairro(),
                dto.getEndereco().getCep(),
                dto.getEndereco().getCidade(),
                dto.getEndereco().getEstado());
        return new Pessoa(dto.getNome(), endereco, dto.getAtivo());
    }

    public void removerPessoa(final Long id) {
        try {
            buscarPessoaPorId(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeemUsoException(String.format("Pessoa com id %d, não pode ser removida pois está em uso", id));
        }

    }
    private Pessoa buscarPessoaPorId(final Long id) {
        return repository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Não existe Pessoa com id %d", id)));
    }

    public Pessoa atualizarPessoa(final Long id, final PessoaDTO dto) {
        Pessoa pessoaObj = buscarPessoaPorId(id);
        BeanUtils.copyProperties(dto, pessoaObj, "id");
        return repository.save(pessoaObj);
    }

    public Pessoa modificarEndereco(final Long id, Endereco endereco) {
        Pessoa pessoaObj = buscarPessoaPorId(id);
        pessoaObj.getEndereco().setLogradouro(endereco.getLogradouro());
        pessoaObj.getEndereco().setComplemento(endereco.getComplemento());
        pessoaObj.getEndereco().setNumero(endereco.getNumero());
        pessoaObj.getEndereco().setBairro(endereco.getBairro());
        pessoaObj.getEndereco().setCidade(endereco.getCidade());
        pessoaObj.getEndereco().setEstado(endereco.getEstado());
        pessoaObj.getEndereco().setCep(endereco.getCep());
        return repository.save(pessoaObj);
    }

    public Pessoa modificarStatusAtivo(final Long id) {
        Pessoa pessoaObj = buscarPessoaPorId(id);
        pessoaObj.setAtivo(!pessoaObj.getAtivo());
        return repository.save(pessoaObj);
    }

    public Pessoa atualizarPessoaParcial(final Long id, Map<String, Object> campos) {
        Pessoa pessoaObj = buscarPessoaPorId(id);
        merge(campos, pessoaObj);
        return repository.save(pessoaObj);
    }

    private void merge(Map<String, Object> camposOrigem, Pessoa pessoaDestino) {

        /* usando ObjectMapper para mapear a Map<> -> Class destino*/
        ObjectMapper objectMapper = new ObjectMapper();
        Pessoa pessoaOrigem = objectMapper.convertValue(camposOrigem, Pessoa.class);
        System.out.println(pessoaOrigem);

        /* loop do Map<>*/
        camposOrigem.forEach((nomePropiedade, valorPropiedade)->{
            if (nomePropiedade.equals("endereco") && valorPropiedade instanceof Map<?,?>) {
                Map<String, Object> camposEndereco = (Map<String, Object>) valorPropiedade;
                Endereco enderecoOrigem = objectMapper.convertValue(camposEndereco, Endereco.class);
                Endereco enderecoDestino = pessoaDestino.getEndereco();

                camposEndereco.forEach((campoEndereco, valorEndereco) -> {
                    Field field = ReflectionUtils.findField(Endereco.class, campoEndereco);
                    if (field != null) {
                        field.setAccessible(true);
                        Object novoValor = ReflectionUtils.getField(field, enderecoOrigem);
                        if (novoValor != null) {
                            ReflectionUtils.setField(field, enderecoDestino, novoValor);
                        }
                    }
                });


            } else {
                Field field = ReflectionUtils.findField(Pessoa.class, nomePropiedade);
                if (field != null) {
                    field.setAccessible(true); //necessario para acessar atributos private
                    Object novoValor = ReflectionUtils.getField(field, pessoaOrigem);//pegando o valor do Map<>
                    System.out.println(nomePropiedade + " = " + valorPropiedade + " = " + novoValor);
                    if (novoValor != null) {
                        ReflectionUtils.setField(field, pessoaDestino, novoValor);
                    }
                }
            }
        });
    }
}
