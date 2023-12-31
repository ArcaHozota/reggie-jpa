package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.DishFlavor;

/**
 * 料理と料理の味の関係リポジトリ
 *
 * @author Administrator
 * @since 2022-11-23
 */
@Repository
public interface DishFlavorRepository extends JpaRepository<DishFlavor, Long>, JpaSpecificationExecutor<DishFlavor> {

	/**
	 * 批量刪除數據
	 *
	 * @param dishIds 菜品ID集合
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByDishIds(@Param("dishIds") List<Long> dishIds);
}
