package com.github.wreulicke.test.context.provider.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.wreulicke.test.context.provider.FieldNameProvider;
import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.SynthesizedProvider;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NopConfiguration.class)
public class FieldNameProviderTest {
	
	@ProvidedBy(FieldNameProvider.class)
	String field;
	
	@SynthesizedProvider
	String field2;
	
	
	@Test
	public void test() {
		assertThat(field).isEqualTo("field");
	}
	
	@Test
	public void testWithSynthesizedCase() {
		assertThat(field2).isEqualTo("field2");
	}
}
