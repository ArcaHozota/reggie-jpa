package jp.co.reggie.jpadeal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.repository.CategoryRepository;
import jp.co.reggie.jpadeal.repository.DishRepository;
import jp.co.reggie.jpadeal.repository.SetmealMapper;
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
@Transactional
public class CategoryServiceImpl implements CategoryService {

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryMapper;

	/**
	 * 菜品數據接口
	 */
	@Resource
	private DishRepository dishMapper;

	/**
	 * 套餐數據接口
	 */
	@Resource
	private SetmealMapper setmealMapper;

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	@Override
	public List<Category> findByType(final Integer categoryType) {
		return this.categoryMapper.selectByType(categoryType);
	}

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	@Override
	public void remove(final Long id) {
		// 查詢當前分類是否已經關聯了菜品，如果已經關聯抛出一個異常；
		final long count1 = this.dishMapper.countByCategoryId(id);
		if (count1 > 0) {
			throw new CustomException(CustomMessages.ERP009);
		}
		// 查詢當前分類是否已經關聯了套餐，如果已經關聯抛出一個異常；
		final long count2 = this.setmealMapper.countByCategoryId(id);
		if (count2 > 0) {
			throw new CustomException(CustomMessages.ERP009);
		}
		// 正常刪除分類；
		this.categoryMapper.removeById(id);
	}

	/**
	 * 新增分類
	 *
	 * @param category 實體類
	 */
	@Override
	public void save(final Category category) {
		BasicContextUtils.fillWithInsert(category);
		category.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.categoryMapper.saveById(category);
	}

	/**
	 * 修改分類
	 *
	 * @param category 實體類
	 */
	@Override
	public void update(final Category category) {
		BasicContextUtils.fillWithUpdate(category);
		this.categoryMapper.updateById(category);
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
		final Integer offset = (pageNum - 1) * pageSize;
		final Integer categoryInfosCnt = this.categoryMapper.getCategoryInfosCnt();
		if (categoryInfosCnt == 0) {
			return Pagination.of(new ArrayList<>(), categoryInfosCnt, pageNum, pageSize);
		}
		final List<Category> categoryInfos = this.categoryMapper.getCategoryInfos(pageSize, offset);
		return Pagination.of(categoryInfos, categoryInfosCnt, pageNum, pageSize);
	}
}
