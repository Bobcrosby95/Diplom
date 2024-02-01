## Название проекта
Дипломный проект — автоматизация тестирования, взаимодействующего с СУБД и API Банка.

## Начало работы
Проект размещен в удаленном репозитории на сайте GITHUB, чтобы скопировать проект к себе на компьютер, необходимо открыть терминал и ввести команду git clone с ссылкой на проект (https://github.com/Bobcrosby95/Diplom).

## Prerequisites
Для реализации проекта необходимы:
- Браузер (Google Chrome),
- IntelliJ IDEA.

## Установка и запуск
1. В IntelliJ IDEA был подготовлен файл docker-compose.yml (https://github.com/Bobcrosby95/Diplom/blob/main/docker-compose.yml) для запуска контейнеров.
2. В IntelliJ IDEA был подготовлен файл Dockerfile (https://github.com/Bobcrosby95/Diplom/blob/main/gate-simulator/Dockerfile) для запуска эмулятора.
3. Запустить Docker Desktop.
4. В терминале IntelliJ IDEA вводится команда для запуска контейнеров docker-compose up.
5. В IntelliJ IDEA запускается джарник командой java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar и командой java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar.
6. В консоли для запуска автотестов используется команда ./gradlew clean test.

## Ссылки на проектную документацию
