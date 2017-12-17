package com.github.wreulicke.test.context.provider.junit5;

import com.github.wreulicke.test.context.provider.Preparater;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

/**
 * JUnit5 Extension for provider-rule.
 *
 * @author wreulicke
 */
public class ProviderExtension implements BeforeEachCallback {

    private final Preparater preparater;

    public ProviderExtension() {
        preparater = new Preparater((o, c) -> {
            // nop
        });
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Optional<Object> instanceOpt = context.getTestInstance();

        instanceOpt.ifPresent(preparater::prepare);

    }
}
