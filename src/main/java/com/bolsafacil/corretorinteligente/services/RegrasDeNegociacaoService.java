package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;

/**
 * RegrasDeNegociacaoService
 */
public interface RegrasDeNegociacaoService {

    public Collection<?> aplicarRegrasDeNegociacao(ObservacaoDeAcoes monitoramento);
}