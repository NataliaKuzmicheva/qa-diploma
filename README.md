# Дипломный проект по профессии "Инженер по тестированию"
## Веб-приложение "Путешествие дня"

## Необходимая документация
- [Задание на дипломный проект](https://github.com/netology-code/qa-diploma)
- [План автоматизации](https://github.com/NataliaKuzmicheva/qa-diploma/blob/main/documents/Plan.md)
- [Отчет по результатам автоматизированного тестирования](https://github.com/NataliaKuzmicheva/qa-diploma/blob/main/documents/Report.md)
- [Отчет по автоматизации](ссылка)

## Начало работы

Для запуска автотестов необходимы следующие программы
* IntelliJ IDEA
* Docker Desktop 
* Google Chrome или другой браузер 
* Git

## Установка, запуск и генерация отчета
1. Склонировать проект с GitHub на свой компьютер командой в терминале Git Bash git clone https://github.com/NataliaKuzmicheva/qa-diploma;
2. Запустить Docker Desktop;
3. В IntelliJ IDEA открыть проект;
4. Запустить контейнер используя команду в терминале:
   ```
   docker-compose up
   ```
5. Запустить SUT в терминале при помощи команды:
    - для MySQL:
      ```
      java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
      ```
   
    - для PostgreSQL:
      ```
      java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
      ```
    
6. Сервис будет запущен в браузере по адресу: http://localhost:8080;
7. Запустить автотесты в терминале при помощи команды:
    - для MySQL:
      ```
      ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
      ```
   
    - для PostgreSQL:
      ```
      ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
      ```
8. Сгенерировать отчёт по итогам тестирования с помощью **Allure**, который автоматически откроется в браузере с помощью команды в терминале:
   ```
   ./gradlew allureServe
   ```

Закончить работу с программами: в терминале ввести сочетание клавиш _CTRL + C_ и подтвердить действие в терминале вводом _Y_.

Если необходимо перезапустить контейнеры, приложение или авто-тесты, нужно остановить работу сервисов в терминале
сочетанием клавиш _CTRL + C_ и перезапустить их, повторив шаги 4-8.

