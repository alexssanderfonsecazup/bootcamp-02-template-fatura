package br.com.bootcamp.zup.fatura.consometransacao;

import br.com.bootcamp.zup.fatura.Fatura;
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
import java.util.Optional;

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
        Optional<Fatura> optionalFatura = faturaRepository.findByCartaoAndMesReferencia(transacao.getCartao(), transacao.getEfetivadaEm().getMonth());

        logger.info("Verificando se já existe fatura iniciada para o cartão {} no mês de {}", transacao.getCartao().getId(), transacao.getEfetivadaEm().getMonth());

        if (optionalFatura.isPresent()) {
            logger.info("Encontrada fatura {}", optionalFatura.get().getId());
            optionalFatura.get().getTransacoes().add(transacao);
            logger.info("Transação {} adicionada na fatura {}", transacao.getId(), optionalFatura.get().getId());
            return;
        }


        Fatura fatura = new Fatura(transacao.getCartao(), transacao.getEfetivadaEm().getMonth());
        entityManager.persist(fatura);
        fatura.setTransacoes(Arrays.asList(transacao));

        logger.info("Fatura {} criada para o cartao {}", fatura.getId(), transacao.getCartao().getId());
        logger.info("Transação {} adicionada na fatura {}", fatura.getId(), transacao.getId());
    }


}
