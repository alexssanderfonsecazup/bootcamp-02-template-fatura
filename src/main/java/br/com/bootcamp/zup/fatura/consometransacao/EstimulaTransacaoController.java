package br.com.bootcamp.zup.fatura.consometransacao;

import br.com.bootcamp.zup.fatura.integracao.TransacaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@RestController
@RequestMapping("/faturas/transacoes/estimulos")
public class EstimulaTransacaoController {


    @Autowired
    private TransacaoClient transacaoClient;

    @PersistenceContext
    EntityManager entityManager;

    @Value("${transacao.idcartao}")
    private  String cartaoEstimulado;

    @Value("${transacao.emaillogado}")
    private  String emailUsuarioLogado;

    private Logger logger = LoggerFactory.getLogger(EstimulaTransacaoController.class);

    @PostMapping
    public void subscreverEnvioTransacao(){
        transacaoClient.estimulaTransacao( Map.of("id", cartaoEstimulado,"email",emailUsuarioLogado));
        logger.info("Enviado estimulo para consumo das transações");
    }

    @DeleteMapping
    public void desinscreverEnvioTransacao(){
        transacaoClient.removerEstimulo(cartaoEstimulado);
    }



}
