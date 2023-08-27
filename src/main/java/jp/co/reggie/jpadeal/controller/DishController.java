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
import jp.co.reggie.jpadeal.dto.DishDto;
import jp.co.reggie.jpadeal.service.DishService;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.Reggie;
import lombok.extern.log4j.Log4j2;

/**
 * 菜品管理控制器
 *
 * @author Administrator
 */
@Log4j2
@RestController
@RequestMapping("/dish")
public class DishController {

	/**
	 * 菜品管理服務接口
	 */
	@Resource
	private DishService dishService;

	/**
	 * 新增菜品
	 *
	 * @param dishDto 數據傳輸類對象
	 * @return R.success(成功新增菜品的信息)
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final DishDto dishDto) {
		log.info("新增菜品：{}" + dishDto.toString());
		this.dishService.saveWithFlavours(dishDto);
		return Reggie.success(CustomMessages.SRP004);
	}

	/**
	 * 菜品信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Pagination<DishDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize,
			@RequestParam(name = "name", required = false) final String keyword) {
		final Pagination<DishDto> dtoPage = this.dishService.pagination(pageNum, pageSize, keyword);
		return Reggie.success(dtoPage);
	}

	/**
	 * 根據ID顯示菜品信息
	 *
	 * @param id 菜品ID
	 * @return R.success(菜品信息)
	 */
	@GetMapping("/{id}")
	public Reggie<DishDto> getDishInfo(@PathVariable final Long id) {
		// 根據ID查詢菜品信息以及對應的口味信息；
		final DishDto dishDto = this.dishService.getByIdWithFlavour(id);
		return Reggie.success(dishDto);
	}

	/**
	 * 刪除菜品信息
	 *
	 * @param ids 菜品ID集合
	 * @return R.success(菜品刪除成功的信息)
	 */
	@DeleteMapping
	public Reggie<String> delete(@RequestParam("ids") final Long[] ids) {
		final List<Long> idList = Arrays.asList(ids);
		log.info("即將刪除菜品：", idList);
		this.dishService.remove(idList);
		return Reggie.success(CustomMessages.SRP005);
	}

	/**
	 * 修改菜品信息
	 *
	 * @param dishDto 數據傳輸類對象
	 * @return R.success(菜品更新成功的信息)
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final DishDto dishDto) {
		log.info(dishDto.toString());
		this.dishService.updateWithFlavour(dishDto);
		return Reggie.success(CustomMessages.SRP020);
	}

	/**
	 * 修改菜品在售狀態
	 *
	 * @param status 菜品狀態
	 * @return R.success(修改成功信息)
	 */
	@PutMapping("/status/{status}")
	public Reggie<String> changeStatus(@PathVariable final String status, @RequestParam("ids") final Long[] ids) {
		this.dishService.batchUpdateByIds(status, Arrays.asList(ids));
		return Reggie.success(CustomMessages.SRP016);
	}

	/**
	 * 回顯菜品表單數據
	 *
	 * @param dish 實體類對象
	 * @return R.success(菜品信息)
	 */
	@GetMapping("/list")
	public Reggie<List<DishDto>> getList(@RequestParam("categoryId") final Long categoryId) {
		final List<DishDto> dtoList = this.dishService.getListByCategoryId(categoryId);
		return Reggie.success(dtoList);
	}
}
