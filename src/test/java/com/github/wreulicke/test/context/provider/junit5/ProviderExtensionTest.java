package com.github.wreulicke.test.context.provider.junit5;

import com.github.wreulicke.test.context.provider.FieldNameProvider;
import com.github.wreulicke.test.context.provider.NameAnnotationProvider;
import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.SynthesizedProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
@ExtendWith(ProviderExtension.class)
public class ProviderExtensionTest {

    @ProvidedBy(FieldNameProvider.class)
    String field;

    @SynthesizedProvider
    String field2;

    @ProvidedBy(NameAnnotationProvider.class)
    @NameAnnotationProvider.Name("test")
    String test;

    @Test
    public void test() {
        assertThat(field).isEqualTo("field");
    }

    @Test
    public void testWithSynthesizedCase() {
        assertThat(field2).isEqualTo("field2");
    }

    @Test
    public void testWithNamedAnnotationCase() {
        assertThat(test).isEqualTo("test");
    }

    @ParameterizedTest
    @ParameterProviderSource
    public void testWithParameterCase(
            @ProvidedBy(NameAnnotationProvider.class)
            @NameAnnotationProvider.Name("test") String test) {
        assertThat(test).isEqualTo("test");
    }
}
