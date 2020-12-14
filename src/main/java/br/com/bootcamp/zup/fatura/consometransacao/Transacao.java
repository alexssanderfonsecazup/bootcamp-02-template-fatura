package br.com.bootcamp.zup.fatura.consometransacao;



import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.consometransacao.Estabelecimento;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {
    @Id
    @NotNull
    private String id;

    @NotNull @Positive
    private BigDecimal valor;

    @NotNull
    @Embedded
    private Estabelecimento estabelecimento;

    @NotNull
    private LocalDateTime efetivadaEm;

    @ManyToOne(cascade = CascadeType.PERSIST )
    @JoinColumn(name ="cartao_id")
    private Cartao cartao;

    public Transacao(){}

    public Transacao(@NotNull String id, @NotNull @Positive BigDecimal valor, @NotNull Estabelecimento estabelecimento, @NotNull LocalDateTime efetivadaEm, Cartao cartao) {
        this.id = id;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
        this.efetivadaEm = efetivadaEm;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }


}
