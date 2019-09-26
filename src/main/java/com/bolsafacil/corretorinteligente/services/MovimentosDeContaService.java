package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * MovimentosDeContaService
 */
public interface MovimentosDeContaService {

	Collection<? extends MovimentacaoDeConta> salvar(Collection<? extends MovimentacaoDeConta> movimentacoesDeConta, AcaoObservada observacaoGatilho);

    Collection<? extends MovimentacaoDeConta> listar();
}