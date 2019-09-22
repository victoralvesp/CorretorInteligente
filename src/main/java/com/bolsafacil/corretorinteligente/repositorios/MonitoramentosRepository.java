package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

/**
 * MonitoramentosRepository
 */
public interface MonitoramentosRepository {

    public Monitoramento salvar(Monitoramento monitoramento);

    public Collection<Monitoramento> listar();

    public Monitoramento buscar(long id);
}