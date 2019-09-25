package com.bolsafacil.corretorinteligente.domain.regrasdenegociacao;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * RegraDeNegociacao
 */
public interface RegraDeNegociacao {

    public MovimentacaoDeConta aplicarRegra(AcaoObservada acaoObservada);
}