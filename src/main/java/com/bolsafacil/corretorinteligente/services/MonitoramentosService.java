package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

import javassist.NotFoundException;

/**
 * MonitoramentosService
 */
public interface MonitoramentosService {

    public Monitoramento salvarMonitoramento(Monitoramento monitoramento);
    public Monitoramento buscarMonitoramento(String empresa) throws NotFoundException;
    public boolean excluirMonitoramento(String empresa) throws NotFoundException;
    public Collection<Monitoramento> listarMonitoramentos();
}