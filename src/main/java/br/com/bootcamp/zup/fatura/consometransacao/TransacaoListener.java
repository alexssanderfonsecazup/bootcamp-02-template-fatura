package br.com.bootcamp.zup.fatura.consometransacao;

import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.FaturaPk;
import br.com.bootcamp.zup.fatura.FaturaRepository;
import br.com.bootcamp.zup.fatura.consometransacao.kafka.response.EventoDeTransacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class TransacaoListener implements ApplicationListener<NovaTransacaoEvent> {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    FaturaRepository faturaRepository;

    private final Logger logger = LoggerFactory.getLogger(NovaTransacaoEvent.class);

    @Override
    @Transactional
    public void onApplicationEvent(NovaTransacaoEvent event) {

        EventoDeTransacao transacaoEvent = event.getTransacao();
        Cartao cartao = entityManager.find(Cartao.class, transacaoEvent.getCartao().getId());

        if (cartao == null) {
            cartao = transacaoEvent.getCartao().toModel();
            entityManager.persist(cartao);
        }

        Transacao transacao = transacaoEvent.toModel(cartao);
        entityManager.persist(transacao);

        adicionarNaFatura(transacao);
    }

    private void adicionarNaFatura(Transacao transacao) {
        Fatura fatura = entityManager.find(Fatura.class, new FaturaPk(transacao.getEfetivadaEm().getMonth(), transacao.getCartao().getId()));
        logger.info("Verificando se já existe fatura iniciada para o mês da transação ");

        if (fatura !=null ) {
            logger.info("Já existe valores na fatura do mês");
            fatura.getTransacoes().add(transacao);
            fatura.adicionaValorNaFatura(transacao.getValor());
            logger.info("Valor da transação adicionada na fatura do mês com sucesso");
            return;
        }

        logger.info("Primeira transação para a fatura do mês");
        FaturaPk id = new FaturaPk(transacao.getEfetivadaEm().getMonth(), transacao.getCartao().getId());
        fatura = new Fatura(id, transacao.getEfetivadaEm().getMonth(), transacao.getValor());
        entityManager.persist(fatura);
        fatura.setTransacoes(new HashSet<Transacao>(Arrays.asList(transacao)));
        logger.info("Valor da transação adicionado na fatura com sucesso");

    }


}
