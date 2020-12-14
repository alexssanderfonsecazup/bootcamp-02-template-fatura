package br.com.bootcamp.zup.fatura.consometransacao.kafka.response;



import br.com.bootcamp.zup.fatura.consometransacao.Transacao;
import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.consometransacao.Estabelecimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventoDeTransacao {

    private String id;
    private BigDecimal valor;
    private EventoDeTransacaoEstabelecimento estabelecimento;
    private EventoDeTransacaoCartao cartao;
    private LocalDateTime efetivadaEm;


    public Transacao toModel(Cartao cartao) {
        Estabelecimento estabelecimento = new Estabelecimento(this.estabelecimento.getNome(),
                this.estabelecimento.getCidade(), this.estabelecimento.getEndereco());
        return new Transacao(id, valor, estabelecimento, efetivadaEm, cartao);

    }

    public String getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public EventoDeTransacaoEstabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public EventoDeTransacaoCartao getCartao() {
        return cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }
}
