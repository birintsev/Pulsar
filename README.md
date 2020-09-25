# Pulsar

Pulsar - сервер-клієнт програмне забезпечення, що реалізує функціоонал агрегації та відображення пасивного та активного моніторингу сервісів та серверів.

### Технології

При розробкі Pulsar були використанні наступні технології:

* [**React**](https://reactjs.org/) - JavaScript-бібліотека з відкритим вихідним кодом для розробки інтерфейса користувача
* [**Node.js**](https://nodejs.org/en/) - середовище виконання JavaScript
* **Java 1.8** - об'єктно орієнтована, строго типізованна мова програмування *version*
* [**Javalin**](https://javalin.io/) - simple web framework
* [**Maven**](https://maven.apache.org/) - фреймворк для автоматизації збірки проекту
* [**Hibernate**](https://hibernate.org/) - це об’єктно-реляційний інструмент відображення для мови програмування Java
* **Git** - система для контролю  версій
*  **npm/yarn** - менеджеры пакетов
*  **jwt/package** - JSON web tocken стандарт для створення токенів доступу
* **Postman** - засіб для тестування API
* **Webstorm/VScode/IntelliJ IDEA** - среды разработки
* **Jetty** - Java HTTP сервер та Java Servlet container
* **Nginx** - веб сервер
* **PostgreSQL** - база даних
* **ssh/http/https** - протоколи передачі даних
* [**potapuff/agent**](https://github.com/potapuff/agent) - Linux monitoring agent fro course Techical Solution Support / Software Quality Assurance

### Автори та розробники

* Project manager - [Ilona Bielym](https://www.github.com/ilona-bielym)
* Back developer - [Birintsev Mykhailo] (https://www.github.com/leader228228)
* Front developer - [Yaroslav Raschupkin](https://www.github.com/yaroslav-raschupkin)
* Front developer - [name]()
* QA lead - [name]()
* Tester - [name]()
* Tester- [name]()

### Installation, інсталяція

**Backend частина Pulsar**
Запуск програми в режимі розробника

Запустіть скрипт в папкі в якій ви бажаєте розмістити проект:
```
git clone https://tss2020.repositoryhosting.com/git/tss2020/t3.git/backend
```
Відкрийте клонований репозиторій та запустіть Maven task:
```
mvn clean package
```
Якщо ви не встановлювали Maven в вашу систему, перегляньте [керівництво](https://maven.apache.org/install.html) з його встановлення.Тепер Apache Maven 3.6.3 використовується для розробки.

Потім, перейдіть до новостворенної цільової папки, запустіть скрипт:
```
java -jar Pulsar.jar start
```

### Deploy інструкція

Запуск додатку в dev/prod середовищі не має ніяки відмінностей з інсталяцією. Ви можете легко вказати розташування файлу конфігурації за допомогою *-f* опції команди *start*

**Список команди, що підтримуються**
1. *start*
    ```
    -f, -file<code> - configuration file location (a set of overriding application properties)
    ```

### Agent
Агент - це програмне забезпечення, розгорнуте на сервері клієнта. Він являє собою віддалену службу, що надсилає зібрані дані про стан системи до цієї програми. Вихідний код агента можна знайти [тут.](https://github.com/potapuff/agent)

В основному, агент - це незалежна послуга, яка може бути різною для кожного клієнта. Однак факт використання програмного забезпечення іншого агента повинен бути узгоджений з  [Borys Kuzikov](https://github.com/potapuff), [Mykhailo Birintsev](https://github.com/leader228228)  і не порушувати ліцензійні правила, документ про роботу та умови використання.

Див. приклади повідомлень агента в документі Statements of Work

### Statements of Work

Project Statements of Work document could be found [here.](https://docs.google.com/document/d/1CF3Y6A0OpV_nDartqZKGqExg75xHAdra7IPK9210BZg/edit)

### Сповіщення про баги та проблеми
Якщо ви думаєте, що ви знайшли баг. Зверніться до [QA lead](https://github.com/MarySweetRollStolen) з задокументованою проблемою з максимально можливою кількістю інформацією про те як відтворити цей баг.

### Стандарти коду 
- [**Oracle Java Code Convention**](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) 
- [**Airbnb React/JSX Style Guide**](https://github.com/airbnb/javascript/tree/master/react)

### Ліцензія
**ТSSedu license** 

Copyright 2020 ©, team 3
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
Software can’t be used by other teams at TSS courses.
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
