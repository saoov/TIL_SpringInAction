package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	private TacoRepository tacoRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}

	@GetMapping
	public String showDesignForm(Model model) {
//		List<Ingredient> ingredients = Arrays.asList(
//				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//				new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//				new Ingredient("TMTO", "DIced TOmatoes", Type.VEGGIES),
//				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//				new Ingredient("CHED", "Cheddar", Type.CHEESE),
//				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//				new Ingredient("SLSA", "Salsa", Type.SAUCE),
//				new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//				);
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
			filterByType(ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		
		return "design"; //design.html
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
		return ingredients
				.stream()
				.filter(x->x.getType().equals(type))
				.collect(Collectors.toList());
		
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			return "design";
		}
		
		//이 지점에서 타코 디자인 저장
		//3장에서 진행
//		log.info("Processing design : "+design);
		Taco saved = tacoRepo.save(design);
		order.addDesign(saved);
		return "redirect:/orders/current";
	}
}
