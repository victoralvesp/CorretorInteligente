package com.bolsafacil.corretorinteligente.services.implementacoes;

import java.util.Collection;

import com.bolsafacil.corretorinteligente.domain.contas.ContaPessoal;
import com.bolsafacil.corretorinteligente.repositorios.ContasRepository;
import com.bolsafacil.corretorinteligente.services.ContasService;

import org.springframework.stereotype.Component;

/**
 * ContasService
 */
@Component
public class ContasServiceImpl implements ContasService {

    private final ContasRepository repoContas;

    public ContasServiceImpl(ContasRepository repoContas) {
        this.repoContas = repoContas;
    }


    @Override
    public Collection<ContaPessoal> listar() {
        return null;
    }

    @Override
    public ContaPessoal inserir(ContaPessoal contaConvertida) {
        return null;
    }

    
}