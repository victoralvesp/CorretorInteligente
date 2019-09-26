package com.bolsafacil.corretorinteligente.repositorios;

import java.util.List;

import com.bolsafacil.corretorinteligente.entities.MonitoramentosDataEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * MonitoramentosDataRepository
 */
@Component
public interface MonitoramentosDataRepository extends CrudRepository<MonitoramentosDataEntity, Long> {

    public List<MonitoramentosDataEntity> findByEmpresa(String empresa);
}