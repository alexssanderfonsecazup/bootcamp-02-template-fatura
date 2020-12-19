package br.com.bootcamp.zup.fatura.renogociacaofatura;


import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import br.com.bootcamp.zup.fatura.integracao.CartaoClient;
import br.com.bootcamp.zup.fatura.integracao.RenegociacaoFaturaIntegracaoRequest;
import br.com.bootcamp.zup.fatura.parcelarfatura.ParcelaFaturaController;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes/{idCartao}/faturas/{idFatura}/renegociacoes")
public class RegociarFaturaController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CartaoClient cartaoClient;

    private Logger logger = LoggerFactory.getLogger(ParcelaFaturaController.class);

    @PostMapping
    @Transactional
    public ResponseEntity<?> renegociarFatura(@PathVariable String idCartao, @PathVariable UUID idFatura
            , @RequestBody @Valid RenegociacaoFaturaRequest renegociacaoRequest, UriComponentsBuilder uriBuilder) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        Fatura fatura = entityManager.find(Fatura.class, idFatura);
        if (fatura == null) {
            return ResponseEntity.notFound().build();
        }

        logger.info("Iniciando processo de renegociação da fatura {} para o cartão {} ", idFatura, idCartao);

        Renegociacao renegociacao = renegociacaoRequest.toModel(fatura);
        entityManager.persist(renegociacao);
        logger.info("Renegociação {} salva no banco de dados", renegociacao.getId());

        try {
            logger.info("Sinalizando a api legado da renegociação {}", renegociacao.getId());
            cartaoClient.renogociaFatura(idCartao, new RenegociacaoFaturaIntegracaoRequest(idCartao, renegociacao.getQuantidade(), renegociacao.getValorParcela()));
            renegociacao.setStatus(StatusRenegociacaoEnum.APROVADO);
            logger.info("Renegociação {} aprovado pela api legado", renegociacao.getId());
        } catch (FeignException e) {
            renegociacao.setStatus(StatusRenegociacaoEnum.NEGADO);
            logger.info("Renegociação {} não foi  aprovada pela api legado", renegociacao.getId());
            logger.debug("Status: ", e.status());
            logger.debug("Content: ", e.contentUTF8());
        }


        URI uri = uriBuilder.path("/cartoes/{idCartao}/faturas/{idFatura}/renegociacoes/{idRenegociacao}")
                .buildAndExpand(idCartao, idFatura, renegociacao.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{idRenegociacao}")
    public ResponseEntity<?> consultaParcela(@PathVariable String idCartao,
                                             @PathVariable UUID idFatura,
                                             @PathVariable UUID idRenegociacao) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        Fatura fatura = entityManager.find(Fatura.class, idFatura);
        if (fatura == null) {
            return ResponseEntity.notFound().build();
        }

        Renegociacao renegociacao = entityManager.find(Renegociacao.class, idRenegociacao);
        if (renegociacao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new RenegociaoResponse(renegociacao));

    }

}
