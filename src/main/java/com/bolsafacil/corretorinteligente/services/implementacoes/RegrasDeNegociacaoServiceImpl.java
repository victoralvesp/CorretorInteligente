package com.bolsafacil.corretorinteligente.services.implementacoes;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeNegociacao;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeVenda;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;

import org.springframework.stereotype.Component;

/**
 * RegrasDeNegociacaoService
 */
@Component
public class RegrasDeNegociacaoServiceImpl implements RegrasDeNegociacaoService {

    private final MonitoramentosService serviceMonitoramentos;

    public RegrasDeNegociacaoServiceImpl(MonitoramentosService repoMonitoramentos) {
        this.serviceMonitoramentos = repoMonitoramentos;
    }

    Collection<RegraDeNegociacao> regrasMonitoradas;

    @Override
    public Collection<RegraDeNegociacao> getRegrasDeNegociacao() {
        if (regrasMonitoradas == null)
            regrasMonitoradas = definirRegrasMonitoradas();
        return regrasMonitoradas;
    }

    private Collection<RegraDeNegociacao> definirRegrasMonitoradas() {
        var monitoramentos = serviceMonitoramentos.listarMonitoramentos();
        return monitoramentos.stream()
                 .flatMap(mnt -> criarRegra(mnt))
                 .collect(toList());
    }

    private Stream<RegraDeNegociacao> criarRegra(Monitoramento monitoramento) {
        if(monitoramento == null) {
            return Stream.of();
        }
        
        var contaDoMonitoramento = monitoramento.getConta();

        var regrasCriadas = new ArrayList<RegraDeNegociacao>();

        var precoCompraMonitoramento = monitoramento.getPrecoCompra();
        if (precoCompraMonitoramento != null && precoCompraMonitoramento.compareTo(BigDecimal.ZERO) > 0) {
            var saldoDisponivelNaConta = contaDoMonitoramento.getSaldoAtual();
            regrasCriadas.add(new RegraDeCompra(monitoramento, saldoDisponivelNaConta));
        }

        var precoVendaMonitoramento = monitoramento.getPrecoVenda();
		if (precoVendaMonitoramento != null && precoVendaMonitoramento.compareTo(BigDecimal.ZERO) > 0) {
            var empresaDoMonitoramento = monitoramento.getEmpresa();
            var quantidadeDeAcoesDisponivel = contaDoMonitoramento.buscarQuantidadeDeAcoesDisponivel(empresaDoMonitoramento);
            regrasCriadas.add(new RegraDeVenda(monitoramento, quantidadeDeAcoesDisponivel));
        }
        

        return regrasCriadas.stream();
    }

    @Override
    public Collection<? extends MovimentacaoDeConta> aplicarRegrasDeNegociacao(AcaoObservada acaoObservada) {
        if (getRegrasDeNegociacao() == null) {
            return null;
        }
        return regrasMonitoradas.stream()
                        .map(regra -> regra.aplicarRegra(acaoObservada))
                        .collect(toList());
    }

    

    
}