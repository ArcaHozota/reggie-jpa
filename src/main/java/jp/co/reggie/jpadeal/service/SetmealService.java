package jp.co.reggie.jpadeal.service;

import java.util.List;

import jp.co.reggie.jpadeal.dto.SetmealDto;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 套餐管理服務接口
 *
 * @author Administrator
 */
public interface SetmealService {

	/**
	 * 新增套餐，同時保存套餐和菜品的關聯
	 *
	 * @param setmealDto 數據傳輸類
	 */
	void saveWithDish(SetmealDto setmealDto);

	/**
	 * 更新套餐，同時更新套餐和菜品的關聯
	 *
	 * @param setmealDto 數據傳輸類
	 */
	void updateWithDish(SetmealDto setmealDto);

	/**
	 * 刪除套餐，同時刪除套餐和菜品的關聯
	 *
	 * @param ids 套餐ID的集合
	 */
	void removeWithDish(List<Long> ids);

	/**
	 * 套餐信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<SetmealDto>
	 */
	Pagination<SetmealDto> pagination(Integer pageNum, Integer pageSize, String keyword);

	/**
	 * 根據ID顯示套餐信息
	 *
	 * @param id 套餐ID
	 * @return SetmealDto
	 */
	SetmealDto getByIdWithDishInfo(Long id);

	/**
	 * 根據套餐集合批量停發售
	 *
	 * @param status   在售狀態
	 * @param stmlList 套餐集合
	 */
	void batchUpdateByIds(String status, List<Long> stmlList);
}
