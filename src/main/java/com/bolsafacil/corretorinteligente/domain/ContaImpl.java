package com.bolsafacil.corretorinteligente.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * ContaImpl
 */
public class ContaImpl implements Conta {

    LocalDateTime dataUltimaAtualizacao;

    BigDecimal saldoDisponivelInicial;

    private BigDecimal saldoMovimentacoes;
    private List<MovimentacaoDeConta> movimentacoesRegistradas = new ArrayList<MovimentacaoDeConta>();
    
    public ContaImpl(BigDecimal saldoInicial) {
        saldoDisponivelInicial = saldoInicial;
        saldoMovimentacoes = new BigDecimal("0");
    }

    @Override
    public BigDecimal getSaldoAtual() {
        return saldoDisponivelInicial.add(saldoMovimentacoes);
    }

    @Override
    public void registrarMovimentacoes(MovimentacaoDeConta... movimentacoes) {
        if(movimentacoes == null)
            return;

        for (MovimentacaoDeConta movimentacao : movimentacoes) {
            switch (movimentacao.getTipoMovimentacao()) {
                case COMPRA:
                    subtrairSaldoMovimentacao(movimentacao);
                    break;
                case VENDA:
                    adicionarSaldoMovimentacao(movimentacao);
                default:
                    break;
            }
        }
    }

    private void adicionarSaldoMovimentacao(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.add(movimentacao.getValorMovimentado());
    }

    private void subtrairSaldoMovimentacao(MovimentacaoDeConta movimentacao) {
        movimentacoesRegistradas.add(movimentacao);
        saldoMovimentacoes = saldoMovimentacoes.subtract(movimentacao.getValorMovimentado());
    }

    
}