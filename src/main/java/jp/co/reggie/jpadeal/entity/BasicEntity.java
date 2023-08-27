package jp.co.reggie.jpadeal.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 基礎Entity實體類
 *
 * @author Administrator
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class BasicEntity {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 創建時間
	 */
	private LocalDateTime creationTime;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatingTime;

	/**
	 * 創建人
	 */
	private Long creationUser;

	/**
	 * 修改者
	 */
	private Long updatingUser;
}
