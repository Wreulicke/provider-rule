package com.github.wreulicke.test.context.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.github.wreulicke.test.context.provider.NameAnnotationProvider.Name;

public class NameAnnotationProvider extends AbstractTypedProvider<Name> {
	private Name name;
	
	
	public NameAnnotationProvider(Name annot) {
		name = annot;
	}
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({
		ElementType.FIELD,
		ElementType.TYPE,
		ElementType.METHOD
	})
	public @interface Name {
		String value() default "";
	}
	
	
	@Override
	public void visitMethod(Object instance, Method method) throws Exception {
		throw new AssertionError();
	}
	
	@Override
	public void visitField(Object instance, Field field) throws Exception {
		field.setAccessible(true);
		field.set(instance, name.value());
	}
}
