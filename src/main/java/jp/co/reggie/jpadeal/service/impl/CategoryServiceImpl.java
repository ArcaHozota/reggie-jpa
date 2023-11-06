package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.postgresql.util.PSQLException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.repository.CategoryRepository;
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
@Transactional(rollbackFor = PSQLException.class)
public class CategoryServiceImpl implements CategoryService {

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findByType(final Integer categoryType) {
		return this.categoryRepository.selectByType(categoryType);
	}

	@Override
	public Pagination<Category> pagination(final Integer pageNum, final Integer pageSize) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize,
				Sort.by(Sort.Order.asc("sort"), Sort.Order.desc("updatedTime")));
		final Specification<Category> specification = Specification.where((root, query,
				criteriaBuilder) -> criteriaBuilder.equal(root.get("logicDeleteFlg"), Constants.LOGIC_FLAG));
		final Page<Category> categories = this.categoryRepository.findAll(specification, pageRequest);
		return Pagination.of(categories.getContent(), categories.getTotalElements(), pageNum - 1, pageSize);
	}

	@Override
	public void remove(final Long id) {
		// 查詢當前分類是否已經關聯了菜品或者套餐，如果已經關聯抛出一個異常；
		final Category category = this.categoryRepository.findById(id).orElseGet(Category::new);
		if (category.getDishes().isEmpty() || category.getSetmealList().isEmpty()) {
			throw new CustomException(CustomMessages.ERP009);
		}
		// 正常刪除分類；
		this.categoryRepository.removeById(id);
	}

	@Override
	public void save(final Category category) {
		category.setId(BasicContextUtils.getGeneratedId());
		category.setCreatedTime(LocalDateTime.now());
		category.setUpdatedTime(LocalDateTime.now());
		category.setCreatedUser(BasicContextUtils.getCurrentId());
		category.setUpdatedUser(BasicContextUtils.getCurrentId());
		category.setDeleteFlg(Constants.LOGIC_FLAG);
		this.categoryRepository.save(category);
	}

	@Override
	public void update(final Category category) {
		final Category category2 = this.categoryRepository.findById(category.getId()).orElseGet(Category::new);
		category2.setName(category.getName());
		category2.setSort(category.getSort());
		category2.setUpdatedTime(LocalDateTime.now());
		category2.setUpdatedUser(BasicContextUtils.getCurrentId());
		this.categoryRepository.save(category2);
	}
}
