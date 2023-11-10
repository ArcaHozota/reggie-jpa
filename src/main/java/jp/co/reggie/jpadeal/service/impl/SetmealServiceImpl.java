package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.postgresql.util.PSQLException;
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
import jp.co.reggie.jpadeal.dto.SetmealDishDto;
import jp.co.reggie.jpadeal.dto.SetmealDto;
import jp.co.reggie.jpadeal.entity.Setmeal;
import jp.co.reggie.jpadeal.entity.SetmealDish;
import jp.co.reggie.jpadeal.repository.CategoryExRepository;
import jp.co.reggie.jpadeal.repository.SetmealDishRepository;
import jp.co.reggie.jpadeal.repository.SetmealRepository;
import jp.co.reggie.jpadeal.service.SetmealService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.SecondBeanUtils;
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
	 * 分類管理擴展數據接口
	 */
	@Resource
	private CategoryExRepository categoryExRepository;

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
	public void batchUpdateByIds(final String status, final List<Long> stmlIds) {
		final List<Setmeal> setmeals = this.setmealRepository.findAllById(stmlIds).stream().peek(item -> {
			if (StringUtils.isEqual("0", status)) {
				item.setStatus(Constants.STATUS_VALID);
			} else if (StringUtils.isEqual("1", status)) {
				item.setStatus(Constants.STATUS_FORBIDDEN);
			} else {
				throw new CustomException(CustomMessages.ERP022);
			}
			item.setUpdatedTime(LocalDateTime.now());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		this.setmealRepository.saveAllAndFlush(setmeals);
		this.categoryExRepository.refresh();
	}

	@Override
	public SetmealDto getByIdWithDishInfo(final Long id) {
		final Setmeal setmeal = this.setmealRepository.findById(id).orElseThrow(() -> {
			throw new CustomException(CustomMessages.ERP019);
		});
		final SetmealDto setmealDto = new SetmealDto();
		final List<SetmealDishDto> setmealDishDtos = new ArrayList<>();
		setmeal.getSetmealDishes().forEach(item -> {
			final SetmealDishDto setmealDishDto = new SetmealDishDto();
			SecondBeanUtils.copyNullableProperties(item, setmealDto);
			setmealDishDtos.add(setmealDishDto);
		});
		SecondBeanUtils.copyNullableProperties(setmeal, setmealDto);
		setmealDto.setCategoryName(setmeal.getCategory().getName());
		setmealDto.setSetmealDishes(setmealDishDtos);
		return setmealDto;
	}

	@Override
	public Pagination<SetmealDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Direction.DESC, "updatedTime"));
		final Setmeal setmeal = new Setmeal();
		setmeal.setName(keyword);
		setmeal.setDeleteFlg(Constants.LOGIC_FLAG);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", GenericPropertyMatchers.contains())
				.withMatcher("deleteFlg", GenericPropertyMatchers.exact());
		final Example<Setmeal> example = Example.of(setmeal, exampleMatcher);
		final Page<Setmeal> setmeals = this.setmealRepository.findAll(example, pageRequest);
		final List<SetmealDto> setmealDtos = setmeals.getContent().stream().map(item -> {
			final SetmealDto setmealDto = new SetmealDto();
			final List<SetmealDishDto> setmealDishDtos = new ArrayList<>();
			for (final SetmealDish SetmealDish : item.getSetmealDishes()) {
				final SetmealDishDto setmealDishDto = new SetmealDishDto();
				SecondBeanUtils.copyNullableProperties(SetmealDish, setmealDto);
				setmealDishDtos.add(setmealDishDto);
			}
			SecondBeanUtils.copyNullableProperties(item, setmealDto);
			setmealDto.setCategoryName(item.getCategory().getName());
			setmealDto.setSetmealDishes(setmealDishDtos);
			return setmealDto;
		}).collect(Collectors.toList());
		return Pagination.of(setmealDtos, setmeals.getTotalElements(), pageNum, pageSize);
	}

	@Override
	public void removeWithDish(final List<Long> ids) {
		// 查詢套餐狀態以確認是否可以刪除；
		final Integer count = this.setmealRepository.countStatusByIds(ids);
		if (count > 0) {
			// 如果無法刪除，則抛出異常；
			throw new CustomException(CustomMessages.ERP012);
		}
		// 刪除套餐口味表中的數據；
		this.setmealDishRepository.batchRemoveBySmIds(ids);
		// 刪除套餐表中的數據；
		this.setmealRepository.batchRemoveByIds(ids);
		this.categoryExRepository.refresh();
	}

	@Override
	public void saveWithDish(final SetmealDto setmealDto) {
		// 聲明套餐實體類並拷貝屬性；
		final Setmeal setmeal = new Setmeal();
		SecondBeanUtils.copyNullableProperties(setmealDto, setmeal);
		// 保存套餐的基本信息；
		setmeal.setId(BasicContextUtils.getGeneratedId());
		setmeal.setCreatedTime(LocalDateTime.now());
		setmeal.setUpdatedTime(LocalDateTime.now());
		setmeal.setCreatedUser(BasicContextUtils.getCurrentId());
		setmeal.setUpdatedUser(BasicContextUtils.getCurrentId());
		setmeal.setDeleteFlg(Constants.LOGIC_FLAG);
		this.setmealRepository.saveAndFlush(setmeal);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().map(item -> {
			final SetmealDish setmealDish = new SetmealDish();
			SecondBeanUtils.copyNullableProperties(item, setmealDish);
			setmealDish.setId(BasicContextUtils.getGeneratedId());
			setmealDish.setSetmealId(setmeal.getId());
			setmealDish.setSort(RANDOM.nextInt(setmealDto.getSetmealDishes().size()));
			setmealDish.setCreatedTime(LocalDateTime.now());
			setmealDish.setUpdatedTime(LocalDateTime.now());
			setmealDish.setCreatedUser(BasicContextUtils.getCurrentId());
			setmealDish.setUpdatedUser(BasicContextUtils.getCurrentId());
			return setmealDish;
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishRepository.saveAllAndFlush(setmealDishes);
		this.categoryExRepository.refresh();
	}

	@Override
	public void updateWithDish(final SetmealDto setmealDto) {
		// 聲明套餐實體類並拷貝屬性；
		final Setmeal setmeal = new Setmeal();
		SecondBeanUtils.copyNullableProperties(setmealDto, setmeal);
		// 保存套餐的基本信息；
		setmeal.setUpdatedTime(LocalDateTime.now());
		setmeal.setUpdatedUser(BasicContextUtils.getCurrentId());
		this.setmealRepository.saveAndFlush(setmeal);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().map(item -> {
			final SetmealDish setmealDish = new SetmealDish();
			SecondBeanUtils.copyNullableProperties(item, setmealDish);
			setmealDish.setSetmealId(setmealDto.getId());
			setmealDish.setSort(RANDOM.nextInt(setmealDto.getSetmealDishes().size()));
			setmealDish.setUpdatedTime(LocalDateTime.now());
			setmealDish.setUpdatedUser(BasicContextUtils.getCurrentId());
			return setmealDish;
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishRepository.saveAllAndFlush(setmealDishes);
		this.categoryExRepository.refresh();
	}
}
