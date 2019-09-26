package com.bolsafacil.corretorinteligente.repositorios;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;

import org.springframework.data.repository.CrudRepository;

/**
 * MonitoramentosRepository
 */
public interface ObservacoesDeAcaoRepository extends CrudRepository<AcaoObservada, Long> {


}