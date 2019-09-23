package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

/**
 * MonitoramentosService
 */
public interface MonitoramentosService {

    public Monitoramento incluirMonitoramento(Monitoramento monitoramento);
    public Monitoramento buscarMonitoramento(String empresa);
    public boolean excluirMonitoramento(String empresa);
    public Collection<Monitoramento> listarMonitoramentos();
}