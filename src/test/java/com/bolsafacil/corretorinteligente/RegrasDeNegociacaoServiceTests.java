package com.bolsafacil.corretorinteligente;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.domain.MovimentacaoDeConta;
import com.bolsafacil.corretorinteligente.domain.contas.ContaDeAcao;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeCompra;
import com.bolsafacil.corretorinteligente.domain.regrasdenegociacao.RegraDeVenda;
import com.bolsafacil.corretorinteligente.fixtures.FixtureContas;
import com.bolsafacil.corretorinteligente.fixtures.FixtureMonitoramentos;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.RegrasDeNegociacaoService;
import com.bolsafacil.corretorinteligente.services.implementacoes.RegrasDeNegociacaoServiceImpl;

import org.assertj.core.api.Condition;
import org.junit.Test;

/**
 * RegrasDeNegociacaoServiceTests
 */
public class RegrasDeNegociacaoServiceTests {

    @Test
    public void deveCriarUmaRegraDeCompraParaUmMonitoramentoComPrecoDeCompraPositivoSalvo() {
        // Arrange
        var fixtureDB = new FixtureMonitoramentos();
        var precoCompra = new BigDecimal("10.00");
        var monitoramentoComCompra = fixtureDB.criarNovoMonitoramento(precoCompra, null);
        fixtureDB.preencherMonitoramentos(monitoramentoComCompra);
        var repo = fixtureDB.criarMockMonitoramentosRepository();
        var service = criarService(repo);

        //Act
        var regras = service.getRegrasDeNegociacao();

        //Assert
        var ehRegraDeCompra = new Condition<>(regra -> regra.getClass() == RegraDeCompra.class, "É regra de compra");
        assertThat(regras).extracting(regra -> regra).areExactly(1, ehRegraDeCompra);
    }
    @Test
    public void deveCriarUmaRegraDeVendaParaUmMonitoramentoComPrecoDeVendaPositivoSalvo() {
        // Arrange
        var fixtureDB = new FixtureMonitoramentos();
        var precoVenda = new BigDecimal("10.00");
        var monitoramentoComVenda = fixtureDB.criarNovoMonitoramento(null, precoVenda);
        fixtureDB.preencherMonitoramentos(monitoramentoComVenda);
        var repo = fixtureDB.criarMockMonitoramentosRepository();
        var service = criarService(repo);

        //Act
        var regras = service.getRegrasDeNegociacao();

        //Assert
        var ehRegraDeVenda = new Condition<>(regra -> regra.getClass() == RegraDeVenda.class, "É regra de compra");
        assertThat(regras).extracting(regra -> regra).areExactly(1, ehRegraDeVenda);
    }

    @Test
    public void naoDeveManterContaDeAcaoComSaldoNegativo() {
        //Arrange
        var fixtureDB = new FixtureMonitoramentos();
        var fixtureDBContas = new FixtureContas();
        var precoVenda = new BigDecimal("10.00");
        var contaDoMonitoramento = fixtureDBContas.criarContaPessoalPreenchida();
        var monitoramentoComVenda = fixtureDB.criarNovoMonitoramento(null, precoVenda);
        monitoramentoComVenda.setConta(contaDoMonitoramento);
        var empresa = monitoramentoComVenda.getEmpresa();
        fixtureDB.preencherMonitoramentos(monitoramentoComVenda);
        var observacaoDeAcao = new AcaoObservada(empresa, BigDecimal.valueOf(100000), precoVenda.multiply(new BigDecimal(2)));
        var service = criarService(fixtureDB.criarMockMonitoramentosRepository());
        //Act
        var movimentacoesDeConta = service.aplicarRegrasDeNegociacao(observacaoDeAcao);
        contaDoMonitoramento.registrarMovimentacoes(movimentacoesDeConta.toArray(new MovimentacaoDeConta[] { }));

        //Assert
        assertThat(contaDoMonitoramento.getContasDeAcao()).extracting(ContaDeAcao::getSaldoAtual)
                                        .allMatch(saldo -> saldo.compareTo(BigDecimal.ZERO) >= 0);
    } 

    private RegrasDeNegociacaoService criarService(MonitoramentosRepository repo) {
        return new RegrasDeNegociacaoServiceImpl(repo);
    }
}