package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private DishFlavorRepository dishFlavorRepository;

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryRepository;

	/**
	 * 新增菜品，同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	@Override
	public void saveWithFlavours(final DishDto dishDto) {
		// 保存菜品的基本信息到菜品表；
		dishDto.setId(BasicContextUtils.getGeneratedId());
		dishDto.setCreationTime(LocalDateTime.now());
		dishDto.setUpdatingTime(LocalDateTime.now());
		dishDto.setCreationUser(BasicContextUtils.getCurrentId());
		dishDto.setUpdatingUser(BasicContextUtils.getCurrentId());
		dishDto.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.dishRepository.saveById(dishDto);
		// 獲取菜品口味的集合並將菜品ID設置到口味集合中；
		final List<DishFlavor> flavors = dishDto.getFlavors().stream().peek(item -> {
			item.setId(BasicContextUtils.getGeneratedId());
			item.setDishId(dishDto.getId());
			item.setCreationTime(LocalDateTime.now());
			item.setUpdatingTime(LocalDateTime.now());
			item.setCreationUser(BasicContextUtils.getCurrentId());
			item.setUpdatingUser(BasicContextUtils.getCurrentId());
			item.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		this.dishFlavorRepository.batchInsert(flavors);
	}

	/**
	 * 根據ID查詢菜品信息以及對應的口味信息
	 *
	 * @param id 菜品ID
	 * @return dishDto 菜品及口味數據傳輸類
	 */
	@Override
	public DishDto getByIdWithFlavour(final Long id) {
		// 查詢菜品的基本信息；
		final Dish dish = this.dishRepository.selectById(id);
		// 查詢當前菜品所對應的口味信息；
		final List<DishFlavor> flavors = this.dishFlavorRepository.selectByDishId(dish.getId());
		// 聲明一個菜品及口味數據傳輸類對象並拷貝屬性；
		final DishDto dishDto = new DishDto();
		BeanUtils.copyProperties(dish, dishDto);
		dishDto.setFlavors(flavors);
		return dishDto;
	}

	/**
	 * 根據菜品集合批量停發售
	 *
	 * @param status   在售狀態
	 * @param dishList 菜品集合
	 */
	@Override
	public void batchUpdateByIds(final String status, final List<Long> dishList) {
		final LocalDateTime upTime = LocalDateTime.now();
		final Long upUserId = BasicContextUtils.getCurrentId();
		if (StringUtils.isEqual("0", status)) {
			this.dishRepository.batchUpdateByIds(dishList, "1", upTime, upUserId);
		} else if (StringUtils.isEqual("1", status)) {
			this.dishRepository.batchUpdateByIds(dishList, "0", upTime, upUserId);
		} else {
			throw new CustomException(CustomMessages.ERP017);
		}
	}

	/**
	 * 修改菜品信息並同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	@Override
	public void updateWithFlavour(final DishDto dishDto) {
		// 更新菜品信息；
		dishDto.setUpdatingTime(LocalDateTime.now());
		dishDto.setUpdatingUser(BasicContextUtils.getCurrentId());
		this.dishRepository.updateById(dishDto);
		// 添加當前菜品的口味數據並將菜品ID設置到口味集合中；
		final List<DishFlavor> flavors = dishDto.getFlavors().stream().peek(item -> {
			item.setDishId(dishDto.getId());
			item.setUpdatingTime(LocalDateTime.now());
			item.setUpdatingUser(BasicContextUtils.getCurrentId());
		}).collect(Collectors.toList());
		this.dishFlavorRepository.batchUpdateByDishId(flavors);
	}

	/**
	 * 菜品信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<DishDto>
	 */
	@Override
	public Pagination<DishDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
		final Page<Dish> dishes = this.dishRepository.findAll(null, pageRequest);
		final List<DishDto> dishDtos = dishes.getContent().stream().map(item -> {
			final DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto);
			final List<DishFlavor> flavors = this.dishFlavorRepository.selectByDishId(item.getId());
			final Category category = this.categoryRepository.selectById(item.getCategoryId());
			dishDto.setFlavors(flavors);
			dishDto.setCategoryName(category.getName());
			return dishDto;
		}).collect(Collectors.toList());
		return Pagination.of(dishDtos, dishes.getTotalElements(), pageNum, pageSize);
	}

	/**
	 * 根據ID批量刪除菜品
	 *
	 * @param idList 菜品ID集合
	 */
	@Override
	public void remove(final List<Long> idList) {
		// 刪除菜品口味數據；
		this.dishFlavorRepository.batchRemoveByDishId(idList);
		// 刪除菜品信息；
		this.dishRepository.batchRemoveByIds(idList);
	}

	/**
	 * 根據分類ID回顯菜品表單數據
	 *
	 * @param categoryId 分類ID
	 * @return List<DishDto>
	 */
	@Override
	public List<DishDto> getListByCategoryId(final Long categoryId) {
		// 查詢菜品信息；
		final List<Dish> dishList = this.dishRepository.findDishesByCategoryId(categoryId);
		// 獲取菜品及口味數據傳輸類；
		return dishList.stream().map(item -> {
			// 聲明菜品及口味數據傳輸類對象；
			final DishDto dishDto = new DishDto();
			// 拷貝除分類ID以外的屬性；
			BeanUtils.copyProperties(item, dishDto);
			// 獲取分類ID並根據分類ID查詢分類對象；
			final Category category = this.categoryRepository.selectById(item.getCategoryId());
			if (category != null) {
				// 獲取分類名稱；
				final String categoryName = category.getName();
				// 存儲於DTO對象中並返回；
				dishDto.setCategoryName(categoryName);
			}
			// 當前菜品的ID；
			final Long dishId = item.getId();
			// 檢索口味信息；
			final List<DishFlavor> flavors = this.dishFlavorRepository.selectByDishId(dishId);
			dishDto.setFlavors(flavors);
			return dishDto;
		}).collect(Collectors.toList());
	}
}
