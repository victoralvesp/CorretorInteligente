package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;

import javassist.NotFoundException;

/**
 * MonitorService
 */
public interface AcoesService {
	public AcaoObservada registrarObservacaoDeAcoes(AcaoObservada observacao);
	public Collection<AcaoObservada> listarObservacoesRealizadas();
	public AcaoObservada buscar(long id) throws NotFoundException;
}