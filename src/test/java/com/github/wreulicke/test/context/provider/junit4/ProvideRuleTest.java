package com.github.wreulicke.test.context.provider.junit4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.github.wreulicke.test.context.provider.FieldNameProvider;
import com.github.wreulicke.test.context.provider.ProvidedBy;
import com.github.wreulicke.test.context.provider.SynthesizedProvider;

public class ProvideRuleTest {
	@Rule
	public ProvideRule rule = new ProvideRule(this);
	
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
