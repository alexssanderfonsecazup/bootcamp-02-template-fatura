package br.com.bootcamp.zup.fatura.integracao;

import java.math.BigDecimal;

public class ParcelaCartaoIntegracaoRequest {

    private String identificadorDaFatura;
    private int quantidade;
    private BigDecimal valor;

    public ParcelaCartaoIntegracaoRequest(String identificadorDaFatura, int quantidade, BigDecimal valor) {
        this.identificadorDaFatura = identificadorDaFatura;
        this.quantidade = quantidade;
        this.valor = valor;
    }



    public String getIdentificadorDaFatura() {
        return identificadorDaFatura;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
