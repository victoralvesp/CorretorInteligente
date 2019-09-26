package com.bolsafacil.corretorinteligente.repositorios;

import com.bolsafacil.corretorinteligente.entities.MonitoramentosDataEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * MonitoramentosDataRepository
 */
@Component
public interface MonitoramentosDataRepository extends CrudRepository<MonitoramentosDataEntity, String> {

    
}