package jp.co.reggie.jpadeal.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.reggie.jpadeal.common.CommonMessages;
import jp.co.reggie.jpadeal.dto.CategoryDto;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.service.CategoryService;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.Reggie;
import lombok.extern.log4j.Log4j2;

/**
 * 分類管理控制器
 *
 * @author Administrator
 */
@Log4j2
@RestController
@RequestMapping("/category")
public class CategoryController {

	/**
	 * 分類管理服務接口
	 */
	@Resource
	private CategoryService categoryService;

	/**
	 * 刪除分類
	 *
	 * @param id 分類ID
	 * @return R.success(分類刪除成功的信息);
	 */
	@DeleteMapping
	public Reggie<String> delete(@RequestParam("ids") final Long id) {
		log.info("刪除ID={}的分類", id);
		// 實施刪除；
		this.categoryService.remove(id);
		return Reggie.success(CommonMessages.SRP003);
	}

	/**
	 * 分頁信息顯示
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Pagination<CategoryDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize) {
		// 執行查詢；
		final Pagination<CategoryDto> pageInfo = this.categoryService.pagination(pageNum, pageSize);
		return Reggie.success(pageInfo);
	}

	/**
	 * 根據條件查詢分類數據
	 *
	 * @param category 實體類對象
	 * @return R.success(分類結果的集合)
	 */
	@GetMapping("/list")
	public Reggie<List<Category>> queryList(final CategoryDto categoryDto) {
		// 查詢分類結果集並返回；
		final List<Category> list = this.categoryService.findByType(categoryDto.getType());
		return Reggie.success(list);
	}

	/**
	 * 新增分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類新增成功的信息);
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final CategoryDto categoryDto) {
		log.info("category:{}", categoryDto);
		this.categoryService.save(categoryDto);
		return Reggie.success(CommonMessages.SRP001);
	}

	/**
	 * 更新分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類更新成功的信息);
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final CategoryDto categoryDto) {
		log.info("修改分類信息：{}", categoryDto);
		this.categoryService.update(categoryDto);
		return Reggie.success(CommonMessages.SRP002);
	}
}
