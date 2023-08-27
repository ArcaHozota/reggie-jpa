package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.postgresql.util.PSQLException;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.newdeal.entity.SetmealDish;

/**
 * 套餐與菜品關係數據接口
 *
 * @author Administrator
 * @date 2022-11-29
 */
@Mapper
public interface SetmealDishMapper {

	/**
	 * 批量插入數據
	 *
	 * @param setmealDishes 套餐與菜品關係實體類集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchInsert(@Param("dishes") List<SetmealDish> setmealDishes);

	/**
	 * 根據套餐ID集合批量刪除數據
	 *
	 * @param ids 套餐ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveBySmIds(@Param("smIdList") List<Long> ids);

	/**
	 * 根據套餐ID查詢
	 *
	 * @param id 套餐ID
	 * @return List<SetmealDish>
	 */
	List<SetmealDish> selectBySmId(@Param("smId") Long id);

	/**
	 * 根據套餐集合批量更新數據
	 *
	 * @param setmealDishes 套餐集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchUpdateBySmIds(@Param("dishes") List<SetmealDish> setmealDishes);
}
