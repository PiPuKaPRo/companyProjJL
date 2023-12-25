package main.java.edu.vsu.sakovea.infra.orm;

import main.java.edu.vsu.sakovea.infra.orm.AnnotationProcessor;
import main.java.edu.vsu.sakovea.infra.orm.annotations.*;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static main.java.edu.vsu.sakovea.infra.ReflectionUtil.getAllDeclaredNonStaticFieldsFromClassHierarchy;
import static main.java.edu.vsu.sakovea.infra.ReflectionUtil.getCanonicalConstructor;


public class EntityManager {

    private DatabaseConnector databaseConnector;

    public EntityManager(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

//    public static void save(Object entity, Connection connection) {
//
//        Class<?> clazz = entity.getClass();
//        String tableName = AnnotationProcessor.getTableName(clazz);
//        List<String> columnNames = AnnotationProcessor.getColumnNames(clazz);
//
//        StringBuilder columns = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//
//        for (String columnName : columnNames) {
//            columns.append(columnName).append(", ");
//            values.append("?, ");
//        }
//
//        columns.setLength(columns.length() - 2);
//        values.setLength(values.length() - 2);
//
//        String insertSQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ") RETURNING " + AnnotationProcessor.getPrimaryKeyColumnName(clazz);
//        System.out.println(insertSQL);
//        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
//            int parameterIndex = 1;
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(Id.class)) continue;
//                field.setAccessible(true);
//                Object value = field.get(entity);
//                statement.setObject(parameterIndex++, value);
//            }
//
//            statement.execute();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            for (Field field : clazz.getDeclaredFields()) {
//                if (field.isAnnotationPresent(Id.class)) {
//                    field.setAccessible(true);
//                    generatedKeys.next();
//                    field.set(entity, generatedKeys.getObject(0));
//                }
//            }
//        } catch (SQLException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    public static void save(Object entity, Connection connection) {
        if (AnnotationProcessor.isEntity(entity.getClass())) {
            Class<?> clazz = entity.getClass();
            String tableName = AnnotationProcessor.getTableName(clazz);
            List<String> columnNames = AnnotationProcessor.getColumnNames(clazz);

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (String columnName : columnNames) {
                columns.append(columnName).append(", ");
                values.append("?, ");
            }

            columns.setLength(columns.length() - 2); // Remove the last comma
            values.setLength(values.length() - 2); // Remove the last comma

            String insertSQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";

            try (PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                int parameterIndex = 1;
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(Id.class)) continue;
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    statement.setObject(parameterIndex++, value);
                }

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating entity failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Field primaryKeyField = clazz.getDeclaredField(AnnotationProcessor.getPrimaryKeyColumnName(clazz));
                        primaryKeyField.setAccessible(true);
                        primaryKeyField.set(entity, generatedKeys.getObject(1));
                    } else {
                        throw new SQLException("Creating entity failed, no generated key obtained.");
                    }
                }
            } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Class is not marked as an entity.");
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
                if (field.isAnnotationPresent(OneToMany.class)) continue;
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
        System.out.println(deleteSQL);

        try (PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            Field primaryKeyField = clazz.getDeclaredField(primaryKeyColumnName);
            primaryKeyField.setAccessible(true);
            Object primaryKeyValue = primaryKeyField.get(entity);
            System.out.println(primaryKeyValue);
            statement.setObject(1, primaryKeyValue);

            statement.execute();
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

    public static <T> List<T> findAll(Class<T> clazz, Connection connection) {
        if (AnnotationProcessor.isEntity(clazz)) {
            String tableName = AnnotationProcessor.getTableName(clazz);
            String selectAllSQL = "SELECT * FROM " + tableName;

            List<T> entities = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(selectAllSQL)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        T entity = AnnotationProcessor.mapResultSetToEntity(clazz, resultSet);
                        entities.add(entity);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return entities;
        } else {
            throw new IllegalArgumentException("Class is not marked as an entity.");
        }
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

    public static <T> T mapResultSetToEntity(Class<T> clazz, ResultSet resultSet) throws SQLException {
        try {
            if (resultSet.next()) {
                T entity = clazz.getDeclaredConstructor().newInstance();

                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(OneToMany.class)) continue;
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    field.set(entity, value);
                }

                return entity;
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Error mapping ResultSet to entity", e);
        }
    }

    private <T> T relationToObject(Class<T> clazz, ResultSet result) throws Exception {
        if (clazz.isRecord()) {
            Constructor<T> constructor = getCanonicalConstructor(clazz);
            List<Object> parameters = new ArrayList<>();
            for (RecordComponent recordComponent : clazz.getRecordComponents()) {
                parameters.add(processMember(result, recordComponent, recordComponent.getType()));
            }
            return constructor.newInstance(parameters.toArray(new Object[0]));
        } else {
            T object = clazz.getConstructor().newInstance();
            for (Field field : getAllDeclaredNonStaticFieldsFromClassHierarchy(clazz)) {
                field.setAccessible(true);
                Object value = processMember(result, field, field.getType());
                field.set(object, value);
            }
            return object;
        }
    }

    private Object processMember(ResultSet result, AnnotatedElement member, Class<?> type) throws Exception {
        Column column = member.getAnnotation(Column.class);
        if (column != null) {
            String columnName = column.name();
            if (type.equals(Instant.class)) {
                Timestamp time = result.getTimestamp(columnName);
                return time == null ? null : time.toInstant();
            } else if (type.isEnum()) {
                String value = result.getString(columnName);
                return Enum.valueOf((Class<? extends Enum>) type, value);
            } else {
                return result.getObject(columnName, type);
            }
        }

        ManyToOne manyToOne = member.getAnnotation(ManyToOne.class);
        JoinColumn joinColumn = member.getAnnotation(JoinColumn.class);
        if (manyToOne != null && joinColumn != null) {
            String columnName = joinColumn.name();
            Object foreignKey = result.getObject(columnName);

            String referencedColumnName = joinColumn.referencedColumnName();
            Table referenced = type.getAnnotation(Table.class);
            String referencedTableName = referenced == null ? manyToOne.targetEntity() : referenced.name();

            PreparedStatement select = databaseConnector.getConnection().prepareStatement("select * from %s where %s=?;".formatted(referencedTableName, referencedColumnName));
            select.setObject(1, foreignKey);

            select.execute();
            ResultSet foreignResult = select.getResultSet();

            if (foreignResult.next()) {
                return relationToObject(type, foreignResult);
            }
            return null;
        }

        Agregated agregated = member.getAnnotation(Agregated.class);
        if (agregated != null) {
            return relationToObject(type, result);
        }

        return null;
    }
}