package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.bolsafacil.corretorinteligente.entities.MonitoramentosDataEntity.converterDe;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosDataRepository;
import com.bolsafacil.corretorinteligente.services.ContasService;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

/**
 * MonitoramentosService
 */
@Component
public class MonitoramentosServiceImpl implements MonitoramentosService {

    private final MonitoramentosDataRepository repoMonitoramentos;
    private final ContasService serviceConta;

    public MonitoramentosServiceImpl(MonitoramentosDataRepository monitoramentosRepo, ContasService service) {
        this.repoMonitoramentos = monitoramentosRepo;
        serviceConta = service;
    }

    @Override
    public Monitoramento salvarMonitoramento(Monitoramento monitoramento, long idConta) throws NotFoundException {
        var empresaDoMonitoramento = monitoramento.getEmpresa();
        var monitoramentosJaSalvos = repoMonitoramentos.findByEmpresa(empresaDoMonitoramento);
        if(monitoramentosJaSalvos != null) {
            monitoramentosJaSalvos.stream()
                                .filter(mon -> !mon.getExcluido() && mon.getConta().getId() == idConta)
                                .forEach(monitoramentoSalvo -> alterarParaExcluido(monitoramentoSalvo.converterParaModelo()));
        }
        
        var conta = serviceConta.buscar(idConta);
        monitoramento.setConta(conta);
        var monitoramentoIncluido = repoMonitoramentos.save(converterDe(monitoramento));

        return monitoramentoIncluido.converterParaModelo();
    }

    private Monitoramento alterarParaExcluido(Monitoramento monitoramentoJaSalvo) {
        monitoramentoJaSalvo.setExcluido(true);
        var entity = converterDe(monitoramentoJaSalvo);
        return repoMonitoramentos.save(entity).converterParaModelo();
    }

    @Override
    public Monitoramento buscarMonitoramento(String empresa, Long idConta) throws NotFoundException {
        var monitoramentosSalvos = repoMonitoramentos.findByEmpresa(empresa);
        
        if(monitoramentosSalvos == null || monitoramentosSalvos.stream()
                                            .allMatch(mon-> mon.getExcluido() || mon.getConta().getId() != idConta))
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);

        return monitoramentosSalvos.stream().findFirst().get().converterParaModelo();
    }

    @Override
    public boolean excluirMonitoramento(String empresa, Long idConta) throws NotFoundException {
        var monitoramentosSalvos = repoMonitoramentos.findByEmpresa(empresa);
        
        if(monitoramentosSalvos == null || monitoramentosSalvos.stream()
                                                .allMatch(mon-> mon.getExcluido() || mon.getConta().getId() != idConta)) {
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);
        }
        var monitoramentoSalvo = monitoramentosSalvos.stream()
                                    .filter(mon -> mon.getConta().getId() == idConta).findFirst().get();

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