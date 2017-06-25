package com.github.wreulicke.test.context.provider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.wreulicke.test.context.provider.ProvidedBy;

@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD
})
@ProvidedBy(FieldNameProvider.class)
public @interface SynthesizedProvider {
}
