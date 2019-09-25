package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeNegociacao;

/**
 * RegrasDeNegociacaoService
 */
public interface RegrasDeNegociacaoService {
    public Collection<RegraDeNegociacao> getRegrasDeNegociacao();
    public Collection<? extends MovimentacaoDeConta> aplicarRegrasDeNegociacao(AcaoObservada acaoObservada);
}