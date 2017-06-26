package com.github.wreulicke.test.context.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class FieldNameProvider implements Provider {
	@Autowired
	ApplicationContext context;
	
	
	@Override
	public void visitMethod(Object instance, Method method) throws Exception {
		throw new AssertionError();
	}
	
	@Override
	public void visitField(Object instance, Field field) throws Exception {
		field.setAccessible(true);
		field.set(instance, field.getName());
	}
	
}
