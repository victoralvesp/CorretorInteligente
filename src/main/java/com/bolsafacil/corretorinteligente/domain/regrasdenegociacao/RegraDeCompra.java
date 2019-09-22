package com.bolsafacil.corretorinteligente.domain.regrasdenegociacao;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;

/**
 * RegraDeCompra
 */
public class RegraDeCompra implements RegraDeNegociacao {

    /**
     *
     */

    private static final RoundingMode DOWN = RoundingMode.DOWN;
    /**
     *
     */

    private final BigDecimal saldoDisponivel;
    private final Monitoramento monitoramentoDaRegra;

    public RegraDeCompra(Monitoramento monitoramentoDaRegra, BigDecimal saldoDisponivel) {
        this.monitoramentoDaRegra = monitoramentoDaRegra;
        this.saldoDisponivel = saldoDisponivel;
    }

    @Override
    public MovimentacaoDeConta aplicarRegra(AcaoObservada acaoObservada) {
        var precoCompraObservado = acaoObservada.getPrecoCompra();

        if (monitoramentoDaRegra.getPrecoCompra().compareTo(precoCompraObservado) >= 0) {
            var quantidadeDeAcoesAComprar = saldoDisponivel.divide(precoCompraObservado, DOWN).setScale(2, DOWN);
            var dataDaCompra = DefinicoesDoServidor.getDataAtual();
            var movimentacaoDeCompra = new MovimentacaoDeCompraDeAcoes(quantidadeDeAcoesAComprar, dataDaCompra);

            return movimentacaoDeCompra;
        }   
        else {
            return null;
        }

    
    }

    
}