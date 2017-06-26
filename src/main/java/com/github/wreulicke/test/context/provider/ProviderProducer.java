package com.github.wreulicke.test.context.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Function;

import org.springframework.core.annotation.AnnotationUtils;

public class ProviderProducer {
	private Function<AnnotatedElement, Provider> provider;
	
	
	private ProviderProducer(UnsafeFunction<AnnotatedElement, Provider> provider) {
		this.provider = provider;
	}
	
	public static ProviderProducer of(Class<Provider> provider) {
		if (TypedProvider.class.isAssignableFrom(provider)) {
			Type[] types = provider.getGenericInterfaces();
			Class clazz = Arrays.stream(types)
				.filter((t) -> t instanceof ParameterizedType)
				.map(ParameterizedType.class::cast)
				.map((t) -> {
					return (Class) t.getActualTypeArguments()[0];
				})
				.filter((param) -> {
					return !Annotation.class.equals(param) && Annotation.class.isAssignableFrom(param);
				}).findFirst().orElseThrow(AssertionError::new); // TODO more information
			return new ProviderProducer((e) -> {
				Annotation annotation = AnnotationUtils.findAnnotation(e, clazz);
				if (annotation != null) {
					Constructor<Provider> ctor = provider.getConstructor(clazz);
					return ctor.newInstance(annotation);
				}
				throw new AssertionError();
			});
		} else {
			try {
				Constructor<Provider> ctor = provider.getConstructor();
				return new ProviderProducer((e) -> ctor.newInstance());
			} catch (Exception e) {
				throw new AssertionError();
			}
		}
	}
	
	public Provider get(AnnotatedElement e) {
		return provider.apply(e);
	}
	
	
	private interface UnsafeFunction<E, R>extends Function<E, R> {
		@Override
		default R apply(E t) {
			try {
				return unsafeApply(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		R unsafeApply(E t) throws Exception;
	}
}
