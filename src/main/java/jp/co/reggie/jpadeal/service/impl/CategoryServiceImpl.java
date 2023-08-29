package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.repository.CategoryRepository;
import jp.co.reggie.jpadeal.repository.DishRepository;
import jp.co.reggie.jpadeal.repository.SetmealRepository;
import jp.co.reggie.jpadeal.service.CategoryService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;

/**
 * 分類管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-19
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryRepository;

	/**
	 * 菜品數據接口
	 */
	@Resource
	private DishRepository dishRepository;

	/**
	 * 套餐數據接口
	 */
	@Resource
	private SetmealRepository setmealRepository;

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	@Override
	public List<Category> findByType(final Integer categoryType) {
		return this.categoryRepository.selectByType(categoryType);
	}

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	@Override
	public void remove(final Long id) {
		// 查詢當前分類是否已經關聯了菜品或者套餐，如果已經關聯抛出一個異常；
		final long count1 = this.dishRepository.countByCategoryId(id);
		final long count2 = this.setmealRepository.countByCategoryId(id);
		if (count1 > 0 || count2 > 0) {
			throw new CustomException(CustomMessages.ERP009);
		}
		// 正常刪除分類；
		this.categoryRepository.removeById(id);
	}

	/**
	 * 新增分類
	 *
	 * @param category 實體類
	 */
	@Override
	public void save(final Category category) {
		category.setId(BasicContextUtils.getGeneratedId());
		category.setCreationTime(LocalDateTime.now());
		category.setUpdatingTime(LocalDateTime.now());
		category.setCreationUser(BasicContextUtils.getCurrentId());
		category.setUpdatingUser(BasicContextUtils.getCurrentId());
		category.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.categoryRepository.save(category);
	}

	/**
	 * 修改分類
	 *
	 * @param category 實體類
	 */
	@Override
	public void update(final Category category) {
		category.setUpdatingTime(LocalDateTime.now());
		category.setUpdatingUser(BasicContextUtils.getCurrentId());
		this.categoryRepository.save(category);
	}

	/**
	 * 分類信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return Pagination<Category>
	 */
	@Override
	public Pagination<Category> pagination(final Integer pageNum, final Integer pageSize) {
		final PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
		final Page<Category> categories = this.categoryRepository.findAll(pageRequest);
		return Pagination.of(categories.getContent(), categories.getTotalElements(), pageNum, pageSize);
	}
}
