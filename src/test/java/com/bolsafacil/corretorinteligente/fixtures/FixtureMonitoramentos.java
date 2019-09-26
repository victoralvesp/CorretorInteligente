package com.bolsafacil.corretorinteligente.fixtures;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.entities.MonitoramentosDataEntity;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosDataRepository;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import org.mockito.Mockito;

import javassist.NotFoundException;

/**
 * FixtureMonitoramentos
 */
public class FixtureMonitoramentos {

    Set<Monitoramento> monitoramentosSalvos;
    Set<MonitoramentosDataEntity> monitoramentosSalvosData;
    private final Random idGenerator = new Random();

    public Monitoramento criarNovoMonitoramento() {
        int id = idGenerator.nextInt(10000);
        String empresa = "Intel" + id;
        return this.criarNovoMonitoramento(empresa);
    }

    public Monitoramento criarNovoMonitoramento(String empresa) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");

        return this.criarNovoMonitoramento(empresa, precoCompra, precoVenda);
    }

    public Monitoramento criarNovoMonitoramento(String empresa, long id) {
        var precoVenda = new BigDecimal("11.00");
        var precoCompra = new BigDecimal("10.00");

        return this.criarNovoMonitoramento(empresa, precoCompra, precoVenda, id);
    }

    public Monitoramento criarNovoMonitoramento(BigDecimal precoCompra, BigDecimal precoVenda) {
        var empresa = "Intel";

        return this.criarNovoMonitoramento(empresa, precoCompra, precoVenda);
    }

    public Monitoramento criarNovoMonitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda) {
        int id = idGenerator.nextInt(10000);
        return criarNovoMonitoramento(empresa, precoCompra, precoVenda, id);
    }

    private Monitoramento criarNovoMonitoramento(String empresa, BigDecimal precoCompra, BigDecimal precoVenda, long id) {
        var monitoramento = new Monitoramento(empresa, precoCompra, precoVenda, id);
        var conta = new FixtureContas().criarContaPessoalPreenchida();
        monitoramento.setConta(conta);
        return monitoramento;
    }

    public void preencherMonitoramentos(int qtde) {
        var monitoramentos = new Monitoramento[] {};
        this.preencherMonitoramentos(qtde, monitoramentos);
    }

    public void preencherMonitoramentos(Monitoramento... seed) {
        this.preencherMonitoramentos(0, seed);
    }

    public void preencherMonitoramentos(int qtdeDeEntradas, Monitoramento... seed) {
        var monitoramentos = new ArrayList<>(Arrays.asList(seed));
        for (int i = 0; i < qtdeDeEntradas; i++) {
            var monitoramento = criarNovoMonitoramento();
            monitoramentos.add(monitoramento);
        }
        monitoramentosSalvos = new HashSet<>(monitoramentos);
        monitoramentosSalvosData = monitoramentosSalvos.stream().map(mon -> MonitoramentosDataEntity.converterDe(mon))
                                                                .collect(Collectors.toSet());
    }

    public MonitoramentosService criarMockMonitoramentosServices() throws NotFoundException {
        var repoMock = Mockito.mock(MonitoramentosService.class);
        when(repoMock.salvarMonitoramento(any(Monitoramento.class), anyLong()))
                .thenAnswer(monArg -> adicionarMonitoramento(monArg.getArguments()[0]));

        when(repoMock.listarMonitoramentos()).thenAnswer(i -> getMonitoramentosSalvos());

        when(repoMock.excluirMonitoramento(any(String.class), anyLong())).thenAnswer(an -> {
            var empresa = (String) an.getArguments()[0];
            var monitoramento = find(empresa);
            
            monitoramento.setExcluido(true);
            return monitoramento;
        });
        when(repoMock.buscarMonitoramento(any(String.class), anyLong())).thenAnswer(an -> {
            var empresa = (String)an.getArguments()[0];
            return find(empresa);
        });

        return repoMock;
    }

    public MonitoramentosDataRepository criarMockMonitoramentosRepository() throws NotFoundException {
        var repoMock = Mockito.mock(MonitoramentosDataRepository.class);
        when(repoMock.save(any(MonitoramentosDataEntity.class)))
                .thenAnswer(monArg -> adicionarMonitoramentoData(monArg.getArguments()[0]));

        when(repoMock.findAll()).thenAnswer(i -> getMonitoramentosSalvosData());

        when(repoMock.findById(anyLong())).thenAnswer(an -> {
            var empresa = (long) an.getArguments()[0];
            var monitoramento = findData(empresa);
            
            
            return Optional.of(monitoramento);
        });

        return repoMock;
    }

    private Monitoramento find(String empresa) {
        for (Monitoramento m : getMonitoramentosSalvos()) {
            if (m.getEmpresa().equals(empresa)) {
                return m;
            }
        }
        return null;
    }
    private MonitoramentosDataEntity findData(Long id) {
        for (MonitoramentosDataEntity m : monitoramentosSalvosData) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }
 
    private Monitoramento adicionarMonitoramento(Object object) {
        var monitoramentoAAdicionar = (Monitoramento) object;

        monitoramentosSalvos.add(monitoramentoAAdicionar);

        return monitoramentoAAdicionar;
    }
    private MonitoramentosDataEntity adicionarMonitoramentoData(Object object) {
        var monitoramentoAAdicionar = (MonitoramentosDataEntity) object;
        var objetoJaSalvo = findData(monitoramentoAAdicionar.getId());
        if(objetoJaSalvo == null)
            monitoramentosSalvosData.add(monitoramentoAAdicionar);

        return monitoramentoAAdicionar;
    }

    public Set<Monitoramento> getMonitoramentosSalvos() {
        return monitoramentosSalvos;
    }
    public Set<MonitoramentosDataEntity> getMonitoramentosSalvosData() {
        return monitoramentosSalvosData;
    }
}