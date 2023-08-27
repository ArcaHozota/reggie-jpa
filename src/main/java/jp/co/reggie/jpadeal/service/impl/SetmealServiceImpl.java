package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class SetmealServiceImpl implements SetmealService {

	private static final Random RANDOM = new Random();

	/**
	 * 分類管理數據接口
	 */
	@Resource
	private CategoryRepository categoryMapper;

	/**
	 * 套餐數據接口類
	 */
	@Resource
	private SetmealRepository setmealMapper;

	/**
	 * 套餐與菜品關係數據接口類
	 */
	@Resource
	private SetmealDishRepository setmealDishMapper;

	/**
	 * 新增套餐同時保存套餐和菜品的關聯關係
	 *
	 * @param setmealDto 數據傳輸類
	 */
	@Override
	public void saveWithDish(final SetmealDto setmealDto) {
		// 保存套餐的基本信息；
		BasicContextUtils.fillWithInsert(setmealDto);
		setmealDto.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		this.setmealMapper.saveById(setmealDto);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
			BasicContextUtils.fillWithInsert(item);
			item.setSetmealId(setmealDto.getId());
			item.setLogicDeleteFlg(Constants.LOGIC_FLAG);
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishMapper.batchInsert(setmealDishes);
	}

	/**
	 * 刪除套餐同時刪除套餐和菜品的關聯關係
	 *
	 * @param ids 套餐ID的集合
	 */
	@Override
	public void removeWithDish(final List<Long> ids) {
		// 查詢套餐狀態以確認是否可以刪除；
		final Integer count = this.setmealMapper.getStatusByIds(ids);
		if (count > 0) {
			// 如果無法刪除，則抛出異常；
			throw new CustomException(CustomMessages.ERP012);
		}
		// 刪除套餐表中的數據；
		this.setmealMapper.batchRemoveByIds(ids);
		// 刪除套餐口味表中的數據；
		this.setmealDishMapper.batchRemoveBySmIds(ids);
	}

	/**
	 * 套餐信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param keyword  檢索文
	 * @return Pagination<SetmealDto>
	 */
	@Override
	public Pagination<SetmealDto> pagination(final Integer pageNum, final Integer pageSize, final String keyword) {
		final Integer offset = (pageNum - 1) * pageSize;
		final Integer setmealInfosCnt = this.setmealMapper.getSetmealInfosCnt(keyword);
		if (setmealInfosCnt == 0) {
			return Pagination.of(new ArrayList<>(), setmealInfosCnt, pageNum, pageSize);
		}
		final List<Setmeal> setmealInfos = this.setmealMapper.getSetmealInfos(pageSize, offset, keyword);
		final List<SetmealDto> setmealDtos = setmealInfos.stream().map(item -> {
			final SetmealDto setmealDto = new SetmealDto();
			BeanUtils.copyProperties(item, setmealDto);
			final Category category = this.categoryMapper.selectById(item.getCategoryId());
			final List<SetmealDish> setmealDishes = this.setmealDishMapper.selectBySmId(item.getId());
			setmealDto.setSetmealDishes(setmealDishes);
			setmealDto.setCategoryName(category.getName());
			return setmealDto;
		}).collect(Collectors.toList());
		return Pagination.of(setmealDtos, setmealInfosCnt, pageNum, pageSize);
	}

	/**
	 * 根據ID顯示套餐信息
	 *
	 * @param id 套餐ID
	 * @return SetmealDto
	 */
	@Override
	public SetmealDto getByIdWithDishInfo(final Long id) {
		final Setmeal setmeal = this.setmealMapper.selectById(id);
		final SetmealDto setmealDto = new SetmealDto();
		BeanUtils.copyProperties(setmeal, setmealDto);
		final Category category = this.categoryMapper.selectById(setmeal.getCategoryId());
		final List<SetmealDish> setmealDishes = this.setmealDishMapper.selectBySmId(id);
		setmealDto.setCategoryName(category.getName());
		setmealDto.setSetmealDishes(setmealDishes);
		return setmealDto;
	}

	/**
	 * 更新套餐，同時更新套餐和菜品的關聯
	 *
	 * @param setmealDto 數據傳輸類
	 */
	@Override
	public void updateWithDish(final SetmealDto setmealDto) {
		// 保存套餐的基本信息；
		BasicContextUtils.fillWithUpdate(setmealDto);
		this.setmealMapper.updateById(setmealDto);
		// 獲取套餐菜品關聯集合；
		final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
			BasicContextUtils.fillWithUpdate(item);
			item.setSetmealId(setmealDto.getId());
			item.setSort(RANDOM.nextInt(setmealDto.getSetmealDishes().size()));
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishMapper.batchUpdateBySmIds(setmealDishes);
	}

	/**
	 * 根據套餐集合批量停發售
	 *
	 * @param status   在售狀態
	 * @param stmlList 套餐集合
	 */
	@Override
	public void batchUpdateByIds(final String status, final List<Long> stmlList) {
		final LocalDateTime upTime = LocalDateTime.now();
		final Long upUserId = BasicContextUtils.getOnceId();
		if (StringUtils.isEqual("0", status)) {
			this.setmealMapper.batchUpdateByIds(stmlList, "1", upTime, upUserId);
		} else if (StringUtils.isEqual("1", status)) {
			this.setmealMapper.batchUpdateByIds(stmlList, "0", upTime, upUserId);
		} else {
			throw new CustomException(CustomMessages.ERP022);
		}
	}
}
