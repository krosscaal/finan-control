/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package financial_control_api.repository.impl;

import financial_control_api.domain.model.Lancamento;
import financial_control_api.repository.LancamentoRepositoryQueries;
import financial_control_api.repository.filter.LancamentoFilter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Lancamento> buscarLancamentoFiltro(LancamentoFilter filter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);
        //root
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        /* criar as resctrições */
        //predicate
        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        //where descricao like '%valor de filter.getDescricao%

        //usando hasLength no lugar de isEmprty() que está deprecated
        if (StringUtils.hasLength(filter.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + filter.getDescricao().toLowerCase() + "%"));
        }
        if (filter.getDataVencimentoDe() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoDe()));
        }
        if (filter.getDataVencimentoAte() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[0]);
    }

    @Override
    public List<Lancamento> buscarLancamentoFiltroParams(String descricao, LocalDate dataVencimentoDe, LocalDate dataVenciementoAte) {
        LancamentoFilter filter = new LancamentoFilter();
        filter.setDescricao(descricao);
        filter.setDataVencimentoDe(dataVencimentoDe);
        filter.setDataVencimentoAte(dataVenciementoAte);
        return buscarLancamentoFiltro(filter);
    }

    @Override
    public Page<Lancamento> buscarLancamentoFiltroPaginado(LancamentoFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteriaQuery = builder.createQuery(Lancamento.class);
        //root
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        /* criar as resctrições */
        //predicate
        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteriaQuery.where(predicates);

        TypedQuery<Lancamento> query = manager.createQuery(criteriaQuery);
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private long total(LancamentoFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(filter, builder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));
        return manager.createQuery(criteriaQuery).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

}
