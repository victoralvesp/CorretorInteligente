package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;
import java.util.stream.Collectors;

import com.bolsafacil.corretorinteligente.domain.Monitoramento;
import com.bolsafacil.corretorinteligente.repositorios.MonitoramentosRepository;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import javassist.NotFoundException;

/**
 * MonitoramentosService
 */
public class MonitoramentosServiceImpl implements MonitoramentosService {

    private final MonitoramentosRepository repoMonitoramentos;

    public MonitoramentosServiceImpl(MonitoramentosRepository monitoramentosRepo) {
        this.repoMonitoramentos = monitoramentosRepo;
    }
    @Override
    public Monitoramento salvarMonitoramento(Monitoramento monitoramento) {
        var empresaDoMonitoramento = monitoramento.getEmpresa();
        var monitoramentoJaSalvo = repoMonitoramentos.buscar(empresaDoMonitoramento);
        
        if(monitoramentoJaSalvo != null) {
            repoMonitoramentos.alterarParaExcluido(monitoramentoJaSalvo);
        }

        var monitoramentoIncluido = repoMonitoramentos.incluir(monitoramento);
            
        return monitoramentoIncluido;
    }

    @Override
    public Monitoramento buscarMonitoramento(String empresa) throws NotFoundException {
        var monitoramentoJaSalvo = repoMonitoramentos.buscar(empresa);
        
        if(monitoramentoJaSalvo == null || monitoramentoJaSalvo.getExcluido())
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);

        return monitoramentoJaSalvo;
    }

    @Override
    public boolean excluirMonitoramento(String empresa) throws NotFoundException {
        var monitoramentoJaSalvo = repoMonitoramentos.buscar(empresa);
        
        if(monitoramentoJaSalvo == null)
            throw new NotFoundException("Não foi possível encontrar o monitoramento de " + empresa);
        
        var monitoramentoExcluido = repoMonitoramentos.alterarParaExcluido(monitoramentoJaSalvo);

        return monitoramentoExcluido.getExcluido();
    }

    @Override
    public Collection<Monitoramento> listarMonitoramentos() {
        var monitoramentos = repoMonitoramentos.listar();

        var monitoramentosNaoExcluidos = monitoramentos.stream()
                                                       .filter(mon -> !mon.getExcluido())
                                                       .collect(Collectors.toSet());

        return monitoramentosNaoExcluidos;
    }

    
}