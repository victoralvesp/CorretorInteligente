package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Set;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

/**
 * MonitoramentosRepository
 */
public interface MonitoramentosRepository {

    public Monitoramento incluir(Monitoramento monitoramento);
    public Monitoramento alterarParaExcluido(Monitoramento monitoramento);
    public Set<Monitoramento> listar();
    public Monitoramento buscar(String empresa);
    
}