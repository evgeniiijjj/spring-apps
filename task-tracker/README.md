# Приложение «Task Tracker».

Приложение для просмотра, добавления, удаления, сохранения и редактирования задачь.

## Функционал приложения

Приложение реализует API, co следующей спецификацией:

- Получение списка всех пользователей - GET/api/users;

        Формат ответа:

          [
            {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
            },
            {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
            }
          ]
- Получение пользователя по идентификатору - GET/api/users/user/{id};

      Формат ответа:

      {
        "id": "65d4830816cfe2010b3ea8ec",
        "userName": "Alex",
        "email": "alex@gmail.com"
      }

      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
- Добавление нового пользователя - POST/api/users/user;

        {
          "id": "65d4847d16cfe2010b3ea8ee",
          "userName": "Eugine",
          "email": "eugine@gmail.com"
        }

      Формат ответа:

        {
          "id": "65d4847d16cfe2010b3ea8ee",
          "userName": "Eugine",
          "email": "eugine@gmail.com"
        }
- Редактирование пользователя - PUT/api/users/user;

      Редактирование пользователя с id - 65d4847d16cfe2010b3ea8ee:

        {
          "id": "65d4847d16cfe2010b3ea8ee",
          "userName": "Eugine",
          "email": "eug@gmail.com"
        }

      Формат ответа в случае успеха:

        {
          "id": "65d4847d16cfe2010b3ea8ee",
          "userName": "Eugine",
          "email": "eug@gmail.com"
        }
        
      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
- Удаление пользователя по идентификатору - DELETE/api/users/user/{id};

      Формат ответа в случае успеха:

        Возвращает код ответа 204 - нет контента.
        
      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
- Получение списка всех задач - GET/api/tasks;

        Формат ответа:

            [
              {
                "id": "65d4899eb03b6c389a355a2f",
                "name": "task_1",
                "description": "description_task_1",
                "createdAt": "2024-02-20T11:14:38Z",
                "updatedAt": "2024-02-20T11:14:38Z",
                "status": "TODO",
                "author": {
                    "id": "65d4830816cfe2010b3ea8ec",
                    "userName": "Alex",
                    "email": "alex@gmail.com"
                },
                "assignee": {
                    "id": "65d4832216cfe2010b3ea8ed",
                    "userName": "John",
                    "email": "john@gmail.com"
                },
                "observers": [
                    {
                        "id": "65d4847d16cfe2010b3ea8ee",
                        "userName": "Eugine",
                        "email": "eug@gmail.com"
                    },
                    {
                        "id": "65d4897fb03b6c389a355a2e",
                        "userName": "Nick",
                        "email": "nick@gmail.com"
                    }
                ]
              },
              {
                "id": "65d48aa6e9f2c77d536c1920",
                "name": "task_2",
                "description": "description_task_2",
                "createdAt": "2024-02-20T11:19:02Z",
                "updatedAt": "2024-02-20T11:19:02Z",
                "status": "TODO",
                "author": {
                  "id": "65d4830816cfe2010b3ea8ec",
                  "userName": "Alex",
                  "email": "alex@gmail.com"
                },
                "assignee": {
                  "id": "65d4832216cfe2010b3ea8ed",
                  "userName": "John",
                  "email": "john@gmail.com"
                },
                "observers": [
                  {
                    "id": "65d4847d16cfe2010b3ea8ee",
                    "userName": "Eugine",
                    "email": "eug@gmail.com"
                  },
                  {
                    "id": "65d4897fb03b6c389a355a2e",
                    "userName": "Nick",
                    "email": "nick@gmail.com"
                  }
                ]
              }
            ]
- Получение задачи по идентификатору - GET/api/tasks/task/{id};

      Формат ответа:

      {
        "id": "65d4899eb03b6c389a355a2f",
        "name": "task_1",
        "description": "description_task_1",
        "createdAt": "2024-02-20T11:14:38Z",
        "updatedAt": "2024-02-20T11:14:38Z",
        "status": "TODO",
        "author": {
            "id": "65d4830816cfe2010b3ea8ec",
            "userName": "Alex",
            "email": "alex@gmail.com"
        },
        "assignee": {
            "id": "65d4832216cfe2010b3ea8ed",
            "userName": "John",
            "email": "john@gmail.com"
        },
        "observers": [
            {
                "id": "65d4847d16cfe2010b3ea8ee",
                "userName": "Eugine",
                "email": "eug@gmail.com"
            },
            {
                "id": "65d4897fb03b6c389a355a2e",
                "userName": "Nick",
                "email": "nick@gmail.com"
            }
        ]
      }

      Формат ответа в случае ошибки:
      
              Возвращает код ответа 404 - не найдено.
