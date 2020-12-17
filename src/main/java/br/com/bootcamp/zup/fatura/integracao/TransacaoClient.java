package br.com.bootcamp.zup.fatura.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(url = "http://localhost:7777/api/cartoes",name = "transacoes")
public interface TransacaoClient {

    @PostMapping
    void estimulaTransacao(@RequestBody Map<String,Object> estimuloTransacao);

    @DeleteMapping("/{id}")
    void removerEstimulo(@PathVariable String id);


}
