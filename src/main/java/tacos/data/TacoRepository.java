package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.Taco;

/*
 * CrudRepository는 CRUD를 위한 많은 메서드가 선언되어 있다. 
 * 첫번째 매개변수는 리퍼지터리에 저장되는 개체 타입이며 두 번째 매개변수는 개체 ID 속성의 타입이다.
 */
public interface TacoRepository extends CrudRepository<Taco, Long>{
//	Taco save(Taco design);
}
