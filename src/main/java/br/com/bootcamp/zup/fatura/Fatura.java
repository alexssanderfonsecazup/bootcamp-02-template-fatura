package br.com.bootcamp.zup.fatura;

import br.com.bootcamp.zup.fatura.consometransacao.Transacao;
import org.springframework.util.Assert;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Set;

@Entity
public class Fatura {


    @EmbeddedId
    private FaturaPk id;

    @NotNull
    private BigDecimal total;

    @OneToMany
    private Set<Transacao> transacoes;

    @Deprecated
    public Fatura() {
    }

    public Fatura(FaturaPk id, @NotNull Month mes, @NotNull BigDecimal total) {
        this.id = id;
        this.total = total;

    }

    public void adicionaValorNaFatura(@NotNull BigDecimal valor) {
        Assert.isTrue(total != null, "Você não pode fazer a soma com o total nulo");
        this.total = total.add(valor);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTransacoes(Set<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Set<Transacao> getTransacoes() {
        return transacoes;
    }
}
