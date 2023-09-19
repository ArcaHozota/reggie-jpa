package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Dish;

/**
 * 料理リポジトリ
 *
 * @author Administrator
 * @since 2022-11-19
 */
public interface DishRepository extends JpaRepository<Dish, Long>, JpaSpecificationExecutor<Dish> {

	/**
	 * 根據菜品ID集合批量刪除
	 *
	 * @param dishIdList 菜品ID集合
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByIds(@Param("dishIds") List<Long> dishIdList);

	/**
	 * 根據菜品ID查詢狀態
	 *
	 * @param id 菜品ID
	 * @return 記錄數
	 */
	Integer countStatusByIds(@Param("ids") List<Long> ids);

	/**
	 * 根據分類ID查詢
	 *
	 * @param id 分類ID
	 * @return 記錄數
	 */
	Integer countByCategoryId(@Param("categoryId") Long id);

	/**
	 * 根據分類ID獲取菜品集合
	 *
	 * @param categoryId 分類ID
	 * @return List<Dish>
	 */
	List<Dish> findByCategoryId(@Param("categoryId") Long categoryId);
}