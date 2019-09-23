package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;

/**
 * Conta
 */
public interface Conta {

    public BigDecimal getSaldoAtual();
    public void registrarMovimentacoes(MovimentacaoDeConta... movimentacoes);
}