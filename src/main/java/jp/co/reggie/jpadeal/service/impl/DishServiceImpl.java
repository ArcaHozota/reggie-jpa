package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
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
import jp.co.reggie.jpadeal.dto.DishFlavorDto;
import jp.co.reggie.jpadeal.entity.Dish;
import jp.co.reggie.jpadeal.entity.DishFlavor;
import jp.co.reggie.jpadeal.repository.DishFlavorRepository;
import jp.co.reggie.jpadeal.repository.DishRepository;
import jp.co.reggie.jpadeal.service.DishService;
import jp.co.reggie.jpadeal.utils.BasicContextUtils;
import jp.co.reggie.jpadeal.utils.Pagination;
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
	 * 菜品口味數據接口
	 */
	@Resource
	private DishFlavorRepository dishFlavourRepository;

	@Override
	public void saveWithFlavours(final DishDto dishDto) {
		// 保存菜品的基本信息到菜品表；
		final Dish dish = new Dish();
		BeanUtils.copyProperties(dishDto, dish);
		dish.setId(BasicContextUtils.getGeneratedId());
		dish.setCreatedTime(LocalDateTime.now());
		dish.setUpdatedTime(LocalDateTime.now());
		dish.setCreatedUser(BasicContextUtils.getCurrentId());
		dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		dish.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.dishRepository.save(dish);
		// 獲取菜品口味的集合並將菜品ID設置到口味集合中；
		final List<DishFlavor> dishFlavors = dish.getDishFlavors().stream().peek(item -> {
			item.setId(BasicContextUtils.getGeneratedId());
			item.setDishId(dishDto.getId());
			item.setCreatedTime(LocalDateTime.now());
			item.setUpdatedTime(LocalDateTime.now());
			item.setCreatedUser(BasicContextUtils.getCurrentId());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
			item.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		this.dishFlavourRepository.saveAll(dishFlavors);
	}

	@Override
	public DishDto getByIdWithFlavour(final Long id) {
		// 查詢菜品的基本信息；
		final Dish dish1 = new Dish();
		dish1.setId(id);
		dish1.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		final Example<Dish> example = Example.of(dish1, ExampleMatcher.matchingAll());
		final Dish newDish = this.dishRepository.findOne(example).orElseGet(Dish::new);
		// 聲明一個菜品及口味數據傳輸類對象並拷貝屬性；
		final DishDto dishDto = new DishDto();
		BeanUtils.copyProperties(newDish, dishDto);
		return dishDto;
	}

	@Override
	public void batchUpdateByIds(final String status, final List<Long> dishIds) {
		final List<Dish> dishes = this.dishRepository.findAllById(dishIds);
		final List<Dish> newDishes = dishes.stream().peek(dish -> {
			dish.setUpdatedTime(LocalDateTime.now());
			dish.setUpdatedUser(BasicContextUtils.getCurrentId());
			if (StringUtils.isEqual("0", status)) {
				dish.setStatus(Constants.STATUS_VALID);
			} else if (StringUtils.isEqual("1", status)) {
				dish.setStatus(Constants.STATUS_FORBIDDEN);
			} else {
				throw new CustomException(CustomMessages.ERP017);
			}
		}).collect(Collectors.toList());
		this.dishRepository.saveAll(newDishes);
	}

	@Override
	public void updateWithFlavour(final DishDto dishDto) {
		// 聲明菜品實體類；
		final Dish dish = new Dish();
		BeanUtils.copyProperties(dishDto, dish);
		dish.setUpdatedTime(LocalDateTime.now());
		dish.setUpdatedUser(BasicContextUtils.getCurrentId());
		// 更新菜品信息；
		this.dishRepository.save(dish);
		// 添加當前菜品的口味數據並將菜品ID設置到口味集合中；
		final List<DishFlavor> flavours = dish.getDishFlavors().stream().peek(item -> {
			item.setDishId(dishDto.getId());
			item.setUpdatedTime(LocalDateTime.now());
			item.setUpdatedUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		// 更新菜品口味信息；
		this.dishFlavourRepository.saveAll(flavours);
	}

	@Override
	public Pagination<DishDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Direction.DESC, "updatedTime"));
		final Dish dish = new Dish();
		dish.setName(StringUtils.toHankaku(keyword));
		dish.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("name", GenericPropertyMatchers.contains())
				.withMatcher("logicDeleteFlg", GenericPropertyMatchers.exact());
		final Example<Dish> example = Example.of(dish, exampleMatcher);
		final Page<Dish> dishes = this.dishRepository.findAll(example, pageRequest);
		final List<DishDto> dishDtos = dishes.getContent().stream().map(item -> {
			final DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto, "dishFlavors");
			final List<DishFlavorDto> dishFlavors = dishDto.getDishFlavors();
			for (final DishFlavor dishFlavor : item.getDishFlavors()) {
				final DishFlavorDto dishFlavorDto = new DishFlavorDto();
				BeanUtils.copyProperties(dishFlavor, dishFlavorDto);
				dishFlavors.add(dishFlavorDto);
			}
			dishDto.setDishFlavors(dishFlavors);
			return dishDto;
		}).collect(Collectors.toList());
		return Pagination.of(dishDtos, dishes.getTotalElements(), pageNum - 1, pageSize);
	}

	@Override
	public void remove(final List<Long> idList) {
		final Integer countStatusByIds = this.dishRepository.countStatusByIds(idList);
		if (countStatusByIds > 0) {
			throw new CustomException(CustomMessages.ERP024);
		}
		// 刪除菜品口味數據；
		this.dishFlavourRepository.batchRemoveByDishIds(idList);
		// 刪除菜品信息；
		this.dishRepository.batchRemoveByIds(idList);
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
			BeanUtils.copyProperties(item, dishDto);
			// 設置分類名稱；
			dishDto.setCategoryName(item.getCategory().getName());
			return dishDto;
		}).collect(Collectors.toList());
	}
}
