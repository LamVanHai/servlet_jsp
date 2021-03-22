package com.lamvanhai.repository.impl;

import com.lamvanhai.annotation.Id;
import com.lamvanhai.paging.PageAble;
import com.lamvanhai.repository.JpaRepository;
import com.lamvanhai.repository.connection.ConnectionPool;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.lamvanhai.util.AnnotationUtil.*;
import static com.lamvanhai.util.ReflectionUtil.convertToEntity;
import static com.lamvanhai.util.ReflectionUtil.get;


public class BaseQuery<T, ID extends Serializable> implements JpaRepository<T, ID> {
    private String INSERT = "INSERT INTO %s(%s) VALUES(%s)";
    private String UPDATE = "UPDATE %s SET %s WHERE %s";
    private String DELETE = "DELETE FROM %s WHERE %s";
    private String SELECT_CONDITION = "SELECT %s FROM %s WHERE %s";
    private String SELECT = "SELECT %s FROM %s";
    Connection connection = ConnectionPool.getConnection();
    private Class<T> tClass;

    public BaseQuery() {
        this.tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String getSimpleName() {
        return tClass.getSimpleName();
    }

    @Override
    public T insert(T t) throws NoSuchFieldException {
        String tableName = getTableName(tClass);
        Field[] fields = tClass.getDeclaredFields();
        StringBuilder properties = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Id.class)) {
                if (getAutoIncrement(tClass, field.getName())) {
                    continue;
                }
            }
            properties.append(getColumnName(tClass, field.getName())).append(",");
            values.append("?,");
        }
        properties.deleteCharAt(properties.length() - 1);
        values.deleteCharAt(values.length() - 1);
        String sql = String.format(INSERT, tableName, properties, values);

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int id = 1;
            if (fields[0].isAnnotationPresent(Id.class)) {
                if (!getAutoIncrement(tClass, fields[0].getName())) {
                    ps.setObject(id++, get(t, fields[0]));
                }
            }
            for (int i = 1; i < fields.length; i++) {
                ps.setObject(id++, get(t, fields[i]));
            }
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return t;
    }

    @Override
    public T update(ID id, T t) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StringBuilder update = new StringBuilder();
        String tableName = getTableName(tClass);
        Field[] fields = tClass.getDeclaredFields();
        Connection connection = ConnectionPool.getConnection();
        for (int i = 1; i < fields.length; i++) {
            Field field = fields[i];
            if (get(t, field) == null) {
                continue;
            }
            update.append(getColumnName(tClass, field.getName()))
                    .append(" = ?,");
        }
        update.deleteCharAt(update.length() - 1);
        StringBuilder condition = new StringBuilder(getPrimaryKey(tClass, fields[0].getName()))
                .append(" = ?");

        String sql = String.format(UPDATE, tableName, update, condition);

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int index = 1;
            for (int i = 1; i < fields.length; i++) {
                if (get(t, fields[i]) == null) {
                    continue;
                }
                ps.setObject(index++, get(t, fields[i]));
            }
            ps.setObject(index, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return t;
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            String condition = getPrimaryKey(tClass, tClass.getDeclaredFields()[0].getName()) + "=?";
            String sql = String.format(SELECT_CONDITION, "*", getTableName(tClass), condition);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            T t = null;
            while (rs.next()) {
                t = convertToEntity(rs, tClass);
            }

            return Optional.of(t);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();

            return Optional.empty();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            return Optional.empty();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

            return Optional.empty();
        } catch (InstantiationException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @Override
    public void delete(ID id) {
        String condition = null;
        try {
            condition = getColumnName(tClass, tClass.getDeclaredFields()[0].getName()) + "=?";
            String sql = String.format(DELETE, getTableName(tClass), condition);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Stream<T> findAll() {
        String sql = String.format(SELECT, "*", getTableName(tClass));
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = convertToEntity(rs, tClass);
                list.add(t);
            }

            return list.stream();
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public Stream<T> findAll(PageAble pageAble) {
        String sql = String.format(SELECT, "*", getTableName(tClass));
        StringBuilder sqlExe = new StringBuilder(sql);
        if (pageAble != null) {
            if (pageAble.getSize() != 0 && pageAble.getPageIndex() != 0) {
                sqlExe.append(" LIMIT ? OFFSET ? ");
            }
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sqlExe.toString());
            ps.setInt(1, pageAble.getSize());
            ps.setInt(2, pageAble.getOffSet());
            ResultSet rs = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = convertToEntity(rs, tClass);
                list.add(t);
            }

            return list.stream();
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public long count() {
        String sql = String.format(SELECT, "COUNT(1)", getTableName(tClass));
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            long total = 0;
            while (rs.next()) {
                total = rs.getLong(1);
            }

            return total;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }
}
