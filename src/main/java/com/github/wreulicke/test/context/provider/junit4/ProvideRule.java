package com.github.wreulicke.test.context.provider.junit4;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.github.wreulicke.test.context.provider.Preparator;

public class ProvideRule implements TestRule {
	
	protected ProvideRule(Object instance) {
		new Preparator((o, c) -> {
			/* nop */
		}).prepare(instance);
	}
	
	@Override
	public Statement apply(Statement base, Description description) {
		return base;
	}
	
}
