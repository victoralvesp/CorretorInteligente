package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;

import javassist.NotFoundException;

/**
 * MonitorService
 */
public interface AcoesService {
	public ObservacaoDeAcoes registrarObservacaoDeAcoes(ObservacaoDeAcoes monitoramento);
	public Collection<ObservacaoDeAcoes> listarObservacoesRealizadas();
	public ObservacaoDeAcoes buscar(long id) throws NotFoundException;
}