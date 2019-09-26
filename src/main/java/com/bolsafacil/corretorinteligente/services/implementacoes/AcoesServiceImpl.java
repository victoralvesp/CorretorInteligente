package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.bolsafacil.corretorinteligente.DefinicoesDoServidor;
import com.bolsafacil.corretorinteligente.domain.AcaoObservada;
import com.bolsafacil.corretorinteligente.repositorios.ObservacoesDeAcaoRepository;
import com.bolsafacil.corretorinteligente.services.AcoesService;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

/**
 * MonitorServiceImpl
 */
@Component
public class AcoesServiceImpl implements AcoesService {

    private final ObservacoesDeAcaoRepository repository;

    public AcoesServiceImpl(ObservacoesDeAcaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public AcaoObservada registrarObservacaoDeAcoes(AcaoObservada monitoramento) {
        var dataAtual = DefinicoesDoServidor.getDataAtual();
        monitoramento.setData(dataAtual);

        var monitoramentoSalvo = repository.save(monitoramento);

        return monitoramentoSalvo;
    }

    @Override
    public Collection<AcaoObservada> listarObservacoesRealizadas() {
        
        var monitoramentosSalvos = repository.findAll();

        return StreamSupport.stream(monitoramentosSalvos.spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Override
    public AcaoObservada buscar(long id) throws NotFoundException{
        var monitoramentoBuscado = repository.findById(id);

        if(!monitoramentoBuscado.isPresent())
            throw new NotFoundException("Não foi possível encontrar o monitoramento buscado");

        return monitoramentoBuscado.get();
    }
}