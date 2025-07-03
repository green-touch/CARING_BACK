package com.caring.user_service.common.interfaces;

@FunctionalInterface
public interface QueryFunction<T, R> {
    R query(T input);
}
