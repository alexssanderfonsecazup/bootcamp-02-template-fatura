package br.com.bootcamp.zup.fatura.consometransacao;

import br.com.bootcamp.zup.fatura.consometransacao.kafka.response.EventoDeTransacao;
import org.springframework.context.ApplicationEvent;

public class NovaTransacaoEvent extends ApplicationEvent {

    private EventoDeTransacao eventoTransacao;

    public NovaTransacaoEvent(Object source, EventoDeTransacao eventoTransacao) {
        super(source);
        this.eventoTransacao = eventoTransacao;
    }

    public EventoDeTransacao getTransacao() {
        return eventoTransacao;
    }
}
