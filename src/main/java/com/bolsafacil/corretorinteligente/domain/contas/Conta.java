package com.bolsafacil.corretorinteligente.domain.contas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;

/**
 * Conta
 */
public interface Conta {

    public BigDecimal getSaldoAtual();
    public void registrarMovimentacoes(MovimentacaoDeConta... movimentacoes);
	public LocalDateTime getDataUltimaAtualizacao();
}