package com.bolsafacil.corretorinteligente;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bolsafacil.corretorinteligente.domain.AcaoDoMonitoramento;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.MonitorService;
import com.bolsafacil.corretorinteligente.services.MonitorServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * MonitorServiceTests
 */
public class MonitorServiceTests {

    private MonitorService monitorService;

    @Before
    public void setup() {
        var monitoramentosRepoMock = criarMockMonitoramentosRepository();
        monitorService = new MonitorServiceImpl(monitoramentosRepoMock);
    }

    @Test
    public void monitoramentoDeveSerSalvoComDataDeRegistroAtual() {
        //Arrange
        var monitoramento = criarNovoMonitoramento();
        var dataAtual = LocalDateTime.now();
        //Act 
        var monitoramentoRegistrado = monitorService.registrarMonitoramento(monitoramento);
        //Assert
        assertTrue("message", monitoramentoRegistrado.getDataRegistro().compareTo(dataAtual) >= 0 );
    }



    private Monitoramento criarNovoMonitoramento() {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");
        var acaoMonitorada = new AcaoDoMonitoramento("Intel", precoCompra, precoVenda);
        var dataDoMonitoramento = LocalDateTime.now();
        Set<AcaoDoMonitoramento> acoesDoMonitoramento = new HashSet<AcaoDoMonitoramento>(Arrays.asList(acaoMonitorada));

        return new Monitoramento(acoesDoMonitoramento, dataDoMonitoramento);
    }

    private MonitoramentosRepository criarMockMonitoramentosRepository() {
        var repoMock = Mockito.mock(MonitoramentosRepository.class);
        when(repoMock.salvar(any(Monitoramento.class))).then(returnsFirstArg());
        return repoMock;
    }
}