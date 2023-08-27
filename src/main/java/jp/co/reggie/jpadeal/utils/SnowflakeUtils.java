package jp.co.reggie.jpadeal.utils;

import java.util.Random;

/**
 * 雪花のアルゴリズムID生成ツール
 *
 * @author Administrator
 */
public final class SnowflakeUtils {

	private static final Random RANDOM = new Random();

	/**
	 * コンストラクタ
	 */
	private SnowflakeUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 次の雪花アルゴリズムIDを取得
	 *
	 * @return long ID
	 */
	static long nextId() {
		final int nextInt1 = RANDOM.nextInt(31);
		final int nextInt2 = RANDOM.nextInt(31);
		return new SnowflakeIdGenerator(nextInt1, nextInt2).nextId();
	}
}
