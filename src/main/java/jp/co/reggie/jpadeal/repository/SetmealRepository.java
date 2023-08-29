package jp.co.reggie.jpadeal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.dto.SetmealDto;
import jp.co.reggie.jpadeal.entity.Setmeal;

/**
 * 套餐數據接口
 *
 * @author Administrator
 * @date 2022-11-19
 */
public interface SetmealRepository extends JpaRepository<Setmeal, Long> {

	/**
	 * 根據分類ID查詢
	 *
	 * @param id 分類ID
	 * @return 記錄數
	 */
	Integer countByCategoryId(@Param("categoryId") Long id);

	/**
	 * 根據ID集合檢索套餐狀態
	 *
	 * @param ids 套餐ID集合
	 * @return 記錄數
	 */
	Integer getStatusByIds(@Param("smIdList") List<Long> ids);

	/**
	 * 查詢套餐數據
	 *
	 * @param id 套餐ID
	 * @return Setmeal 實體類
	 */
	Setmeal selectById(@Param("id") Long id);

	/**
	 * 保存套餐數據
	 *
	 * @param setmealDto 套餐數據傳輸專用類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void saveById(SetmealDto setmealDto);

	/**
	 * 更新套餐數據
	 *
	 * @param setmealDto 套餐數據傳輸專用類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void updateById(SetmealDto setmealDto);

	/**
	 * 根據ID集合批量刪除套餐
	 *
	 * @param ids 套餐ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByIds(@Param("smIdList") List<Long> ids);

	/**
	 * 根據套餐ID集合批量停發售
	 *
	 * @param smIdList 套餐ID集合
	 * @param status   套餐狀態
	 * @param upTime   更新時間
	 * @param upUser   修改者
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchUpdateByIds(@Param("smIdList") List<Long> smIdList, @Param("status") String status,
			@Param("updateTime") LocalDateTime upTime, @Param("updateUser") Long upUser);
}
