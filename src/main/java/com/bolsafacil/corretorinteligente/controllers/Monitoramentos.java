package com.bolsafacil.corretorinteligente.controllers;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

import com.bolsafacil.corretorinteligente.controllers.dtos.MonitoramentoDto;
import com.bolsafacil.corretorinteligente.services.MonitoramentosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Monitoramentos
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class Monitoramentos {

    @Autowired
    MonitoramentosService service;

    @GetMapping(
        value="/monitoramentos/",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> lista() {
        try {
            var monitoramentos = service.listarMonitoramentos();
            return ok(monitoramentos);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @GetMapping(
        value="/contas/{idConta}/monitoramentos/{empresa}",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> buscar(@PathVariable String empresa, @PathVariable Long idConta) {
        try {
            var monitoramento = service.buscarMonitoramento(empresa, idConta);
            return ok(monitoramento);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @PostMapping(
        value="/contas/{idConta}/monitoramentos/",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> inserir(@RequestBody MonitoramentoDto monitoramentoDto, @PathVariable Long idConta) {
        try {
            var monitoramentoConvertido = monitoramentoDto.converterParaModelo();
            var monitoramentoSalvo = service.salvarMonitoramento(monitoramentoConvertido, idConta);
            return ok(monitoramentoSalvo);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @PutMapping(
        value="/contas/{idConta}/monitoramentos/{empresa}",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> alterar(@RequestBody MonitoramentoDto monitoramentoDto, @PathVariable String empresa, @PathVariable Long idConta) {
        try {
            var monitoramentoConvertido = monitoramentoDto.converterParaModelo();
            var monitoramentoSalvo = service.salvarMonitoramento(monitoramentoConvertido, idConta);
            return ok(monitoramentoSalvo);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(
        value="/contas/{idConta}/monitoramentos/{empresa}",
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseBody
    public ResponseEntity<?> excluir(@PathVariable String empresa, @PathVariable Long idConta) {
        try {
            var monitoramentoSalvo = service.excluirMonitoramento(empresa, idConta);
            return ok(monitoramentoSalvo);
        } catch (Exception e) {
            return badRequest().body(e.getMessage());
        }
    }
}