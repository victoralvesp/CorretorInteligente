package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

import javassist.NotFoundException;

/**
 * MonitoramentosService
 */
public interface MonitoramentosService {

    public Monitoramento salvarMonitoramento(Monitoramento monitoramento, long idConta) throws NotFoundException;
    public Monitoramento buscarMonitoramento(String empresa, Long idConta) throws NotFoundException;
    public boolean excluirMonitoramento(String empresa, Long idConta) throws NotFoundException;
    public Collection<Monitoramento> listarMonitoramentos();
}