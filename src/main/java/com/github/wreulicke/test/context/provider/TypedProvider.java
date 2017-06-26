package com.github.wreulicke.test.context.provider;

import java.lang.annotation.Annotation;

/**
 * Implementation must provide no-args constructor.
 * 
 * @author wreulicke
 * @since 0.0.1
 *
 */
public interface TypedProvider<T extends Annotation>extends Provider {
	
}
