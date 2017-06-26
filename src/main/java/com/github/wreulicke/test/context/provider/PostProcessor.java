package com.github.wreulicke.test.context.provider;

public interface PostProcessor {
	public void process(Object instance, Class<?> clazz);
}
