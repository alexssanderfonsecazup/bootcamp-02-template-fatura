package br.com.bootcamp.zup.fatura.consultasaldo;

import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.FaturaRepository;
import br.com.bootcamp.zup.fatura.compartilhado.ApiErrorException;
import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.consometransacao.Transacao;
import br.com.bootcamp.zup.fatura.consometransacao.TransacaoRepository;
import br.com.bootcamp.zup.fatura.integracao.CartaoClient;
import br.com.bootcamp.zup.fatura.integracao.response.ConsultaCartaoResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes/{idCartao}/saldos")
public class ConsultaSaldoController {

    @PersistenceContext
    private EntityManager entityManager;


    @Value("${transacao.numerocartao}")
    private String numeroCartao;

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CartaoClient cartaoClient;

    private Logger logger = LoggerFactory.getLogger(ConsultaSaldoController.class);

    @GetMapping
    @Transactional
    public ResponseEntity<?> consultaSaldoCartao(@PathVariable String idCartao) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        Month mesAtual = LocalDate.now().getMonth();
        Optional<Fatura> faturaOptional = faturaRepository.findByCartaoAndMesReferencia(cartao, mesAtual);

        if (!faturaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Transacao> transacoes = faturaOptional.get().getTransacoes();

        try {
            ConsultaCartaoResponse cartaoResponse = cartaoClient.consultaCartao(numeroCartao);
            cartao.atualizaLimiteCasoAlterado(cartaoResponse.getLimite());
        } catch (FeignException e) {
            logger.info("Falha ao consulta o saldo do cartão {} na api legado ",idCartao);
            logger.debug("Status:  {}",e.status());
            logger.debug("Content: {}", e.contentUTF8());
            throw new ApiErrorException(HttpStatus.SERVICE_UNAVAILABLE, "Erro ao consultar api de cartão");
        }

        BigDecimal saldo = cartao.calculaSaldoDisponivel(transacoes);
        List<Transacao> ultimasDezTransacoes = transacaoRepository.findFirst10ByCartaoOrderByEfetivadaEmAsc(cartao);
        return ResponseEntity.ok(new ConsultaSaldoResponse(saldo, ultimasDezTransacoes));

    }
}
