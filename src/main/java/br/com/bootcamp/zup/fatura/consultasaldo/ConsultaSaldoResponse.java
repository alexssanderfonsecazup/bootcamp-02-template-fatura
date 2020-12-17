package br.com.bootcamp.zup.fatura.consultasaldo;

import br.com.bootcamp.zup.fatura.consometransacao.Transacao;
import br.com.bootcamp.zup.fatura.detalhefatura.TransacaoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ConsultaSaldoResponse {
    private BigDecimal saldo;
    private List<TransacaoResponse> transacoes;


    ConsultaSaldoResponse(BigDecimal saldo, List<Transacao> transacoes) {
        this.saldo = saldo;
        this.transacoes = TransacaoResponse.toList(transacoes);

    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<TransacaoResponse> getTransacoes() {
        return transacoes;
    }
}
