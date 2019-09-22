package com.bolsafacil.corretorinteligente.services;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;

/**
 * MonitorServiceImpl
 */
public class MonitorServiceImpl implements MonitorService {

    private final MonitoramentosRepository repository;

    public MonitorServiceImpl(MonitoramentosRepository repository) {
        this.repository = repository;
    }

    @Override
    public Monitoramento registrarMonitoramento(Monitoramento monitoramento) {
        var dataAtual = DefinicoesDoServidor.getDataAtual();
        monitoramento.setDataRegistro(dataAtual);

        var monitoramentoSalvo = repository.salvar(monitoramento);

        return monitoramentoSalvo;
    }   
}