package org.example.utils;

import org.example.entities.Contact;
import lombok.Getter;

import java.util.Set;


public enum Printer {
    ADD("добавить новый контакт;", ""),
    ADD_SUCCESS("Контакт успешно добавлен!\n", ""),
    COMMANDS("Введите команду из списка:", "%10s - %s%n",
             "SHOW", "ADD", "SAVE", "DELETE", "SEARCH", "EXIT"),
    CONTACT_BOOK_BODY("", "%6d : %41s : %14s : %-20s%n"),
    CONTACT_BOOK_TITLE(" № п/п :           ФАМИЛИЯ ИМЯ ОТЧЕСТВО            : НОМЕР ТЕЛЕФОНА : ЭЛЕКТРОННАЯ ПОЧТА", ""),
    CONTACT_CHOOSING("выберите контакт для удаления по порядковому номеру, либо по email", ""),
    DELETE("удалить выбранный контакт из книги;", ""),
    DELETE_SUCCESS("Контакт успешно удален!\n", ""),
    EMAIL("введите электронную почту абонента:\n", "[a-z][a-z0-9]+_?-?[a-z0-9]*@[a-z]+.[a-z]+"),
    EMPTY_CONTACT_BOOK_MESSAGE("КНИГА КОНТАКТОВ ПУСТА", "%47s\n"),
    EMPTY_SEARCH_CONTACT_RESULTS("СПИСОК РЕЗУЛЬТАТОВ ПОИСКА ПУСТ", "%53s\n"),
    EXIT("выйти из книги контактов;", ""),
    INPUT_CONTACT_ID("введите порядковый номер контакта в книге", ""),
    LINE("_", "88"),
    NAME("введите Фамилию Имя Отчество абонента:\n", "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+"),
    NUMBER("", "\\d+"),
    PICK("выбрать контакт;", ""),
    PHONE("введите номер телефона абонента (10 цифр):\n","\\d{10}"),
    SAVE("сохранить контакты;", ""),
    SEARCH("поиск контакта(ов) по полю;", ""),
    SEARCH_INPUT("введите ФИО, либо номер телефона, либо email абонента", ""),
    SHOW("показать книгу контактов;", "%47s\n"),
    SHOW_SEARCH_RESULT("", ""),
    WELCOME("\nДобро пожаловать в книгу контактов!\n", ""),
    WRONG_INPUT("Неверный ввод! Введите команду из списка ниже:", ""),
    WRONG_DATA_INPUT("Неверный ввод! Попробуйте еще раз или введите EXIT для выхода в главное меню", "");


    @Getter
    private final String value;
    private final String format;
    private final String[] commands;

    Printer(String value, String format, String... strings) {

        this.value = value;
        this.format = format;
        this.commands = strings;
    }

    public void print(Object... objects) {

        switch (this) {

            case COMMANDS -> {

                System.out.println(value);

                for (String command : commands) {

                    System.out.printf(
                            format,
                            command,
                            Printer.valueOf(command).value
                    );
                }
                System.out.println();
            }

            case SHOW, SHOW_SEARCH_RESULT -> {

                LINE.print();
                CONTACT_BOOK_TITLE.print();
                LINE.print();

                Set<Contact> contacts = (Set<Contact>) objects[0];

                int count = 0;

                for (Contact contact : contacts) {
                    count++;
                    contact.setNumInOrder(count);
                    System.out.printf(
                            CONTACT_BOOK_BODY.format,
                            contact.getNumInOrder(),
                            contact.getFullName(),
                            contact.getPhoneNumber(),
                            contact.getEmail()
                    );
                    LINE.print();
                }
                if (contacts.isEmpty()) {
                    printEmpty();
                    LINE.print();
                }
                System.out.println();
            }

            case LINE -> System.out.println(value.repeat(Integer.parseInt(format)));

            case EMPTY_CONTACT_BOOK_MESSAGE, EMPTY_SEARCH_CONTACT_RESULTS ->
                    System.out.printf(format, value);

            default -> System.out.println(value);
        }
    }

    public String getRedex() {
        return format;
    }

    private void printEmpty() {

        switch (this) {

            case SHOW -> EMPTY_CONTACT_BOOK_MESSAGE.print();
            case SHOW_SEARCH_RESULT -> EMPTY_SEARCH_CONTACT_RESULTS.print();
        }
    }
}
