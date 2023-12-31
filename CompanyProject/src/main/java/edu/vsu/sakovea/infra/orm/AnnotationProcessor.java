package edu.vsu.sakovea.infra.orm;

import edu.vsu.sakovea.infra.orm.annotations.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnotationProcessor {

    public static String getTableName(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        return (tableAnnotation != null && !tableAnnotation.name().isEmpty()) ? tableAnnotation.name() : clazz.getSimpleName().toLowerCase();
    }

    public static List<String> getColumnNames(Class<?> clazz) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(Id.class)) continue;
            Column columnAnnotation = field.getAnnotation(Column.class);
            String columnName = (columnAnnotation != null && !columnAnnotation.name().isEmpty()) ? columnAnnotation.name() : field.getName();
            columnNames.add(columnName);
        }
        return columnNames;
    }

    public static String getPrimaryKeyColumnName(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                return (columnAnnotation != null && !columnAnnotation.name().isEmpty()) ? columnAnnotation.name() : field.getName();
            }
        }
        throw new RuntimeException("No @Id annotation found for entity " + clazz.getName());
    }

    public static String getManyToOneColumnName(Field field) {
        if (field.isAnnotationPresent(ManyToOne.class)) {
            ManyToOne manyToOneAnnotation = field.getAnnotation(ManyToOne.class);
            return (manyToOneAnnotation != null && !manyToOneAnnotation.targetEntity().isEmpty()) ? manyToOneAnnotation.targetEntity() : field.getName();
        }
        return null;
    }

    public static String getOneToManyMappedBy(Field field) {
        if (field.isAnnotationPresent(OneToMany.class)) {
            OneToMany oneToManyAnnotation = field.getAnnotation(OneToMany.class);
            return (oneToManyAnnotation != null && !oneToManyAnnotation.mappedBy().isEmpty()) ? oneToManyAnnotation.mappedBy() : field.getName();
        }
        return null;
    }

    public static String getJoinColumn(Field field) {
        if (field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
            return (joinColumnAnnotation != null && !joinColumnAnnotation.name().isEmpty()) ? joinColumnAnnotation.name() : field.getName();
        }
        return null;
    }

    public static boolean isEntity(Class<?> clazz){
        return clazz.isAnnotationPresent(Entity.class);
    }

    public static <T> T mapResultSetToEntity(Class<T> clazz, ResultSet resultSet) throws SQLException {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(OneToMany.class)) continue;
                field.setAccessible(true);
                Object value = resultSet.getObject(field.getName());
                field.set(entity, value);
            }

            return entity;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Error mapping ResultSet to entity", e);
        }
    }
}