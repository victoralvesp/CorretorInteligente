package com.bolsafacil.corretorinteligente.repositorios;

import com.bolsafacil.corretorinteligente.entities.MovimentacaoDataEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * MovimentosDataRepository
 */
@Component
public interface MovimentosDataRepository extends CrudRepository<MovimentacaoDataEntity, Long>{

    
}