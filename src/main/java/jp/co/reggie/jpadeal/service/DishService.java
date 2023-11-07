package jp.co.reggie.jpadeal.service;

import java.util.List;

import jp.co.reggie.jpadeal.dto.DishDto;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 菜品管理服務接口
 *
 * @author Administrator
 */
public interface DishService {

	/**
	 * 根據菜品集合批量停發售
	 *
	 * @param status 在售狀態
	 * @param ids    菜品ID集合
	 */
	void batchUpdateByIds(String status, List<Long> ids);

	/**
	 * 根據ID查詢菜品信息以及對應的口味信息
	 *
	 * @param id 菜品ID
	 * @return dishDto 菜品及口味數據傳輸類
	 */
	DishDto getByIdWithFlavour(Long id);

	/**
	 * 根據分類ID回顯菜品表單數據
	 *
	 * @param categoryId 分類ID
	 * @return List<DishDto>
	 */
	List<DishDto> getListByCategoryId(Long categoryId);

	/**
	 * 菜品信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<DishDto>
	 */
	Pagination<DishDto> pagination(Integer pageNum, Integer pageSize, String keyword);

	/**
	 * 根據ID批量刪除菜品
	 *
	 * @param ids 菜品ID集合
	 */
	void remove(List<Long> ids);

	/**
	 * 新增菜品，同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	void saveWithFlavours(DishDto dishDto);

	/**
	 * 修改菜品信息並同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	void updateWithFlavour(DishDto dishDto);
}
