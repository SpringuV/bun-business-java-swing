package repository.interfaceRepo;

import java.util.List;
import java.util.Optional;

import model.Ingredient;
import model.Ingredient.UnitOfMeasurement;

public interface IngredientRepository {
	boolean createIngredient(String name_ingredients, double prices, int quantity,
			UnitOfMeasurement unit_of_measurement, String description, String supplier) throws Exception;

	List<Ingredient> getListIngredient() throws Exception;

	String deleteIngredientByName(String name_ingredients) throws Exception;

	List<Ingredient> getListIngredientBySupplier(String supplier) throws Exception;

	List<Ingredient> getListIngredientByNameASC(String name_ingredients) throws Exception;

	Ingredient findByIngredientId(String id_ingredient) throws Exception;

	Ingredient updateIngredient(String id_ingredient, Optional<Double> prices, Optional<Integer> quantity,
			Optional<UnitOfMeasurement> unit_of_measurement, Optional<String> description, Optional<String> supplier)
			throws Exception;
}
