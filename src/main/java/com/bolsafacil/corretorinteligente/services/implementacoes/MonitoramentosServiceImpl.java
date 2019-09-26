package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.bolsafacil.corretorinteligente.entities.MonitoramentosDataEntity.converterDe;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosDataRepository;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

/**
 * MonitoramentosService
 */
@Component
public class MonitoramentosServiceImpl implements MonitoramentosService {

    private final MonitoramentosDataRepository repoMonitoramentos;

    public MonitoramentosServiceImpl(MonitoramentosDataRepository monitoramentosRepo) {
        this.repoMonitoramentos = monitoramentosRepo;
    }

    @Override
    public Monitoramento salvarMonitoramento(Monitoramento monitoramento) {
        var empresaDoMonitoramento = monitoramento.getEmpresa();
        var monitoramentoJaSalvo = repoMonitoramentos.findById(empresaDoMonitoramento);

        monitoramentoJaSalvo.ifPresent(monitoramentoSalvo -> alterarParaExcluido(monitoramentoSalvo.converterParaModelo()));

        var monitoramentoIncluido = repoMonitoramentos.save(converterDe(monitoramento));

        return monitoramentoIncluido.converterParaModelo();
    }

    private Monitoramento alterarParaExcluido(Monitoramento monitoramentoJaSalvo) {
        monitoramentoJaSalvo.setExcluido(true);
        var entity = converterDe(monitoramentoJaSalvo);
        return repoMonitoramentos.save(entity).converterParaModelo();
    }

    @Override
    public Monitoramento buscarMonitoramento(String empresa) throws NotFoundException {
        var monitoramentoSalvoMaybe = repoMonitoramentos.findById(empresa);
        
        if(!monitoramentoSalvoMaybe.isPresent() || monitoramentoSalvoMaybe.get().getExcluido())
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);

        return monitoramentoSalvoMaybe.get().converterParaModelo();
    }

    @Override
    public boolean excluirMonitoramento(String empresa) throws NotFoundException {
        var monitoramentoSalvoMaybe = repoMonitoramentos.findById(empresa);
        
        if(!monitoramentoSalvoMaybe.isPresent() || monitoramentoSalvoMaybe.get().getExcluido()) {
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);
        }
        var monitoramentoSalvo = monitoramentoSalvoMaybe.get();

        var monitoramentoExcluido = alterarParaExcluido(monitoramentoSalvo.converterParaModelo());

        return monitoramentoExcluido.getExcluido();
    }

    @Override
    public Collection<Monitoramento> listarMonitoramentos() {
        var monitoramentosEnti = repoMonitoramentos.findAll();

        var streamEntities = StreamSupport.stream(monitoramentosEnti.spliterator(), false);

        var monitoramentosNaoExcluidos = streamEntities.map(ent -> ent.converterParaModelo())
                                                       .filter(mon -> !mon.getExcluido())
                                                       .collect(Collectors.toList());

        return monitoramentosNaoExcluidos;
    }

    
}