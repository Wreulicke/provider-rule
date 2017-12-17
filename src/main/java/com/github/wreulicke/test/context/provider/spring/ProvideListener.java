package com.github.wreulicke.test.context.provider.spring;

import com.github.wreulicke.test.context.provider.Preparater;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class ProvideListener extends AbstractTestExecutionListener {

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        Object instance = testContext.getTestInstance();
        AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();

        new Preparater((provider, providerClazz) -> {
            beanFactory.autowireBeanProperties(provider, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
            beanFactory.initializeBean(provider, providerClazz.getName());
        }).prepare(instance);
    }

}
