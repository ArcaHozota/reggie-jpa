package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import jp.co.reggie.jpadeal.common.Constants;
import jp.co.reggie.jpadeal.common.CustomException;
import jp.co.reggie.jpadeal.common.CustomMessages;
import jp.co.reggie.jpadeal.dto.DishDto;
import jp.co.reggie.jpadeal.entity.Category;
import jp.co.reggie.jpadeal.entity.Dish;
import jp.co.reggie.jpadeal.entity.DishFlavor;
import jp.co.reggie.jpadeal.repository.CategoryRepository;
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
public class DishServiceImpl implements DishService {

	/**
	 * 菜品數據接口
	 */
	@Resource
	private DishRepository dishRepository;

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryRepository;

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
				throw new CustomException(CustomMessages.ERP017);
			}
			dish.setUpdatedTime(LocalDateTime.now());
			dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		this.dishRepository.saveAllAndFlush(dishes);
	}

	@Override
	public DishDto getByIdWithFlavour(final Long id) {
		// 查詢菜品的基本信息；
		final Dish dish = new Dish();
		dish.setId(id);
		dish.setDeleteFlg(Constants.LOGIC_FLAG);
		final Example<Dish> example = Example.of(dish, ExampleMatcher.matchingAll());
		final Dish newDish = this.dishRepository.findOne(example).orElseGet(Dish::new);
		// 聲明一個菜品及口味數據傳輸類對象並拷貝屬性；
		final DishDto dishDto = new DishDto();
		SecondBeanUtils.copyNullableProperties(newDish, dishDto);
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
			// 拷貝除分類名稱以外的屬性；
			SecondBeanUtils.copyNullableProperties(item, dishDto);
			// 設置分類名稱；
			final Category category = this.categoryRepository.findById(categoryId).orElseGet(Category::new);
			dishDto.setCategoryName(category.getName());
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
			SecondBeanUtils.copyNullableProperties(item, dishDto);
			final Category category = this.categoryRepository.findById(item.getCategoryId()).orElseGet(Category::new);
			dishDto.setCategoryName(category.getName());
			dishDto.setDishFlavors(item.getDishFlavors());
			return dishDto;
		}).collect(Collectors.toList());
		return Pagination.of(dishDtos, dishes.getTotalElements(), pageNum, pageSize);
	}

	@Override
	public void remove(final List<Long> ids) {
		final Integer countStatusByIds = this.dishRepository.countStatusByIds(ids);
		if (countStatusByIds > 0) {
			throw new CustomException(CustomMessages.ERP024);
		}
		// 刪除菜品口味數據；
		this.dishFlavorRepository.batchRemoveByDishIds(ids);
		// 刪除菜品信息；
		this.dishRepository.batchRemoveByIds(ids);
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
		final List<DishFlavor> dishFlavors = dishDto.getDishFlavors().stream().peek(item -> {
			item.setId(BasicContextUtils.getGeneratedId());
			item.setDishId(dishDto.getId());
			item.setCreatedTime(LocalDateTime.now());
			item.setUpdatedTime(LocalDateTime.now());
			item.setCreatedUser(BasicContextUtils.getCurrentId());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		this.dishFlavorRepository.saveAllAndFlush(dishFlavors);
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
		final List<DishFlavor> flavours = dishDto.getDishFlavors().stream().peek(item -> {
			item.setDishId(dishDto.getId());
			item.setUpdatedTime(LocalDateTime.now());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		// 更新菜品口味信息；
		this.dishFlavorRepository.saveAllAndFlush(flavours);
	}
}
