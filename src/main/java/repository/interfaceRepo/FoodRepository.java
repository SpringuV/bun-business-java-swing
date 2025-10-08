package repository.interfaceRepo;

import java.util.List;
import java.util.Optional;

import model.Food;
import model.Food.FoodType;

public interface FoodRepository {
	boolean createFood(String id_food, String name_food, double prices, String description, String image_path,
			FoodType type_food, boolean is_available) throws Exception;
	
	Food updateFood(String id_food, Optional<String> name_food, Optional<Double> prices, Optional<String> description, Optional<String> image_path, Optional<FoodType> type_food,
			Optional<Boolean> is_available) throws Exception;
	
	List<Food> getListFood() throws Exception;
	
	String deleteFood(String id_food) throws Exception;
}
