package com.bolsafacil.corretorinteligente.repositorios;

import com.bolsafacil.corretorinteligente.entities.ContaDataEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

/**
 * ContasDataRepository
 */
@Component
public interface ContasDataRepository extends CrudRepository<ContaDataEntity, Long>{

    public ContaDataEntity findByEmail(String email);
    
}