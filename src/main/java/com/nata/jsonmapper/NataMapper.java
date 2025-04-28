package com.nata.jsonmapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class NataMapper {

    public static String toJson(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof String || obj instanceof Character) {
            return "\"" + obj + "\"";
        }
        if (obj instanceof Integer || obj instanceof Double || obj instanceof Float || obj instanceof Boolean || obj instanceof Byte) {
            return obj.toString();
        }
        if (obj instanceof Enum<?>) {
            return "\"" + ((Enum<?>) obj).name() + "\"";
        }
        if (obj instanceof Collection<?>) {
            return collectionToJson(obj);
        }
        if (obj.getClass().isArray()) {
            return arrayToJson(obj);
        }
        return compositeToJson(obj);
    }

    public static void main(String[] args) throws IllegalAccessException {
        Scooter scooter = Scooter.builder()
            .name("SMJ Sport")
            .weight(5.0)
            .height(90)
            .length(80)
            .colors(List.of(Color.GREEN, Color.WHITE, Color.BLACK))
            .frontWheel(Wheel.builder()
                .color(Color.GREEN)
                .diameter(23)
                .solid(true)
                .tags(new String[]{"A", "B", "C"})
                .build())
            .backWheel(Wheel.builder()
                .color(Color.GREEN)
                .diameter(18)
                .solid(true)
                .build())
            .build();

        System.out.println(toJson(scooter));
    }

    private static String compositeToJson(Object obj) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Field[] allFields = obj.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            builder.append("\"")
                .append(field.getName())
                .append("\": ");
            Object value = field.get(obj);
            builder.append(toJson(value));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }

    private static String arrayToJson(Object obj) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < Array.getLength(obj); i++ ) {
            Object element = Array.get(obj, i);
            builder.append(toJson(element));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }

    private static String collectionToJson(Object obj) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Object o : (Collection<?>) obj) {
            builder.append(toJson(o));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }
}
