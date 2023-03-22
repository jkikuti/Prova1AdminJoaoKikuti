package lab.uel.prova1adminjoaokikuti.controller;

import lab.uel.prova1adminjoaokikuti.model.ItemCardapio;
import lab.uel.prova1adminjoaokikuti.model.ItemCardapioRepository;
import lab.uel.prova1adminjoaokikuti.model.Restaurante;
import lab.uel.prova1adminjoaokikuti.model.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    ItemCardapioRepository iRepo;

    @Autowired
    RestauranteRepository rRepo;

    @GetMapping(value = {"/", "index"})
    public String indexPage() {

        Restaurante rest = new Restaurante();
        rest.setNome("mae");

        rRepo.save(rest);

        ItemCardapio iCardapio = new ItemCardapio();
        iCardapio.setNome("lasanha");
        iCardapio.setDescricao("Muito bom");
        iCardapio.setPreco(4.70);
        iCardapio.setRestaurante(rest);
        iRepo.save(iCardapio);

        return "index";
    }
}
