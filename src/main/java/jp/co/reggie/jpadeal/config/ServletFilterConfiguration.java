package jp.co.reggie.jpadeal.config;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

/**
 * Struts2設定クラス
 *
 * @author ArkamaHozota
 * @since 1.09
 */
@Log4j2
@Configuration
public class ServletFilterConfiguration {

	/**
	 * Strutsの基本フィルタを配置する
	 *
	 * @return StrutsPrepareAndExecuteFilter
	 */
	@Bean
	protected StrutsPrepareAndExecuteFilter strutsPrepareAndExecuteFilter() {
		log.info("Struts2フレームワーク配置成功！");
		return new StrutsPrepareAndExecuteFilter();
	}
}
