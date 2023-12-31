package jp.co.reggie.jpadeal.service;

import java.util.List;

import jp.co.reggie.jpadeal.dto.CategoryDto;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 分類管理服務接口
 *
 * @author Administrator
 */
public interface CategoryService {

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	List<Category> findByType(Integer categoryType);

	/**
	 * 分類信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return Pagination<CategoryDto>
	 */
	Pagination<CategoryDto> pagination(Integer pageNum, Integer pageSize);

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	void remove(Long id);

	/**
	 * 新增分類
	 *
	 * @param category 分類管理數據傳輸類
	 */
	void save(CategoryDto categoryDto);

	/**
	 * 修改分類
	 *
	 * @param category 分類管理數據傳輸類
	 */
	void update(CategoryDto category);
}
