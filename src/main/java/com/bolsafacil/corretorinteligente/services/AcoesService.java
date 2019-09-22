package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;

import javassist.NotFoundException;

/**
 * MonitorService
 */
public interface AcoesService {
	public ObservacoesDeAcao registrarObservacaoDeAcoes(ObservacoesDeAcao monitoramento);
	public Collection<ObservacoesDeAcao> listarObservacoesRealizadas();
	public ObservacoesDeAcao buscar(long id) throws NotFoundException;
}