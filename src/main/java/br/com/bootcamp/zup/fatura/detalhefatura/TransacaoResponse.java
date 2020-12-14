package br.com.bootcamp.zup.fatura.detalhefatura;

import br.com.bootcamp.zup.fatura.consometransacao.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponse {

    private String id;
    private BigDecimal valor;
    private EstabelecimentoResponse estabelecimento;
    private LocalDateTime efetivadaEm;

    public TransacaoResponse(Transacao transacao){
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.estabelecimento = new EstabelecimentoResponse(transacao.getEstabelecimento());
        this.efetivadaEm = transacao.getEfetivadaEm();
    }


    public String getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public EstabelecimentoResponse getEstabelecimento() {
        return estabelecimento;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}
