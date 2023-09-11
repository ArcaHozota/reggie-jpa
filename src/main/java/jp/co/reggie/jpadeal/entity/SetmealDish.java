package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 套餐與菜品關係實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "public.setmeal_dish")
@NamedQuery(name = "SetmealDish.selectBySmId", query = "select smd from SetmealDish smd where smd.logicDeleteFlg = 'visible' and smd.setmealId =:smId")
@NamedQuery(name = "SetmealDish.batchRemoveBySmIds", query = "update SetmealDish smd set smd.logicDeleteFlg = 'removed' where smd.setmealId in(:smIds)")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = -641135780975738908L;

    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 套餐ID
     */
    @Column(nullable = false)
    private Long setmealId;

    /**
     * 菜品ID
     */
    @Column(nullable = false)
    private Long dishId;

    /**
     * 菜品名稱(冗餘字段)
     */
    private String name;

    /**
     * 菜品原價(冗餘字段)
     */
    private BigDecimal price;

    /**
     * 份數
     */
    @Column(nullable = false)
    private Integer copies;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 創建時間
     */
    @Column(nullable = false)
    private LocalDateTime creationTime;

    /**
     * 更新時間
     */
    @Column(nullable = false)
    private LocalDateTime updatingTime;

    /**
     * 創建人
     */
    @Column(name = "creation_user", nullable = false)
    private Long createdUser;

    /**
     * 修改者
     */
    @Column(name = "updating_user", nullable = false)
    private Long updatedUser;

    /**
     * 邏輯刪除字段
     */
    @Column(nullable = false)
    private String logicDeleteFlg;
}
