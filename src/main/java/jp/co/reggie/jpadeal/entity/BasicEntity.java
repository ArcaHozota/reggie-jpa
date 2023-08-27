package jp.co.reggie.jpadeal.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

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
	@Id
	private Long id;

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
	@Column(nullable = false)
	private Long creationUser;

	/**
	 * 修改者
	 */
	@Column(nullable = false)
	private Long updatingUser;
}
