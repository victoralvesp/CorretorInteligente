package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;

/**
 * RegrasDeNegociacaoService
 */
public interface RegrasDeNegociacaoService {

    public Collection<?> aplicarRegrasDeNegociacao(ObservacoesDeAcao monitoramento);
}