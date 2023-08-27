package jp.co.reggie.jpadeal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.dto.DishDto;
import jp.co.reggie.jpadeal.entity.Dish;

/**
 * 菜品數據接口
 *
 * @author Administrator
 * @date 2022-11-19
 */
public interface DishRepository extends JpaRepository<Dish, Long> {

	/**
	 * 根據菜品ID集合批量停發售
	 *
	 * @param dishIdList 菜品ID集合
	 * @param status     菜品狀態
	 * @param upTime     更新時間
	 * @param upUser     修改者
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchUpdateByIds(@Param("dishIdList") List<Long> dishIdList, @Param("status") String status,
			@Param("updateTime") LocalDateTime upTime, @Param("updateUser") Long upId);

	/**
	 * 添加菜品信息
	 *
	 * @param dishDto 菜品以及口味數據傳輸專用類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void saveById(DishDto dishDto);

	/**
	 * 更新菜品信息
	 *
	 * @param dishDto 菜品以及口味數據傳輸專用類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void updateById(DishDto dishDto);

	/**
	 * 根據分類ID查詢
	 *
	 * @param id 分類ID
	 * @return 記錄數
	 */
	Integer countByCategoryId(@Param("categoryId") Long id);

	/**
	 * 根據ID查詢
	 *
	 * @param id ID
	 * @return Dish 實體類
	 */
	Dish selectById(@Param("id") Long id);

	/**
	 * 菜品信息分頁查詢
	 *
	 * @param pageSize 頁面大小
	 * @param offset   偏移量
	 * @param keyword  檢索文
	 * @return List<DishDto>
	 */
	List<Dish> getDishInfos(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset,
			@Param("keyword") String keyword);

	/**
	 * 檢索菜品信息總記錄數
	 *
	 * @param keyword 檢索文
	 * @return Integer 符合條件的總記錄數
	 */
	Integer getDishInfosCnt(@Param("keyword") String keyword);

	/**
	 * 根據菜品ID集合批量刪除
	 *
	 * @param dishIdList 菜品ID集合
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void batchRemoveByIds(@Param("dishIdList") List<Long> dishIdList);

	/**
	 * 根據分類ID獲取菜品集合
	 *
	 * @param categoryId 分類ID
	 * @return List<Dish>
	 */
	List<Dish> findDishesByCategoryId(@Param("categoryId") Long categoryId);
}
