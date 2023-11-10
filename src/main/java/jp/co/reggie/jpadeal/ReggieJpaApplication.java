package jp.co.reggie.jpadeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.log4j.Log4j2;

/**
 * Reggie-take-outプロジェクト始動クラス
 *
 * @author ArkamaHozota
 * @since 1.00beta
 */
@Log4j2
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieJpaApplication {
	public static void main(final String[] args) {
		SpringApplication.run(ReggieJpaApplication.class, args);
		log.info("本工程啓動成功...");
	}
}
