package com.github.wreulicke.test.context.provider;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public class ProviderProducer {
    private Function<AnnotatedElement, Provider> provider;


    private ProviderProducer(UnsafeFunction<AnnotatedElement, Provider> provider) {
        this.provider = provider;
    }

    public static ProviderProducer of(Class<? extends Provider> provider) {
        if (AbstractTypedProvider.class.isAssignableFrom(provider)) {
            ParameterizedType type = (ParameterizedType) provider.getGenericSuperclass();
            @SuppressWarnings("rawtypes")
            Class param = (Class) type.getActualTypeArguments()[0];
            if (Annotation.class.equals(param) || Annotation.class.isAssignableFrom(param) == false)
                throw new AssertionError();
            try {
                Constructor<? extends Provider> ctor = provider.getConstructor(param);
                return new ProviderProducer((e) -> {
                    Annotation annotation = AnnotationUtils.findAnnotation(e, param);
                    if (annotation != null) {
                        return ctor.newInstance(annotation);
                    }
                    throw new AssertionError();
                });
            } catch (ReflectiveOperationException e) {
                throw new AssertionError();
            }
        }
        try {
            Constructor<? extends Provider> ctor = provider.getConstructor();
            return new ProviderProducer((e) -> ctor.newInstance());
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    public Provider get(AnnotatedElement e) {
        return provider.apply(e);
    }


    private interface UnsafeFunction<E, R> extends Function<E, R> {
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
