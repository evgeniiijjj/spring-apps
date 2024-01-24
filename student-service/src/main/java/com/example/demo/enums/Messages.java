package com.example.demo.enums;

import com.example.demo.entities.Student;

import java.util.Arrays;

public enum Messages {

    ID_MISSING("id - %d is missing", 0),
    LINE("_", 50),
    LINE_SEPARATOR("\n", 0),
    ROW("%5s : %15s : %15s : %-5s", 0),
    TABLE("", 0),
    TABLE_TITLES("", 0, "Id", "First name", "Last name", "Age");

    private final String message;
    private final int repeat;
    private final String[] titles;

    Messages(String message, int repeat, String... titles) {
        this.message = message;
        this.repeat = repeat;
        this.titles = titles;
    }

    public String getStringMessage(Object... objects) {
        return switch (this) {
            case ROW -> getStringRows(objects);
            case ID_MISSING ->
                    String.format(message, objects[0]);
            case TABLE -> getStringTable(objects);
            case TABLE_TITLES -> ROW.getStringRow(titles);
            default -> message.repeat(repeat);
        };
    }

    private String getStringTable(Object... students) {
        return LINE.getStringMessage() +
                LINE_SEPARATOR.message +
                TABLE_TITLES.getStringMessage() +
                LINE_SEPARATOR.message +
                LINE.getStringMessage() +
                ROW.getStringRows(students) +
                LINE_SEPARATOR.message +
                LINE.getStringMessage();
    }

    private String getStringRows(Object... students) {
        return Arrays
                .stream(students)
                .filter(this::isStudent)
                .map(obj -> (Student) obj)
                .map(student ->
                        getStringRow(
                                String.valueOf(student.getId()),
                                student.getFirstName(),
                                student.getLastName(),
                                String.valueOf(student.getAge())
                        )
                )
                .reduce(
                        new StringBuilder(),
                        (sb, str) -> sb
                                .append(LINE_SEPARATOR.message)
                                .append(str),
                        StringBuilder::append
                )
                .toString();
    }

    private boolean isStudent(Object object) {
        return Student.class.equals(object.getClass());
    }

    private String getStringRow(String... strings) {
        if (strings.length == 4) {
            return String.format(message, strings[0], strings[1], strings[2], strings[3]);
        }
        return "";
    }
}
