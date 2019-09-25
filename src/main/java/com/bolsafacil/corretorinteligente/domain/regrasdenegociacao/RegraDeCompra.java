package com.bolsafacil.corretorinteligente.domain.regrasdenegociacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.Conta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeCompraDeAcoes;

/**
 * RegraDeCompra
 */
public class RegraDeCompra implements RegraDeNegociacao {

    /**
     *
     */

    private static final RoundingMode modoArredondamento = RoundingMode.DOWN;
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

        String empresaDaAcao = acaoObservada.getEmpresa();
        var empresaObservaIgualAMonitorada = monitoramentoDaRegra.getEmpresa().equals(empresaDaAcao);
        if (empresaObservaIgualAMonitorada && estaAbaixoDoDesejado(precoCompraObservado)) {
            var quantidadeDeAcoesAComprar = saldoDisponivel.divide(precoCompraObservado, modoArredondamento).setScale(2, modoArredondamento);
            var dataDaCompra = DefinicoesDoServidor.getDataAtual();
            var valorTotalMovimentado = quantidadeDeAcoesAComprar.multiply(precoCompraObservado)
                                                                 .multiply(new BigDecimal(-1))
                                                                 .setScale(2, modoArredondamento);
            var movimentacaoDeCompra = new MovimentacaoDeCompraDeAcoes(valorTotalMovimentado, dataDaCompra,
                                                                            quantidadeDeAcoesAComprar, empresaDaAcao
                                                                            , getContaDoMonitoramento());

            return movimentacaoDeCompra;
        } else {
            return null;
        }

    }

    private boolean estaAbaixoDoDesejado(BigDecimal precoCompraObservado) {
        var precoDesejado = monitoramentoDaRegra.getPrecoCompra();
        return precoCompraObservado.compareTo(precoDesejado) <= 0;
    }

    private Conta getContaDoMonitoramento() {
        return monitoramentoDaRegra.getConta();
    }
    
}