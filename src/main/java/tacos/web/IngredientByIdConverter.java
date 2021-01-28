package tacos.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

/*
 * Converter에서 String은 변환할 값의 타입이고 Ingredient는 변환된 값의 타입이다
 */
@Component
public class IngredientByIdConverter implements Converter<String,Ingredient>{

	private IngredientRepository ingredientRepo;
	
	/*
	 * IngredientRepository 인터페이스를 구현한 빈(JdbcIngredientRepository) 인스턴스가 생성자의 인자로 주입
	 */
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	/*
	 * JPA에서는 자동 구현된 findbyId()가 실행되고 DB에서 식자재를 찾지 못했을 경우 null이 발생할 수 있기 때문에
	 * 안전한 처리를 위해 Optional사용
	 */
	@Override
	public Ingredient convert(String id) {
		Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
		return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
	}

	
}
