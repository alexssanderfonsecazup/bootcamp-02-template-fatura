package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.consometransacao.Transacao;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@Entity
public class Fatura {


    @EmbeddedId
    private FaturaPk id;

    @OneToMany
    private List<Transacao> transacoes;


    @Deprecated
    public Fatura() {
    }

    public Fatura(@NotNull  Month mes, @NotNull String idCartao) {
        FaturaPk id = new FaturaPk(mes, idCartao);
        this.id = id;
    }

    public BigDecimal calculaValorFatura(){
        return Transacao.somaTransacoes(transacoes);
    }


    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}
