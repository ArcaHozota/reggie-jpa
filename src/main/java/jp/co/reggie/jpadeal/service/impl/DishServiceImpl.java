package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import jp.co.reggie.jpadeal.common.ReggieException;
import jp.co.reggie.jpadeal.common.CommonMessages;
import jp.co.reggie.jpadeal.dto.DishDto;
import jp.co.reggie.jpadeal.dto.DishFlavorDto;
import jp.co.reggie.jpadeal.entity.Dish;
import jp.co.reggie.jpadeal.entity.DishFlavor;
import jp.co.reggie.jpadeal.repository.CategoryExRepository;
import jp.co.reggie.jpadeal.repository.DishFlavorRepository;
import jp.co.reggie.jpadeal.repository.DishRepository;
import jp.co.reggie.jpadeal.service.DishService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;
import jp.co.reggie.jpadeal.utils.SecondBeanUtils;
import jp.co.reggie.jpadeal.utils.StringUtils;

/**
 * 菜品管理服務實現類
 *
 * @author Administrator
 * @since 2022-11-19
 */
@Service
@Transactional(rollbackFor = PSQLException.class)
public class DishServiceImpl implements DishService {

	/**
	 * 菜品數據接口
	 */
	@Resource
	private DishRepository dishRepository;

	/**
	 * 分類管理擴展數據接口
	 */
	@Resource
	private CategoryExRepository categoryExRepository;

	/**
	 * 菜品口味數據接口
	 */
	@Resource
	private DishFlavorRepository dishFlavorRepository;

