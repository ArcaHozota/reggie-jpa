package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.postgresql.util.PSQLException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.dto.SetmealDto;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.entity.Setmeal;
import jp.co.reggie.jpadeal.entity.SetmealDish;
import jp.co.reggie.jpadeal.repository.CategoryRepository;
import jp.co.reggie.jpadeal.repository.SetmealDishRepository;
import jp.co.reggie.jpadeal.repository.SetmealRepository;
import jp.co.reggie.jpadeal.service.SetmealService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.StringUtils;

/**
 * 套餐管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-19
 */
@Service
@Transactional(rollbackFor = PSQLException.class)
public class SetmealServiceImpl implements SetmealService {

	private static final Random RANDOM = new Random();

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryRepository;

	/**
	 * 套餐數據接口類
	 */
	@Resource
	private SetmealRepository setmealRepository;

	/**
	 * 套餐與菜品關係數據接口類
	 */
	@Resource
	private SetmealDishRepository setmealDishRepository;

	@Override
	public void saveWithDish(final SetmealDto setmealDto) {
		// 聲明套餐實體類並拷貝屬性；
		final Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDto, setmeal, "categoryName", "setmealDishes");
		// 保存套餐的基本信息；
		setmeal.setId(BasicContextUtils.getGeneratedId());
		setmeal.setCreatedTime(LocalDateTime.now());
		setmeal.setUpdatedTime(LocalDateTime.now());
		setmeal.setCreatedUser(BasicContextUtils.getCurrentId());
		setmeal.setUpdatedUser(BasicContextUtils.getCurrentId());
		setmeal.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.setmealRepository.save(setmeal);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
			item.setId(BasicContextUtils.getGeneratedId());
			item.setSetmealId(setmealDto.getId());
			item.setCreatedTime(LocalDateTime.now());
			item.setUpdatedTime(LocalDateTime.now());
			item.setCreatedUser(BasicContextUtils.getCurrentId());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
			item.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishRepository.saveAll(setmealDishes);
	}

	@Override
	public void removeWithDish(final List<Long> ids) {
		// 查詢套餐狀態以確認是否可以刪除；
		final Integer count = this.setmealRepository.countStatusByIds(ids);
		if (count > 0) {
			// 如果無法刪除，則抛出異常；
			throw new CustomException(CustomMessages.ERP012);
		}
		// 刪除套餐表中的數據；
		this.setmealRepository.batchRemoveByIds(ids);
		// 刪除套餐口味表中的數據；
		this.setmealDishRepository.batchRemoveBySmIds(ids);
	}

	@Override
	public Pagination<SetmealDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Direction.DESC, "updatedTime"));
		final Setmeal setmeal = new Setmeal();
		setmeal.setName(keyword);
		setmeal.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", GenericPropertyMatchers.contains())
				.withMatcher("logicDeleteFlg", GenericPropertyMatchers.exact());
		final Example<Setmeal> example = Example.of(setmeal, exampleMatcher);
		final Page<Setmeal> setmeals = this.setmealRepository.findAll(example, pageRequest);
		final List<SetmealDto> setmealDtos = setmeals.getContent().stream().map(item -> {
			final SetmealDto aDto = new SetmealDto();
			BeanUtils.copyProperties(item, aDto);
			final List<SetmealDish> setmealDishes = this.setmealDishRepository.selectBySmId(item.getId());
			Category category = new Category();
			category.setId(item.getCategoryId());
			category.setLogicDeleteFlg(Constants.LOGIC_FLAG);
			final Example<Category> categoryExample = Example.of(category, ExampleMatcher.matchingAll());
			category = this.categoryRepository.findOne(categoryExample).orElseGet(Category::new);
			aDto.setSetmealDishes(setmealDishes);
			aDto.setCategoryName(category.getName());
			return aDto;
		}).collect(Collectors.toList());
		return Pagination.of(setmealDtos, setmeals.getTotalElements(), pageNum - 1, pageSize);
	}

	@Override
	public SetmealDto getByIdWithDishInfo(final Long id) {
		final Setmeal setmeal = this.setmealRepository.findById(id).orElseGet(Setmeal::new);
		final SetmealDto setmealDto = new SetmealDto();
		BeanUtils.copyProperties(setmeal, setmealDto);
		final Category category = this.categoryRepository.findById(setmeal.getCategoryId()).orElseGet(Category::new);
		final List<SetmealDish> setmealDishes = this.setmealDishRepository.selectBySmId(id);
		setmealDto.setCategoryName(category.getName());
		setmealDto.setSetmealDishes(setmealDishes);
		return setmealDto;
	}

	@Override
	public void updateWithDish(final SetmealDto setmealDto) {
		// 聲明套餐實體類並拷貝屬性；
		final Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDto, setmeal, "categoryName", "setmealDishes");
		// 保存套餐的基本信息；
		setmeal.setUpdatedTime(LocalDateTime.now());
		setmeal.setUpdatedUser(BasicContextUtils.getCurrentId());
		this.setmealRepository.save(setmeal);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
			item.setSetmealId(setmealDto.getId());
			item.setSort(RANDOM.nextInt(setmealDto.getSetmealDishes().size()));
			item.setUpdatedTime(LocalDateTime.now());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishRepository.saveAll(setmealDishes);
	}

	@Override
	public void batchUpdateByIds(final String status, final List<Long> stmlList) {
		final LocalDateTime upTime = LocalDateTime.now();
		final Long upUserId = BasicContextUtils.getCurrentId();
		Integer newStatus;
		if (StringUtils.isEqual("0", status)) {
			newStatus = Constants.STATUS_VALID;
		} else if (StringUtils.isEqual("1", status)) {
			newStatus = Constants.STATUS_FORBIDDEN;
		} else {
			throw new CustomException(CustomMessages.ERP022);
		}
		this.setmealRepository.batchUpdateByIds(stmlList, newStatus, upTime, upUserId);
	}
}
