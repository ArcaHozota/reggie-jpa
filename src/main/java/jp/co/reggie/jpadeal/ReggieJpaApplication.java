package jp.co.reggie.jpadeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.log4j.Log4j2;

/**
 * 瑞吉外賣工程啓動項
 *
 * @author Administrator
 * @since 2022-11-08
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
