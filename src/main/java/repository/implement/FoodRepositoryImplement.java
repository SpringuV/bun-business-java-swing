package repository.implement;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Food;
import model.Food.FoodType;
import repository.interfaceRepo.BaseDao;
import repository.interfaceRepo.FoodRepository;
import repository.interfaceRepo.SessionUtils;

public class FoodRepositoryImplement extends BaseDao<Food> implements FoodRepository {
	private static final Logger logger = LogManager.getLogger(FoodRepositoryImplement.class);

	@Override
	public boolean createFood(String id_food, String name_food, double prices, String description, String image_path, FoodType type_food,
			boolean is_available) throws Exception {
		Food food = Food.builder().description(description).id_food(id_food).name_food(name_food).image_path(image_path).prices(prices)
				.type_food(type_food).is_available(is_available).build();
		return saveOrUpdate(food) != null;
	}
	
	@Override
	public Food updateFood(String id_food, Optional<String> name_food, Optional<Double> prices, Optional<String> description, Optional<String> image_path, Optional<FoodType> type_food,
			Optional<Boolean> is_available) throws Exception {
		Food food = findById(Food.class, id_food);
		if(food == null) {
			throw new Exception("Food with id '" + id_food + "' not found");
		}
		name_food.ifPresent(food::setName_food);
		prices.ifPresent(food::setPrices);
		description.ifPresent(food::setDescription);
		image_path.ifPresent(food::setImage_path);
		type_food.ifPresent(food::setType_food);
		is_available.ifPresent(food::set_available);
		
		return saveOrUpdate(food);
	}
	
	@Override
	public List<Food> getListFood() throws Exception{
		return findAll(Food.class);
	}

	@Override
	public String deleteFood(String id_food) throws Exception {
		String result = "delete failed";
		try {
			startOperation();
			String hql = "DELETE FROM Food WHERE id_food=:id_food";
			int rowEffected = session.createQuery(hql, Food.class).setParameter("id_food", id_food).executeUpdate();
			if (rowEffected > 0)
				result = "delete success";
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			SessionUtils.rollback(transaction);
			logger.error("Error deleteFood: {}", e.getMessage(), e);
			throw e;
		} finally {
			closeSession();
		}
		return result;
	}

}
