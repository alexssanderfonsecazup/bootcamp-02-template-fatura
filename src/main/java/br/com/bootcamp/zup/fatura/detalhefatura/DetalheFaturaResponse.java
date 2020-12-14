package br.com.bootcamp.zup.fatura.detalhefatura;

import br.com.bootcamp.zup.fatura.Fatura;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalheFaturaResponse {

    private BigDecimal total;
    private Set<TransacaoResponse> transacoes;

    public DetalheFaturaResponse(Fatura fatura) {
        this.total = fatura.getTotal();
        this.transacoes = fatura.getTransacoes().stream()
                .map(TransacaoResponse::new)
                .collect(Collectors.toSet());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Set<TransacaoResponse> getTransacoes() {
        return transacoes;
    }
}
