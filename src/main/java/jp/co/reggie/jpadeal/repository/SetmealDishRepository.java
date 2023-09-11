package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.SetmealDish;
import oracle.jdbc.driver.OracleSQLException;

/**
 * 定食と料理の関係リポジトリ
 *
 * @author Administrator
 * @since 2022-11-29
 */
@Repository
public interface SetmealDishRepository extends JpaRepository<SetmealDish, Long>, JpaSpecificationExecutor<SetmealDish> {

	/**
	 * 根據套餐ID查詢
	 *
	 * @param id 套餐ID
	 * @return List<SetmealDish>
	 */
	List<SetmealDish> selectBySmId(@Param("smId") Long id);

	/**
	 * 根據套餐ID集合批量刪除數據
	 *
	 * @param ids 套餐ID集合
	 */
	@Transactional(rollbackFor = OracleSQLException.class)
	void batchRemoveBySmIds(@Param("smIds") List<Long> ids);
}
