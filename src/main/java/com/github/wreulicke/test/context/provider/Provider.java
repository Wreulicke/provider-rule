package com.github.wreulicke.test.context.provider;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Implementation must provide no-args constructor.
 *
 * @author wreulicke
 * @since 0.0.1
 *
 */
public interface Provider {

	void visitMethod(Object instance, Method method) throws Exception;

	void visitField(Object instance, Field field) throws Exception;

	default Object visitParameter(AnnotatedType parameter) throws Exception  {
	    return null;
    }
}
