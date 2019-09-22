package com.bolsafacil.corretorinteligente.domain.regrasdenegociacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.movimentacoes.MovimentacaoDeVendaDeAcoes;

/**
 * RegraDeVenda
 */
public class RegraDeVenda implements RegraDeNegociacao {

    private static final RoundingMode modoArredondamento = RoundingMode.DOWN;

    private final BigDecimal quantidadeDeAcoesDisponivel;
    private final Monitoramento monitoramentoDaRegra;

    public RegraDeVenda(Monitoramento monitoramentoDaRegra, BigDecimal quantidadeDeAcoesDisponivel) {
        this.quantidadeDeAcoesDisponivel = quantidadeDeAcoesDisponivel;
        this.monitoramentoDaRegra = monitoramentoDaRegra;
    }


    @Override
    public MovimentacaoDeConta aplicarRegra(AcaoObservada acaoObservada) {
        var precoVendaObservado = acaoObservada.getPrecoVenda();

        var empresaObservaIgualAMonitorada = monitoramentoDaRegra.getEmpresa().equals(acaoObservada.getEmpresa());
        if (empresaObservaIgualAMonitorada && estaAcimaDoDesejado(precoVendaObservado)) {
            var valorTotalVendido = quantidadeDeAcoesDisponivel.multiply(precoVendaObservado).setScale(2, modoArredondamento);
            var dataDaVenda = DefinicoesDoServidor.getDataAtual();
            var movimentacaoDeCompra = new MovimentacaoDeVendaDeAcoes(valorTotalVendido, dataDaVenda);

            return movimentacaoDeCompra;
        } else {
            return null;
        }

    }
    private boolean estaAcimaDoDesejado(BigDecimal precoCompraObservado) {
        var precoDesejado = monitoramentoDaRegra.getPrecoCompra();
        return precoCompraObservado.compareTo(precoDesejado) <= 0;
    }

    
}