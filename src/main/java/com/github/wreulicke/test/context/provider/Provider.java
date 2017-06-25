package com.github.wreulicke.test.context.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface Provider {
	void visitMethod(Object instance, Method method) throws Exception;
	
	void visitField(Object instance, Field field) throws Exception;
}
