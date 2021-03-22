package com.lamvanhai.validate;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator<T> {
    private final T obj;

    private Validator(T obj) {
        this.obj = obj;
    }

    public static <T> Validator<T> of(T t) {
        return new Validator<>(Objects.requireNonNull(t));
    }

    public Validator<T> validate(Predicate<T> validation, Supplier<RuntimeException> runtimeException) {
        if (validation.test(obj)) {
            throw runtimeException.get();
        }
        return this;
    }

    public <U> Validator<T> validate(BiPredicate<T, U> validation, Supplier<RuntimeException> runtimeException, U u) {
        if (validation.test(obj, u)) {
            throw runtimeException.get();
        }
        return this;
    }

    public <U> Validator<T> validate(Function<T, U> projection, Predicate<U> validation,
                                     Supplier<RuntimeException> throwable) {
        return validate(projection.andThen(validation::test)::apply, throwable);
    }

    public T get() {
        return obj;
    }
}
