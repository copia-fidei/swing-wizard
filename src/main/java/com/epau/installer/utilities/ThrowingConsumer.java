package com.epau.installer.utilities;

public interface ThrowingConsumer<T> {

	void accept(T t) throws Exception;
}
