package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.ObservacaoDeAcoes;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;
import com.bolsafacil.corretorinteligente.services.AcoesService;

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
    public ObservacaoDeAcoes registrarObservacaoDeAcoes(ObservacaoDeAcoes monitoramento) {
        var dataAtual = DefinicoesDoServidor.getDataAtual();
        monitoramento.setDataRegistro(dataAtual);

        var monitoramentoSalvo = repository.salvar(monitoramento);

        return monitoramentoSalvo;
    }

    @Override
    public Collection<ObservacaoDeAcoes> listarObservacoesRealizadas() {
        
        var monitoramentosSalvos = repository.listar();

        return monitoramentosSalvos;
    }

    @Override
    public ObservacaoDeAcoes buscar(long id) throws NotFoundException{
        var monitoramentoBuscado = repository.buscar(id);

        if(monitoramentoBuscado == null)
            throw new NotFoundException("Não foi possível encontrar o monitoramento buscado");

        return monitoramentoBuscado;
    }
}