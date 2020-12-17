package br.com.bootcamp.zup.fatura.detalhefatura;

import br.com.bootcamp.zup.fatura.Fatura;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class DetalheFaturaResponse {

    private BigDecimal total;
    private List<TransacaoResponse> transacoes;

    public DetalheFaturaResponse(Fatura fatura) {
        this.total = fatura.calculaValorFatura();
        this.transacoes = TransacaoResponse.toList(fatura.getTransacoes());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<TransacaoResponse> getTransacoes() {
        return transacoes;
    }
}
