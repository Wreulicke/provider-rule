package com.github.wreulicke.test.context.provider.junit5;

import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.Provider;
import com.github.wreulicke.test.context.provider.ProviderProducer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.commons.JUnitException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ParameterProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Method method = context.getTestMethod()
                .orElseThrow(() -> new JUnitException("cannot find method"));

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations.length == 0) {
            throw new JUnitException("cannot use here : " + method.toGenericString());
        }

        Map<Class<? extends Provider>, ProviderProducer> cache = new HashMap<>();


        return Stream.of(parameterAnnotations)
                .map(p -> {
                    AnnotatedElement element = AnnotatedElementUtils.forAnnotations(p);
                    ProvidedBy provided = AnnotationUtils.getAnnotation(element, ProvidedBy.class);
                    if (provided == null)
                        throw new JUnitException(
                                "this method parameter in " + method.getName() + " is not annotated by @ProvidedBy");
                    Class<? extends Provider> providerClazz = provided.value();
                    ProviderProducer producer = cache.computeIfAbsent(providerClazz, ProviderProducer::of);
                    Provider provider = producer.get(element);
                    try {
                        return provider.visitParameter(null);
                    } catch (Exception e) {
                        throw new JUnitException("Unexpected exception during visit parameter", e);
                    }
                })
                .map(Arguments::of);

    }
}
