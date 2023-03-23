package lab.uel.prova1adminjoaokikuti.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lab.uel.prova1adminjoaokikuti.model.ItemCardapio;
import lab.uel.prova1adminjoaokikuti.model.ItemCardapioRepository;
import lab.uel.prova1adminjoaokikuti.model.Restaurante;
import lab.uel.prova1adminjoaokikuti.model.RestauranteRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    ItemCardapioRepository iRepo;

    @Autowired
    RestauranteRepository rRepo;

    private static final String SESSION_ITEM = "sessionI";

    @GetMapping(value = {"/", "index"})
    public String indexPage(Model model) {
        model.addAttribute("restaurantes", rRepo.findAll());
        model.addAttribute("itens", iRepo.findAll());

        return "index";
    }

    // Adicionar restaurante
    // Mostrar pagina adicionar_restaurante
    @GetMapping("/novo_restaurante")
    public String showAddRestaurante(Restaurante restaurante) {
        return "novo_restaurante";
    }

    // Função para adicionar restaurante com forms
    @PostMapping("/adicionar_restaurante")
    public String addRestaurante(@Valid Restaurante restaurante, BindingResult result) {

        if (result.hasErrors()) {
            return "novo_restaurante";
        }

        rRepo.save(restaurante);
        return "redirect:/listar_restaurante";
    }

    // Listar restaurantes (editar/ remover/ adicionar item)
    @GetMapping("/listar_restaurante")
    public String showRestaurante(Model model) {

        model.addAttribute("restaurantes", rRepo.findAll());
        return "listar_restaurante";
    }

    // Editar restaurante
    @GetMapping("/editar_restaurante/{id}")
    public String editRestaurante(@PathVariable("id") int id, Model model) {

        Restaurante restaurante = rRepo
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encintrado"));

        model.addAttribute("restaurante", restaurante);
        return "atualizar_restaurante";

    }

    // Atualizar
    @PostMapping("/atualizar_restaurante/{id}")
    public String attRestaurante(@PathVariable("id") int id, @Valid Restaurante restaurante, BindingResult result) {

        if (result.hasErrors()) {
            return "atualizar_restaurante";
        }

        rRepo.save(restaurante);
        return "redirect:/listar_restaurante";
    }

    // Remover
    @GetMapping("/remover_restaurante/{id}")
    public String rmvRestaurante(@PathVariable("id") int id) {

        Restaurante restaurante = rRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        rRepo.delete(restaurante);

        return "redirect:/listar_restaurante";
    }

    // Pagina Novo Item
    @GetMapping("/cardapio/{id}")
    public String showItemRestaurante(@PathVariable("id") int id, Model model, HttpServletRequest request) {

        List<ItemCardapio> cardapio = iRepo.findByRestauranteId(id);

        model.addAttribute("itemCardapio", new ItemCardapio());
        model.addAttribute("sessionI", cardapio);
        model.addAttribute("restaurante", rRepo.findById(id).orElse(null));

        return "listar_cardapio";
    }

    @PostMapping("/adicionar_item/{id}")
    public String addItemRestaurante(@PathVariable("id") int id, @Valid ItemCardapio itemCardapio, BindingResult result , Model model, HttpServletRequest request) {

        Restaurante restaurante = rRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não existe"));

        Map<Restaurante, ItemCardapio> cardapio = (Map<Restaurante, ItemCardapio>) request
                .getSession().getAttribute(SESSION_ITEM);

        if (CollectionUtils.isEmpty(cardapio)) {
            cardapio = new HashMap<>();
        }

        itemCardapio.setRestaurante(restaurante);
        cardapio.put(restaurante, itemCardapio);

        iRepo.save(itemCardapio);

        request.getSession().setAttribute(SESSION_ITEM, cardapio);


        return "redirect:/cardapio/"+id;
    }

    // Editar Item
    @GetMapping("/editar_item/{id}")
    public String editItemRestaurante(@PathVariable("id") int id, Model model) {

        List<ItemCardapio> itemCardapio = iRepo.findByRestauranteId(id);
        model.addAttribute("itemCardapio", itemCardapio);
        model.addAttribute("restaurante", rRepo.findById(id).orElse(null));

        return "atualizar_cardapio";
    }

    @PostMapping("/atualizar_item/{id}")
    public String attItemRestaurante(@PathVariable("id") int id, @Valid ItemCardapio itemCardapio, BindingResult result, Model model, HttpServletRequest request) {

        Restaurante restaurante = rRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não existe"));

        Map<Restaurante, ItemCardapio> cardapio = (Map<Restaurante, ItemCardapio>) request
                .getSession().getAttribute(SESSION_ITEM);

        itemCardapio.setRestaurante(restaurante);
        cardapio.put(restaurante, itemCardapio);

        iRepo.save(itemCardapio);

        request.getSession().setAttribute(SESSION_ITEM, cardapio);

        return "redirect:/cardapio/"+id;
    }

}
