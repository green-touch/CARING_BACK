package com.caring.manager_service.common.interfaces;

@FunctionalInterface
public interface QueryFunction<T, R> {
    R query(T input);
}
