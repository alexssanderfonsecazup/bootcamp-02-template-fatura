package br.com.bootcamp.zup.fatura.detalhefatura;


import br.com.bootcamp.zup.fatura.Fatura;
import br.com.bootcamp.zup.fatura.FaturaRepository;
import br.com.bootcamp.zup.fatura.consometransacao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Optional;

@RestController
@RequestMapping("faturas/cartoes/{idCartao}")
public class ConsultaDetalhesFaturaController {

    @Autowired
    FaturaRepository faturaRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @GetMapping
    public ResponseEntity<DetalheFaturaResponse> detalhesFatura(@PathVariable String idCartao) {

        Cartao cartao = entityManager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }
        LocalDate dataAtual = LocalDate.now();
        Optional<Fatura> faturaOptional = faturaRepository.findByCartaoAndMesAndAno(cartao,dataAtual.getMonth(), dataAtual.getYear());

        if (!faturaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DetalheFaturaResponse(faturaOptional.get()));

    }
}
