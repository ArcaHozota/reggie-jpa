package jp.co.reggie.jpadeal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Setmeal;

/**
 * 定食リポジトリ
 *
 * @author Administrator
 * @since 2022-11-19
 */
@Repository
public interface SetmealRepository extends JpaRepository<Setmeal, Long>, JpaSpecificationExecutor<Setmeal> {

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
	Integer countStatusByIds(@Param("smIdList") List<Long> ids);

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
	@Modifying
	@Transactional(rollbackFor = PSQLException.class)
	void batchUpdateByIds(@Param("smIdList") List<Long> smIdList, @Param("status") Integer status,
			@Param("updatedTime") LocalDateTime upTime, @Param("updatedUser") Long upUser);
}
