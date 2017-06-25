package com.github.wreulicke.test.context.provider.spring;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class ProvideListener extends AbstractTestExecutionListener {
	
	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		Object instance = testContext.getTestInstance();
		new Preparator(testContext).prepare(instance);
	}
	
}
