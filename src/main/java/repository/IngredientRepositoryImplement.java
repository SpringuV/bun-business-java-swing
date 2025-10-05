package repository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Ingredient;
import model.Ingredient.UnitOfMeasurement;

public class IngredientRepositoryImplement extends BaseDao<Ingredient> {
	private static final Logger logger = LogManager.getLogger(IngredientRepositoryImplement.class);

	public boolean createIngredient(String name_ingredients, double prices, int quantity,
			UnitOfMeasurement unit_of_measurement, String description, String supplier) throws Exception {
		Ingredient ingredient = Ingredient.builder().description(description).name_ingredients(name_ingredients)
				.prices(prices).unit_of_measurement(unit_of_measurement).quantity(quantity).supplier(supplier).build();
		return saveOrUpdate(ingredient) != null;
	}

	public List<Ingredient> getListIngredient() throws Exception {
		return findAll(Ingredient.class);
	}

	public String deleteIngredientByName(String name_ingredients) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Ingredient WHERE name_ingredients=:name_ingredients";
			int rowEffected = session.createQuery(hql, Integer.class).setParameter("name_ingredients", name_ingredients)
					.executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteIngredientByName: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

	public List<Ingredient> getListIngredientBySupplier(String supplier) throws Exception {
		List<Ingredient> ingredient_list;
		try {
			startOperation();
			String hql = "FROM Ingredient WHERE supplier=:supplier";
			ingredient_list = session.createQuery(hql, Ingredient.class).setParameter("supplier", supplier).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListIngredientBySupplier: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return ingredient_list;
	}

	public List<Ingredient> getListIngredientByNameASC(String name_ingredients) throws Exception {
		List<Ingredient> ingredient_list;
		try {
			startOperation();
			String hql = "FROM Ingredient i WHERE i.name_ingredients=:name_ingredients ORDER BY i.prices ASC";
			ingredient_list = session.createQuery(hql, Ingredient.class)
					.setParameter("name_ingredients", name_ingredients).list();
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error getListIngredientByName: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return ingredient_list;
	}

	public Ingredient findByIngredientId(String id_ingredient) throws Exception {
		return findById(Ingredient.class, id_ingredient);
	}

	public Ingredient updateIngredient(String id_ingredient, Optional<Double> prices,
	        Optional<Integer> quantity,
	        Optional<UnitOfMeasurement> unit_of_measurement,
	        Optional<String> description,
	        Optional<String> supplier) throws Exception {
		Ingredient ingredient = findByIngredientId(id_ingredient);
		if (ingredient == null) {
			throw new Exception("Ingredient with id '" + id_ingredient + "' not found");
		}
		// Cập nhật các thuộc tính
		prices.ifPresent(ingredient::setPrices);
	    quantity.ifPresent(ingredient::setQuantity);
	    unit_of_measurement.ifPresent(ingredient::setUnit_of_measurement);
	    description.ifPresent(ingredient::setDescription);
	    supplier.ifPresent(ingredient::setSupplier);
		// update
		return saveOrUpdate(ingredient);
	}
}