	@Override
	public void batchUpdateByIds(final String status, final List<Long> ids) {
		final List<Dish> dishes = this.dishRepository.findAllById(ids).stream().peek(dish -> {
			if (StringUtils.isEqual("0", status)) {
				dish.setStatus(Constants.STATUS_VALID);
			} else if (StringUtils.isEqual("1", status)) {
				dish.setStatus(Constants.STATUS_FORBIDDEN);
			} else {
				throw new ReggieException(CommonMessages.ERP017);
			}
			dish.setUpdatedTime(LocalDateTime.now());
			dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		this.dishRepository.saveAllAndFlush(dishes);
		this.categoryExRepository.refresh();
	}

	@Override
	public DishDto getByIdWithFlavour(final Long id) {
		// 查詢菜品的基本信息；
		final Dish probe = new Dish();
		probe.setId(id);
		probe.setDeleteFlg(Constants.LOGIC_FLAG);
		final Example<Dish> example = Example.of(probe, ExampleMatcher.matchingAll());
		final Dish dish = this.dishRepository.findOne(example).orElseThrow(() -> {
			throw new ReggieException(CommonMessages.ERP019);
		});
		// 聲明一個菜品及口味數據傳輸類對象並拷貝屬性；
		final DishDto dishDto = new DishDto();
		final List<DishFlavorDto> dishFlavorDtos = new ArrayList<>();
		dish.getDishFlavors().forEach(item -> {
			final DishFlavorDto dishFlavorDto = new DishFlavorDto();
			SecondBeanUtils.copyNullableProperties(item, dishFlavorDto);
			dishFlavorDtos.add(dishFlavorDto);
		});
		SecondBeanUtils.copyNullableProperties(dish, dishDto);
		// 設置分類名稱；
		dishDto.setCategoryName(dish.getCategory().getName());
		dishDto.setFlavors(dishFlavorDtos);
		return dishDto;
	}

	@Override
	public List<DishDto> getListByCategoryId(final Long categoryId) {
		// 查詢菜品信息；
		final List<Dish> dishes = this.dishRepository.findByCategoryId(categoryId);
		// 獲取菜品及口味數據傳輸類；
		return dishes.stream().map(item -> {
			// 聲明菜品及口味數據傳輸類對象；
			final DishDto dishDto = new DishDto();
			final List<DishFlavorDto> dishFlavorDtos = new ArrayList<>();
			for (final DishFlavor dishFlavor : item.getDishFlavors()) {
				final DishFlavorDto dishFlavorDto = new DishFlavorDto();
				SecondBeanUtils.copyNullableProperties(dishFlavor, dishFlavorDto);
				dishFlavorDtos.add(dishFlavorDto);
			}
			// 拷貝除分類名稱以外的屬性；
			SecondBeanUtils.copyNullableProperties(item, dishDto);
			// 設置分類名稱；
			dishDto.setCategoryName(item.getCategory().getName());
			dishDto.setFlavors(dishFlavorDtos);
			return dishDto;
		}).collect(Collectors.toList());
	}

	@Override
	public Pagination<DishDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Direction.DESC, "updatedTime"));
		final Dish dish = new Dish();
		dish.setName(StringUtils.toHankaku(keyword));
		dish.setDeleteFlg(Constants.LOGIC_FLAG);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", GenericPropertyMatchers.contains())
				.withMatcher("deleteFlg", GenericPropertyMatchers.exact());
		final Example<Dish> example = Example.of(dish, exampleMatcher);
		final Page<Dish> dishes = this.dishRepository.findAll(example, pageRequest);
		final List<DishDto> dishDtos = dishes.getContent().stream().map(item -> {
			final DishDto dishDto = new DishDto();
			final List<DishFlavorDto> dishFlavorDtos = new ArrayList<>();
			for (final DishFlavor dishFlavor : item.getDishFlavors()) {
				final DishFlavorDto dishFlavorDto = new DishFlavorDto();
				SecondBeanUtils.copyNullableProperties(dishFlavor, dishFlavorDto);
				dishFlavorDtos.add(dishFlavorDto);
			}
			SecondBeanUtils.copyNullableProperties(item, dishDto);
			dishDto.setCategoryName(item.getCategory().getName());
			dishDto.setFlavors(dishFlavorDtos);
			return dishDto;
		}).collect(Collectors.toList());
		return Pagination.of(dishDtos, dishes.getTotalElements(), pageNum, pageSize);
	}

	@Override
	public void remove(final List<Long> ids) {
		final Integer countStatusByIds = this.dishRepository.countStatusByIds(ids);
		if (countStatusByIds > 0) {
			throw new ReggieException(CommonMessages.ERP024);
		}
		// 刪除菜品口味數據；
		this.dishFlavorRepository.batchRemoveByDishIds(ids);
		// 刪除菜品信息；
		this.dishRepository.batchRemoveByIds(ids);
		this.categoryExRepository.refresh();
	}

	@Override
	public void saveWithFlavours(final DishDto dishDto) {
		// 保存菜品的基本信息到菜品表；
		final Dish dish = new Dish();
		SecondBeanUtils.copyNullableProperties(dishDto, dish);
		dish.setId(BasicContextUtils.getGeneratedId());
		dish.setCreatedTime(LocalDateTime.now());
		dish.setUpdatedTime(LocalDateTime.now());
		dish.setCreatedUser(BasicContextUtils.getCurrentId());
		dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		dish.setDeleteFlg(Constants.LOGIC_FLAG);
		this.dishRepository.saveAndFlush(dish);
		// 獲取菜品口味的集合並將菜品ID設置到口味集合中；
		final List<DishFlavor> dishFlavors = dishDto.getFlavors().stream().map(item -> {
			final DishFlavor dishFlavor = new DishFlavor();
			SecondBeanUtils.copyNullableProperties(item, dishFlavor);
			dishFlavor.setId(BasicContextUtils.getGeneratedId());
			dishFlavor.setDishId(dishDto.getId());
			dishFlavor.setCreatedTime(LocalDateTime.now());
			dishFlavor.setUpdatedTime(LocalDateTime.now());
			dishFlavor.setCreatedUser(BasicContextUtils.getCurrentId());
			dishFlavor.setUpdatedUser(BasicContextUtils.getCurrentId());
			return dishFlavor;
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		this.dishFlavorRepository.saveAllAndFlush(dishFlavors);
		this.categoryExRepository.refresh();
	}

	@Override
	public void updateWithFlavours(final DishDto dishDto) {
		// 聲明菜品實體類；
		final Dish dish = new Dish();
		SecondBeanUtils.copyNullableProperties(dishDto, dish);
		dish.setUpdatedTime(LocalDateTime.now());
		dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		// 更新菜品信息；
		this.dishRepository.saveAndFlush(dish);
		// 添加當前菜品的口味數據並將菜品ID設置到口味集合中；
		final List<DishFlavor> flavours = dishDto.getFlavors().stream().map(item -> {
			final DishFlavor dishFlavor = new DishFlavor();
			SecondBeanUtils.copyNullableProperties(item, dishFlavor);
			item.setDishId(dishDto.getId());
			item.setUpdatedTime(LocalDateTime.now());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
			return dishFlavor;
		}).collect(Collectors.toList());
		// 更新菜品口味信息；
		this.dishFlavorRepository.saveAllAndFlush(flavours);
		this.categoryExRepository.refresh();
	}
}
