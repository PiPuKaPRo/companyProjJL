package main.java.edu.vsu.sakovea.infra.orm;

import main.java.edu.vsu.sakovea.infra.orm.AnnotationProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    public static void save(Object entity, Connection connection) {
        Class<?> clazz = entity.getClass();
        String tableName = AnnotationProcessor.getTableName(clazz);
        List<String> columnNames = AnnotationProcessor.getColumnNames(clazz);

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (String columnName : columnNames) {
            columns.append(columnName).append(", ");
            values.append("?, ");
        }

        columns.setLength(columns.length() - 2);
        values.setLength(values.length() - 2);

        String insertSQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";

        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            int parameterIndex = 1;
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(entity);
                statement.setObject(parameterIndex++, value);
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void update(Object entity, Connection connection) {
        Class<?> clazz = entity.getClass();
        String tableName = AnnotationProcessor.getTableName(clazz);
        List<String> columnNames = AnnotationProcessor.getColumnNames(clazz);
        String primaryKeyColumnName = AnnotationProcessor.getPrimaryKeyColumnName(clazz);

        StringBuilder setClause = new StringBuilder();
        for (String columnName : columnNames) {
            setClause.append(columnName).append(" = ?, ");
        }
        setClause.setLength(setClause.length() - 2);

        String updateSQL = "UPDATE " + tableName + " SET " + setClause + " WHERE " + primaryKeyColumnName + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            int parameterIndex = 1;
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(entity);
                statement.setObject(parameterIndex++, value);
            }

            Field primaryKeyField = clazz.getDeclaredField(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            Object primaryKeyValue = primaryKeyField.get(entity);
            statement.setObject(parameterIndex, primaryKeyValue);

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void delete(Object entity, Connection connection) {
        Class<?> clazz = entity.getClass();
        String tableName = AnnotationProcessor.getTableName(clazz);
        String primaryKeyColumnName = AnnotationProcessor.getPrimaryKeyColumnName(clazz);

        String deleteSQL = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumnName + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            Field primaryKeyField = clazz.getDeclaredField(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            Object primaryKeyValue = primaryKeyField.get(entity);
            statement.setObject(1, primaryKeyValue);

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static <T> T findById(Class<T> clazz, Object id, Connection connection) {
        String tableName = AnnotationProcessor.getTableName(clazz);
        String primaryKeyColumnName = AnnotationProcessor.getPrimaryKeyColumnName(clazz);

        String selectSQL = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColumnName + " = ?";

        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEntity(clazz, resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveOrUpdate(Object entity, Connection connection) {
        if (AnnotationProcessor.isEntity(entity.getClass())) {
            Object primaryKeyValue = getPrimaryKeyValue(entity);
            if (primaryKeyValue == null) {
                save(entity, connection);
            } else {
                update(entity, connection);
            }
        } else {
            throw new IllegalArgumentException("Class is not marked as an entity.");
        }
    }

    private static Object getPrimaryKeyValue(Object entity) {
        Class<?> clazz = entity.getClass();
        String primaryKeyColumnName = AnnotationProcessor.getPrimaryKeyColumnName(clazz);
        try {
            Field primaryKeyField = clazz.getDeclaredField(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            return primaryKeyField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T mapResultSetToEntity(Class<T> clazz, ResultSet resultSet) throws SQLException {
        T entity = null;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = resultSet.getObject(field.getName());
            try {
                field.set(entity, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return entity;
    }
}