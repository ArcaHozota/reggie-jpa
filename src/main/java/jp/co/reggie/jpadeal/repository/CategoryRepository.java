package jp.co.reggie.jpadeal.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.entity.Category;

/**
 * 分類管理數據接口
 *
 * @author Administrator
 * @date 2022-11-19
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	List<Category> selectByType(@Param("type") Integer categoryType);

	/**
	 * 分類信息分頁查詢
	 *
	 * @param pageSize 頁面大小
	 * @param offset   偏移量
	 * @return List<Category>
	 */
	List<Category> getCategoryInfos(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

	/**
	 * 檢索分類信息總記錄數
	 *
	 * @return Integer 符合條件的總記錄數
	 */
	Integer getCategoryInfosCnt();

	/**
	 * 檢索分類
	 *
	 * @param id ID
	 * @return Category
	 */
	Category selectById(@Param("id") Long id);

	/**
	 * 新增分類
	 *
	 * @param category 實體類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void saveById(Category category);

	/**
	 * 修改分類
	 *
	 * @param category 實體類
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void updateById(Category category);

	/**
	 * 刪除分類
	 *
	 * @param id 分類ID
	 */
	@Transactional(rollbackFor = PSQLException.class)
	void removeById(@Param("id") Long id);
}
