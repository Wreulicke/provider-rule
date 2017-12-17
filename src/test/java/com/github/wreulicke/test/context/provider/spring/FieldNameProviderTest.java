package com.github.wreulicke.test.context.provider.spring;

import com.github.wreulicke.test.context.provider.FieldNameProvider;
import com.github.wreulicke.test.context.provider.NameAnnotationProvider;
import com.github.wreulicke.test.context.provider.NameAnnotationProvider.Name;
import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.SynthesizedProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NopConfiguration.class)
public class FieldNameProviderTest {

    @ProvidedBy(FieldNameProvider.class)
    String field;

    @SynthesizedProvider
    String field2;

    @ProvidedBy(NameAnnotationProvider.class)
    @Name("test")
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
}
