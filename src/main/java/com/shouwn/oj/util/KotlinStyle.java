package com.shouwn.oj.util;

import java.util.function.Consumer;
import java.util.function.Function;

public interface KotlinStyle<T> {

	/**
	 * 매개변수로 들어온 함수를 통해
	 * this 객체를 변환하여 반환합니다.
	 *
	 * @param block this 객체를 변환할 함수
	 * @return 변환된 결과
	 * @throws ClassCastException if the object is not
	 * null and is not assignable to the type T.
	 */
	@SuppressWarnings("unchecked")
	default <R> R let(Function<T, R> block) {
		return block.apply((T) this);
	}

	/**
	 * 매개변수로 들어온 함수를
	 * this 객체로 실행합니다.
	 * 그리고 this 객체를 리턴합니다.
	 *
	 * @param block this 객체를 변환할 함수
	 * @return 변환된 결과
	 * @throws ClassCastException if the object is not
	 * null and is not assignable to the type T.
	 */
	@SuppressWarnings("unchecked")
	default T apply(Consumer<T> block) {
		T thisObject = (T) this;

		block.accept(thisObject);
		return thisObject;
	}
}
