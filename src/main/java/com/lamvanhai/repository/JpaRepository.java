package com.lamvanhai.repository;

import com.lamvanhai.paging.PageAble;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Stream;

public interface JpaRepository<T, ID extends Serializable> {
    T insert(T t) throws NoSuchFieldException;

    T update(ID id, T t) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    Optional<T> findById(ID id);

    void delete(ID id);

    Stream<T> findAll();

    Stream<T> findAll(PageAble pageAble);

    long count();
}
