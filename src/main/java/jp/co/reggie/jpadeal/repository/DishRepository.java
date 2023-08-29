package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Dish;

/**
 * 菜品數據接口
 *
 * @author Administrator
 * @date 2022-11-19
 */
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {

	/**
	 * 更新菜品信息
	 *
	 * @param dishDto 菜品以及口味數據傳輸專用類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void updateById(@Param("entity") Dish dish);

	/**
	 * 根據分類ID查詢
	 *
	 * @param id 分類ID
	 * @return 記錄數
	 */
	Integer countByCategoryId(@Param("categoryId") Long id);

	/**
	 * 根據菜品ID集合批量刪除
	 *
	 * @param dishIdList 菜品ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByIds(@Param("dishIds") List<Long> dishIdList);

	/**
	 * 根據分類ID獲取菜品集合
	 *
	 * @param categoryId 分類ID
	 * @return List<Dish>
	 */
	List<Dish> findDishesByCategoryId(@Param("categoryId") Long categoryId);
}