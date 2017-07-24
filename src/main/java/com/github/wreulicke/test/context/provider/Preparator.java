package com.github.wreulicke.test.context.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

public class Preparator {
	
	private PostProcessor postProcessor;
	
	
	public Preparator(PostProcessor processor) {
		postProcessor = processor;
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
		
		@SuppressWarnings("rawtypes")
		Map<Class, ProviderProducer> cache = new HashMap<>();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			ProvidedBy provided = AnnotationUtils.findAnnotation(field, ProvidedBy.class);
			if (provided != null) {
				Class<? extends Provider> providerClazz = provided.value();
				ProviderProducer producer = cache.computeIfAbsent(providerClazz, ProviderProducer::of);
				Provider provider = producer.get(field);
				postProcessor.process(provider, providerClazz);
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
				ProviderProducer producer = cache.computeIfAbsent(providerClazz, ProviderProducer::of);
				Provider provider = producer.get(method);
				postProcessor.process(provider, providerClazz);
				provider.visitMethod(instance, method);
			}
		}
		provideRecursively(instance, clazz.getSuperclass());
	}
}
