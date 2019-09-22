package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;

import javassist.NotFoundException;

/**
 * MonitorService
 */
public interface MonitorService {
	public Monitoramento registrarMonitoramento(Monitoramento monitoramento);
	public Collection<Monitoramento> listarMonitoramentos();
	public Monitoramento buscarMonitoramento(long id) throws NotFoundException;
}