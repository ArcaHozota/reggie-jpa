package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.SetmealDish;

/**
 * 定食と料理の関係リポジトリ
 *
 * @author Administrator
 * @since 2022-11-29
 */
@Repository
public interface SetmealDishRepository extends JpaRepository<SetmealDish, Long>, JpaSpecificationExecutor<SetmealDish> {

	/**
	 * 根據套餐ID集合批量刪除數據
	 *
	 * @param smIds 套餐ID集合
	 */
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveBySmIds(@Param("smIds") List<Long> smIds);
}
