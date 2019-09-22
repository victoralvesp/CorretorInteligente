package com.bolsafacil.corretorinteligente.repositorios;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;

/**
 * MonitoramentosRepository
 */
public interface ObservacoesDeAcaoRepository {

    public ObservacoesDeAcao salvar(ObservacoesDeAcao monitoramento);

    public Collection<ObservacoesDeAcao> listar();

    public ObservacoesDeAcao buscar(long id);
}