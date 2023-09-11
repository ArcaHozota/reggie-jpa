package jp.co.reggie.jpadeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 套餐實體類
 *
 * @author Administrator
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "setmeal")
@NamedQuery(name = "Setmeal.countByCategoryId", query = "select count(1) from Setmeal st where st.logicDeleteFlg = 'visible' and st.categoryId =:categoryId")
@NamedQuery(name = "Setmeal.countStatusByIds", query = "select count(1) from Setmeal st where " +
        "st.logicDeleteFlg ='visible' and st.status ='0' and st.id in (:smIdList)")
@NamedQuery(name = "Setmeal.batchRemoveByIds", query = "update Setmeal st set st.logicDeleteFlg ='removed' where st.id in(:smIdList)")
@NamedQuery(name = "Setmeal.batchUpdateByIds", query = "update Setmeal st set st.status =:status, " +
        "st.updatingTime =:updateTime, st.updatedUser =:updateUser where st.id in(:smIdList)")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 4020217756505140488L;

    /**
     * ID
     */
    @Id
    private Long id;

    /**
     * 分類ID
     */
    @Column(nullable = false)
    private Long categoryId;

    /**
     * 套餐名稱
     */
    @Column(nullable = false)
    private String name;

    /**
     * 套餐價格
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * 套餐售賣狀態：1:在售, 0:停售;
     */
    private String status;

    /**
     * 編碼
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 圖片
     */
    private String image;

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