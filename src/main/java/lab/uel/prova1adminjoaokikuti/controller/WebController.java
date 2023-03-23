package lab.uel.prova1adminjoaokikuti.controller;

import lab.uel.prova1adminjoaokikuti.model.ItemCardapioRepository;
import lab.uel.prova1adminjoaokikuti.model.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    ItemCardapioRepository iRepo;

    @Autowired
    RestauranteRepository rRepo;

    @GetMapping(value = {"/", "index"})
    public String indexPage(Model model) {
        model.addAttribute("restaurantes", rRepo.findAll());
        model.addAttribute("itens", iRepo.findAll());

        return "index";
    }
}
