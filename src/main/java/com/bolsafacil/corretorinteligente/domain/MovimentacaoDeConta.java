package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.TipoMovimentacao;

/**
 * MovimentacaoDeConta
 */
public interface MovimentacaoDeConta {
    public long getId();
    public BigDecimal getValorMovimentado();
    public BigDecimal getQuantidadeDeAcoesMovimentada();
    public String getEmpresaDaAcaoMovimentada();
    public LocalDateTime getDataMovimentacao();
    public TipoMovimentacao getTipoMovimentacao();
    public ContaPessoal getContaMovimentada();
    public AcaoObservada getAcaoObservada();
    public void setAcaoObservada(AcaoObservada acao);
}
