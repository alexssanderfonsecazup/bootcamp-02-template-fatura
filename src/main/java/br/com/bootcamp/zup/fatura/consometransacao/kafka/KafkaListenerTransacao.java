package br.com.bootcamp.zup.fatura.consometransacao.kafka;

import br.com.bootcamp.zup.fatura.consometransacao.NovaTransacaoEvent;
import br.com.bootcamp.zup.fatura.consometransacao.kafka.response.EventoDeTransacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class KafkaListenerTransacao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private final Logger logger = LoggerFactory.getLogger(KafkaListenerTransacao.class);

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    @Transactional
    public void escutaPorTransacoes(EventoDeTransacao eventoDeTransacao) {
        logger.info("Transacao {} recebida", eventoDeTransacao.getId());
        applicationEventPublisher.publishEvent(new NovaTransacaoEvent(this, eventoDeTransacao));


    }
}