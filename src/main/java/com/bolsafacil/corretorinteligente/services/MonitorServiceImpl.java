package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;

import javassist.NotFoundException;

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

    @Override
    public Collection<Monitoramento> listarMonitoramentos() {
        
        var monitoramentosSalvos = repository.listar();

        return monitoramentosSalvos;
    }

    @Override
    public Monitoramento buscarMonitoramento(long id) throws NotFoundException{
        var monitoramentoBuscado = repository.buscar(id);

        if(monitoramentoBuscado == null)
            throw new NotFoundException("Não foi possível encontrar o monitoramento buscado");

        return monitoramentoBuscado;
    }
}