package jp.co.reggie.jpadeal.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    /**
     * 新增套餐同時保存套餐和菜品的關聯關係
     *
     * @param setmealDto 數據傳輸類
     */
    @Override
    public void saveWithDish(final SetmealDto setmealDto) {
        // 保存套餐的基本信息；
        setmealDto.setId(BasicContextUtils.getGeneratedId());
        setmealDto.setCreationTime(LocalDateTime.now());
        setmealDto.setUpdatingTime(LocalDateTime.now());
        setmealDto.setCreationUser(BasicContextUtils.getCurrentId());
        setmealDto.setUpdatingUser(BasicContextUtils.getCurrentId());
        setmealDto.setLogicDeleteFlg(Constants.LOGIC_FLAG);
        this.setmealRepository.save(setmealDto);
        // 獲取套餐菜品關聯集合；
        final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
            item.setId(BasicContextUtils.getGeneratedId());
            item.setSetmealId(setmealDto.getId());
            item.setCreationTime(LocalDateTime.now());
            item.setUpdatingTime(LocalDateTime.now());
            item.setCreationUser(BasicContextUtils.getCurrentId());
            item.setUpdatingUser(BasicContextUtils.getCurrentId());
            item.setLogicDeleteFlg(Constants.LOGIC_FLAG);
        }).collect(Collectors.toList());
        // 保存套餐和菜品的關聯關係；
        this.setmealDishRepository.saveAll(setmealDishes);
    }

    /**
     * 刪除套餐同時刪除套餐和菜品的關聯關係
     *
     * @param ids 套餐ID的集合
     */
    @Override
    public void removeWithDish(final List<Long> ids) {
        // 查詢套餐狀態以確認是否可以刪除；
        final Integer count = this.setmealRepository.getStatusByIds(ids);
        if (count > 0) {
            // 如果無法刪除，則抛出異常；
            throw new CustomException(CustomMessages.ERP012);
        }
        // 刪除套餐表中的數據；
        this.setmealRepository.batchRemoveByIds(ids);
        // 刪除套餐口味表中的數據；
        this.setmealDishRepository.batchRemoveBySmIds(ids);
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
        final PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        final ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("logicDeleteFlg", ExampleMatcher.GenericPropertyMatchers.exact());
        final SetmealDto setmealDto = new SetmealDto();
        setmealDto.setName(keyword);
        setmealDto.setLogicDeleteFlg(Constants.LOGIC_FLAG);
        final Example<SetmealDto> example = Example.of(setmealDto, exampleMatcher);
        final Page<SetmealDto> setmealInfos = this.setmealRepository.findAll(example, pageRequest);
        final List<SetmealDto> setmealDtos = setmealInfos.getContent().stream().map(item -> {
            final SetmealDto aDto = new SetmealDto();
            BeanUtils.copyProperties(item, aDto);
            final Specification<Category> categorySpecification1 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), item.getCategoryId().toString());
            final Specification<Category> categorySpecification2 = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("logicDeleteFlg"), Constants.LOGIC_FLAG);
            final Specification<Category> categorySpecification = Specification.where(categorySpecification1).and(categorySpecification2);
            final Category category = this.categoryRepository.findOne(categorySpecification).orElseGet(Category::new);
            final List<SetmealDish> setmealDishes = this.setmealDishRepository.selectBySmId(item.getId());
            aDto.setSetmealDishes(setmealDishes);
            aDto.setCategoryName(category.getName());
            return aDto;
        }).collect(Collectors.toList());
        return Pagination.of(setmealDtos, setmealInfos.getTotalElements(), pageNum, pageSize);
    }

    /**
     * 根據ID顯示套餐信息
     *
     * @param id 套餐ID
     * @return SetmealDto
     */
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

    /**
     * 更新套餐，同時更新套餐和菜品的關聯
     *
     * @param setmealDto 數據傳輸類
     */
    @Override
    public void updateWithDish(final SetmealDto setmealDto) {
        // 保存套餐的基本信息；
        setmealDto.setUpdatingTime(LocalDateTime.now());
        setmealDto.setUpdatingUser(BasicContextUtils.getCurrentId());
        this.setmealRepository.save(setmealDto);
        // 獲取套餐菜品關聯集合；
        final List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().peek(item -> {
            item.setSetmealId(setmealDto.getId());
            item.setSort(RANDOM.nextInt(setmealDto.getSetmealDishes().size()));
            item.setUpdatingTime(LocalDateTime.now());
            item.setUpdatingUser(BasicContextUtils.getCurrentId());
        }).collect(Collectors.toList());
        // 保存套餐和菜品的關聯關係；
        this.setmealDishRepository.batchUpdateBySmIds(setmealDishes);
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
        final Long upUserId = BasicContextUtils.getCurrentId();
        if (StringUtils.isEqual("0", status)) {
            this.setmealRepository.batchUpdateByIds(stmlList, "1", upTime, upUserId);
        } else if (StringUtils.isEqual("1", status)) {
            this.setmealRepository.batchUpdateByIds(stmlList, "0", upTime, upUserId);
        } else {
            throw new CustomException(CustomMessages.ERP022);
        }
    }
}
