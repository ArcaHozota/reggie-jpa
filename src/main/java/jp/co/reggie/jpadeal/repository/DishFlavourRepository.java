package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.DishFlavour;

/**
 * 料理と料理の味の関係リポジトリ
 *
 * @author Administrator
 * @since 2022-11-23
 */
@Repository
public interface DishFlavourRepository extends JpaRepository<DishFlavour, Long>, JpaSpecificationExecutor<DishFlavour> {

	/**
	 * 根據菜品ID查詢口味信息
	 *
	 * @param dishId 菜品ID
	 * @return 菜品口味列表
	 */
	List<DishFlavour> selectByDishId(@Param("dishId") Long dishId);

	/**
	 * 批量刪除數據
	 *
	 * @param dishIdList 菜品ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByDishIds(@Param("dishIds") List<Long> dishIdList);
}
