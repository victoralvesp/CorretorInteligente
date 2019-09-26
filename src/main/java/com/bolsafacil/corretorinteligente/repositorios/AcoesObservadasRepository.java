package com.bolsafacil.corretorinteligente.repositorios;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * AcoesObservadasRepository
 */
@Component
public interface AcoesObservadasRepository extends CrudRepository<AcaoObservada, Long> {


}