# Приложение «Сервис Новостей».

Приложение для просмотра, добавления, удаления, сохранения и редактирования новостей и комментариев к ним.

## Функционал приложения

Приложение реализует API, co следующей спецификацией:

- Получение списка всех пользователей - GET/api/users?pageNumber=0&pageSize=10;

        Формат ответа:

            [
        
                {
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "email": "email"
                },
                {
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "email": "email"
                },
            ]
- Создание нового пользователя или редактирование существующего - POST/api/users/user;

      Создание пользователя:

        {
          "firstName": "firstName",
          "lastName": "lastName",
          "email": "email"
        }
      Редактирование пользователя с email: email:

        {
          "firstName": "firstName",
          "lastName": "lastName",
          "email": "email"
        }

      Формат ответа в случае успеха:

        {
          "firstName": "firstName",
          "lastName": "lastName",
          "email": "email"
        }
        
      Формат ответа в случае ошибки:

        Имя пользователя должно быть указано!
        Фамилия пользователя должно быть указано!
        Электронная почта пользователя должна быть указана!
- Удаление пользователя по email - DELETE/api/users/user/{email};

      Формат ответа в случае успеха:

        Пользователь с email: email успешно удален!
        
      Формат ответа в случае ошибки:

        Пользователь с email: email не найден!
- Получение списка всех категорий новостей - GET/api/categories?pageNumber=0&pageSize=10;

        Формат ответа:

            [
        
                {
                    "id": 1,
                    "name": "name"
                },
                {
                    "id": 2,
                    "name": "name"
                },
            ]
- Создание новой категории или редактирование существующей - POST/api/categories/category;

      Создание категории:

        {
          "id": null,
          "name": "name"
        }
      Редактирование новости с id: 23:

        {
          "id": 23,
          "name": "name"
        }

      Формат ответа в случае успеха:

        {
          "id": 23,
          "name": "name"
        }
        
      Формат ответа в случае ошибки:

        Имя категории должно быть указано!
- Удаление категории по ID - DELETE/api/categories/category/{id};

      Формат ответа в случае успеха:

        id: 10
        
      Формат ответа в случае ошибки:

        Категории с id: 10 не существует!
- Получение списка всех новостей - GET/api/news?pageNumber=0&pageSize=10&userId=1&categoryId=0;

      Формат ответа:

        [
    
            {
                "id": 1,
                "content": "content"
                "category": {
                    "id": 1,
                    "name": "name"
                }
                "user": {
                    "id": 1,
                    "firstName": "firstName"
                    "lastName": "lastName",
                    "email": "email"
                }
                "commentsNum": 5
            },
            {
                "id": 2,
                "content": "content"
                "category": {
                    "id": 2,
                    "name": "name"
                }
                "user": {
                    "id": 2,
                    "firstName": "firstName"
                    "lastName": "lastName",
                    "email": "email"
                }
                "commentsNum": 15
            },
        ]
- Создание новой новости или редактирование существующей - POST/api/news/news;

      Создание новости:

        {
            "id": null,
            "content": "content"
            "category": {
                "id": 15,
                "name": "name"
            }
            "user": {
                "id": 11,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
        }
      Редактирование новости с id: 12:

        {
            "id": 12,
            "content": "content"
            "category": {
                "id": 15,
                "name": "name"
            }
            "user": {
                "id": 11,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
        }

      Формат ответа в случае успеха:

        {
            "id": 12,
            "content": "content"
            "category": {
                "id": 15,
                "name": "name"
            }
            "user": {
                "id": 11,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
            "commentsNum": 25
        }
        
      Формат ответа в случае ошибки:

        Новость должна содержать не пустой контент!
        Данная категория отсутствует!
        Данный пользователь отсутствует!
        Пользователь с id: 10 не имеет прав на редактирование данной новости!

- Удаление новости по ID - DELETE/api/categories/category/{id};

      Формат ответа в случае успеха:

        id: 11
        
      Формат ответа в случае ошибки:

        Новости с id: 11 не существует!
- Получение списка всех комментариев к новости, либо списка всех комментариев пользователя - GET/api/comments?pageNumber=0&pageSize=10&newsId=11&userId=0;

      Формат ответа:

        [
    
            {
                "id": 11,
                "comment": "comment"
                "user": {
                    "id": 1,
                    "firstName": "firstName"
                    "lastName": "lastName",
                    "email": "email"
                }
                "news": {
                    "id": 1,
                    "content": "content"
                    "category": {
                          "id": 15,
                          "name": "name"
                    }
                    "user": {
                          "id": 11,
                          "firstName": "firstName"
                          "lastName": "lastName",
                          "email": "email"
                    }
                    "commentsNum": 25
                }
            },
            {
                "id": 21,
                "comment": "comment"
                "user": {
                    "id": 1,
                    "firstName": "firstName"
                    "lastName": "lastName",
                    "email": "email"
                }
                "news": {
                    "id": 31,
                    "content": "content"
                    "category": {
                          "id": 3,
                          "name": "name"
                    }
                    "user": {
                          "id": 41,
                          "firstName": "firstName"
                          "lastName": "lastName",
                          "email": "email"
                    }
                    "commentsNum": 13
                }
            },
        ]
- Создание нового комментария или редактирование существующего - POST/api/comments/comment;

      Создание комментария:

        {
            "id": null,
            "comment": "comment"
            "user": {
                "id": 35,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
            "news": {
                "id": 32,
                "content": "content"
                "category": {
                      "id": 7,
                      "name": "name"
                }
                "user": {
                      "id": 3,
                      "firstName": "firstName"
                      "lastName": "lastName",
                      "email": "email"
                }
            }
        }
      Редактирование комментария с id: 33:

        {
            "id": 33,
            "comment": "comment"
            "user": {
                "id": 35,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
            "news": {
                "id": 17,
                "content": "content"
                "category": {
                      "id": 7,
                      "name": "name"
                }
                "user": {
                      "id": 3,
                      "firstName": "firstName"
                      "lastName": "lastName",
                      "email": "email"
                }
            }
        }

      Формат ответа в случае успеха:

        {
            "id": 33,
            "comment": "comment"
            "user": {
                "id": 35,
                "firstName": "firstName"
                "lastName": "lastName",
                "email": "email"
            }
            "news": {
                "id": 32,
                "content": "content"
                "category": {
                      "id": 7,
                      "name": "name"
                }
                "user": {
                      "id": 3,
                      "firstName": "firstName"
                      "lastName": "lastName",
                      "email": "email"
                }
                "commentsNum": 52
            }
        }
        
      Формат ответа в случае ошибки:

        Комментарий не должен быть пустым!
        Данный пользователь не существует!
        Данная новость не существует!
        Пользователь с id: 10 не имеет прав на редактирование данного комментария!

- Удаление комментария по ID - DELETE/api/comments/comment/{id};

      Удаление комментария c id 71 пользователем с id 34:

        {
          "id": 34
        }

      Формат ответа в случае успеха:

        id: 71
        
      Формат ответа в случае ошибки:

        Комментария с id: 71 не существует!
        Пользователь с id: 34 не имеет прав на редактирование данного комментария!
Приложение взаимодействует с базой данный postgresql, разворачиваемой в контейнере docker при помощи файла docker-compose.yaml