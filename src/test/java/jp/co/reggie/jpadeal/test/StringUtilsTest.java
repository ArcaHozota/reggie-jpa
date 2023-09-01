package jp.co.reggie.jpadeal.test;

import org.junit.jupiter.api.Test;

import jp.co.reggie.jpadeal.utils.StringUtils;

public class StringUtilsTest {

	@Test
	public void test() {
		final String att = "０１２ＡＢＣ＠＄％";
		final String hankaku = StringUtils.toHankaku(att);
		System.out.println(hankaku);
	}
}
