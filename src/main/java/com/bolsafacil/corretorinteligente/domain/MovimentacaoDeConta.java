package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;

/**
 * MovimentacaoDeConta
 */
public interface MovimentacaoDeConta {

    public BigDecimal getValorMovimentado();
    public BigDecimal getQuantidadeDeAcoesMovimentada();
    public String getEmpresaDaAcaoMovimentada();
    public LocalDateTime getDataMovimentacao();
    public TipoMovimentacao getTipoMovimentacao();
    public Conta getContaMovimentada();
}
