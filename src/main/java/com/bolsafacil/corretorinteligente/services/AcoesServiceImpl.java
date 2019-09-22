package com.bolsafacil.corretorinteligente.services;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.ObservacoesDeAcao;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;

import javassist.NotFoundException;

/**
 * MonitorServiceImpl
 */
public class AcoesServiceImpl implements AcoesService {

    private final ObservacoesDeAcaoRepository repository;

    public AcoesServiceImpl(ObservacoesDeAcaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ObservacoesDeAcao registrarObservacaoDeAcoes(ObservacoesDeAcao monitoramento) {
        var dataAtual = DefinicoesDoServidor.getDataAtual();
        monitoramento.setDataRegistro(dataAtual);

        var monitoramentoSalvo = repository.salvar(monitoramento);

        return monitoramentoSalvo;
    }

    @Override
    public Collection<ObservacoesDeAcao> listarObservacoesRealizadas() {
        
        var monitoramentosSalvos = repository.listar();

        return monitoramentosSalvos;
    }

    @Override
    public ObservacoesDeAcao buscar(long id) throws NotFoundException{
        var monitoramentoBuscado = repository.buscar(id);

        if(monitoramentoBuscado == null)
            throw new NotFoundException("Não foi possível encontrar o monitoramento buscado");

        return monitoramentoBuscado;
    }
}