package br.com.bootcamp.zup.fatura.integracao;

import br.com.bootcamp.zup.fatura.integracao.response.ConsultaCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
@FeignClient(url="http://localhost:8888/api/cartoes", name = "cartoes")
public interface CartaoClient {

    @GetMapping("/{id}")
    ConsultaCartaoResponse consultaCartao(@PathVariable String id);

    @PostMapping("/{idCartao}/parcelas")
    void parcelaCartao( @PathVariable String idCartao, @RequestBody ParcelaCartaoIntegracaoRequest parcelaCartaoIntegracaoRequest);
}
