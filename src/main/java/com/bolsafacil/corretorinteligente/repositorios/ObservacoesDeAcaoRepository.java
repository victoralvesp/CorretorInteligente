package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;

/**
 * MonitoramentosRepository
 */
public interface ObservacoesDeAcaoRepository {

    public ObservacaoDeAcoes salvar(ObservacaoDeAcoes monitoramento);

    public Collection<ObservacaoDeAcoes> listar();

    public ObservacaoDeAcoes buscar(long id);
}