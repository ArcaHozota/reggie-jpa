package jp.co.reggie.jpadeal.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.dto.SetmealDto;
import jp.co.reggie.jpadeal.service.SetmealService;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.Reggie;
import lombok.extern.log4j.Log4j2;

/**
 * 套餐管理控制器
 *
 * @author Administrator
 * @date 2022-11-29
 */
@Log4j2
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	/**
	 * 套餐管理服務接口
	 */
	@Resource
	private SetmealService setmealService;

	/**
	 * 新增套餐
	 *
	 * @param setmealDto 數據傳輸類
	 * @return R.success(套餐新增成功的信息)
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final SetmealDto setmealDto) {
		log.info("套餐信息：{}", setmealDto);
		// 儲存套餐；
		this.setmealService.saveWithDish(setmealDto);
		return Reggie.success(CustomMessages.SRP010);
	}

	/**
	 * 刪除套餐
	 *
	 * @param ids 套餐ID的集合
	 * @return R.success(套餐刪除成功的信息)
	 */
	@DeleteMapping
	public Reggie<String> delete(@RequestParam("ids") final List<Long> ids) {
		log.info("套餐ID：{}", ids);
		this.setmealService.removeWithDish(ids);
		return Reggie.success(CustomMessages.SRP011);
	}

	/**
	 * 修改菜品信息
	 *
	 * @param setmealDto 數據傳輸類對象
	 * @return R.success(套餐更新成功的信息)
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final SetmealDto setmealDto) {
		log.info("套餐信息：{}", setmealDto);
		this.setmealService.updateWithDish(setmealDto);
		return Reggie.success(CustomMessages.SRP021);
	}

	/**
	 * 修改菜品信息
	 *
	 * @param setmealDto 數據傳輸類對象
	 * @return R.success(套餐更新成功的信息)
	 */
	@PutMapping("/status/{status}")
	public Reggie<String> changeStatus(@PathVariable final String status, @RequestParam("ids") final Long[] ids) {
		this.setmealService.batchUpdateByIds(status, Arrays.asList(ids));
		return Reggie.success(CustomMessages.SRP023);
	}

	/**
	 * 根據ID顯示套餐信息
	 *
	 * @param id 套餐ID
	 * @return R.success(套餐信息)
	 */
	@GetMapping("/{id}")
	public Reggie<SetmealDto> getSetmealDto(@PathVariable final Long id) {
		// 根據ID查詢菜品信息以及對應的口味信息；
		final SetmealDto setmealDto = this.setmealService.getByIdWithDishInfo(id);
		return Reggie.success(setmealDto);
	}

	/**
	 * 分頁信息顯示
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param name     檢索文
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Pagination<SetmealDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize,
			@RequestParam(name = "name", required = false) final String keyword) {
		final Pagination<SetmealDto> pageInfo = this.setmealService.pagination(pageNum, pageSize, keyword);
		return Reggie.success(pageInfo);
	}
}
