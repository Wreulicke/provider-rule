package com.github.wreulicke.test.context.provider.spring;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;

import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.Provider;

public class Preparator {
	private TestContext testContext;
	
	private AutowireCapableBeanFactory beanFactory;
	
	
	public Preparator(TestContext context) {
		testContext = context;
		beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();
	}
	
	public void prepare(Object instance) {
		try {
			provideRecursively(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void provideRecursively(Object instance) throws Exception {
		provideRecursively(instance, instance.getClass());
	}
	
	private void provideRecursively(Object instance, Class<?> clazz)
			throws Exception {
		if (clazz == Object.class)
			return;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			ProvidedBy provided = AnnotationUtils.findAnnotation(field, ProvidedBy.class);
			if (provided != null) {
				Class<? extends Provider> providerClazz = provided.value();
				Constructor<? extends Provider> ctor = providerClazz.getConstructor();
				Provider provider = ctor.newInstance();
				beanFactory.autowireBeanProperties(provider, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
				beanFactory.initializeBean(provider, providerClazz.getName());
				provider.visitField(instance, field);
			}
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (Modifier.isStatic(method.getModifiers())) {
				continue;
			}
			ProvidedBy provided = AnnotationUtils.findAnnotation(method, ProvidedBy.class);
			if (provided != null) {
				Class<? extends Provider> providerClazz = provided.value();
				Constructor<? extends Provider> ctor = providerClazz.getConstructor();
				Provider provider = ctor.newInstance();
				beanFactory.autowireBeanProperties(provider, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
				beanFactory.initializeBean(provider, providerClazz.getName());
				provider.visitMethod(instance, method);
			}
		}
		provideRecursively(instance, clazz.getSuperclass());
	}
}