- Добавление новой задачи - POST/api/tasks/task;

        {
          "id": null,
          "name": "task_1",
          "description": "description_task_1",
          "createdAt": "2024-02-20T11:14:38Z",
          "updatedAt": "2024-02-20T11:14:38Z",
          "status": "TODO",
          "author": {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
          },
          "assignee": {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
          },
          "observers": [
              {
                  "id": "65d4847d16cfe2010b3ea8ee",
                  "userName": "Eugine",
                  "email": "eug@gmail.com"
              },
              {
                  "id": "65d4897fb03b6c389a355a2e",
                  "userName": "Nick",
                  "email": "nick@gmail.com"
              }
          ]
        }

      Формат ответа:

        {
          "id": 65d48aa6e9f2c77d536c1920,
          "name": "task_1",
          "description": "description_task_1",
          "createdAt": "2024-02-20T11:14:38Z",
          "updatedAt": "2024-02-20T11:14:38Z",
          "status": "TODO",
          "author": {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
          },
          "assignee": {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
          },
          "observers": [
              {
                  "id": "65d4847d16cfe2010b3ea8ee",
                  "userName": "Eugine",
                  "email": "eug@gmail.com"
              },
              {
                  "id": "65d4897fb03b6c389a355a2e",
                  "userName": "Nick",
                  "email": "nick@gmail.com"
              }
          ]
        }
- Добавление наблюдателя задачи - PUT/api/tasks/task/{id}

      Добавление задаче с идентификатором - 65d48aa6e9f2c77d536c1920, наблюдателя с идентификатором - 65d4832216cfe2010b3ea8ed:
        
        {
            "id": "65d4832216cfe2010b3ea8ed",
        }

      Формат ответа в случае успеха:

        {
          "id": 65d48aa6e9f2c77d536c1920,
          "name": "updated_task",
          "description": "description_updated_task",
          "createdAt": "2024-02-20T11:14:38Z",
          "updatedAt": "2024-02-20T11:14:38Z",
          "status": "TODO",
          "author": {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
          },
          "assignee": {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
          },
          "observers": [
              {
                  "id": "65d4847d16cfe2010b3ea8ee",
                  "userName": "Eugine",
                  "email": "eug@gmail.com"
              },
              {
                  "id": "65d4832216cfe2010b3ea8ed",
                  "userName": "John",
                  "email": "john@gmail.com"
              },
              {
                  "id": "65d4897fb03b6c389a355a2e",
                  "userName": "Nick",
                  "email": "nick@gmail.com"
              }
          ]
        }
        
      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
- Редактирование задачи - PUT/api/tasks/task;

      Редактирование задачи с идентификатором - 65d48aa6e9f2c77d536c1920:

        {
          "id": 65d48aa6e9f2c77d536c1920,
          "name": "updated_task",
          "description": "description_updated_task",
          "createdAt": "2024-02-20T11:14:38Z",
          "updatedAt": "2024-02-20T11:14:38Z",
          "status": "TODO",
          "author": {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
          },
          "assignee": {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
          },
          "observers": [
              {
                  "id": "65d4847d16cfe2010b3ea8ee",
                  "userName": "Eugine",
                  "email": "eug@gmail.com"
              },
              {
                  "id": "65d4897fb03b6c389a355a2e",
                  "userName": "Nick",
                  "email": "nick@gmail.com"
              }
          ]
        }

      Формат ответа в случае успеха:

        {
          "id": 65d48aa6e9f2c77d536c1920,
          "name": "updated_task",
          "description": "description_updated_task",
          "createdAt": "2024-02-20T11:14:38Z",
          "updatedAt": "2024-02-20T11:14:38Z",
          "status": "TODO",
          "author": {
              "id": "65d4830816cfe2010b3ea8ec",
              "userName": "Alex",
              "email": "alex@gmail.com"
          },
          "assignee": {
              "id": "65d4832216cfe2010b3ea8ed",
              "userName": "John",
              "email": "john@gmail.com"
          },
          "observers": [
              {
                  "id": "65d4847d16cfe2010b3ea8ee",
                  "userName": "Eugine",
                  "email": "eug@gmail.com"
              },
              {
                  "id": "65d4897fb03b6c389a355a2e",
                  "userName": "Nick",
                  "email": "nick@gmail.com"
              }
          ]
        }
        
      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
- Удаление задачи по идентификатору - DELETE/api/tasks/task/{id};

      Формат ответа в случае успеха:

        Возвращает код ответа 204 - нет контента.
        
      Формат ответа в случае ошибки:

        Возвращает код ответа 404 - не найдено.
Приложение взаимодействует с базой данный mongodb, разворачиваемой в контейнере docker при помощи файла docker-compose.yaml