package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.DishFlavor;

/**
 * 菜品口味數據接口
 *
 * @author Administrator
 * @date 2022-11-23
 */
public interface DishFlavorRepository extends JpaRepository<DishFlavor, Long>, JpaSpecificationExecutor<DishFlavor> {

	/**
	 * 根據菜品ID查詢口味信息
	 *
	 * @param dishId 菜品ID
	 * @return 菜品口味列表
	 */
	List<DishFlavor> selectByDishId(@Param("dishId") Long dishId);

	/**
	 * 批量插入數據
	 *
	 * @param flavors 菜品口味實體類集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchInsert(@Param("flavors") List<DishFlavor> flavors);

	/**
	 * 批量更新數據
	 *
	 * @param flavors 菜品口味實體類集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchUpdateByDishId(@Param("flavors") List<DishFlavor> flavors);

	/**
	 * 批量刪除數據
	 *
	 * @param dishIdList 菜品ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByDishId(@Param("dishIdList") List<Long> dishIdList);
}
