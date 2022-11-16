## MAGNIT technical task

## Lapynin Andrey, 11.2022

### 1. Run

```bash
java -jar magnit-test-0.0.1-SNAPSHOT.war
```

### 2. Address

```bash
localhost:8080
```

### 3. Endpoints

* **GET**  /magnit/save/{number} - создание number-количества сущностей
* **GET**  /magnit/table - получение сущностей из БД в формате xml
* **GET**  /magnit/convert-table - получение сущностей из БД в формате xml и конвертация с помощью xslt файла
* **GET**  /magnit/sum-fields - получение суммы полей всех строк в таблцице БД