
1.Склонируйте репозиторий

```
git clone https://github.com/rusakovich-viktar/keycloak-migrate.git
```

2. Перейдите в ваш проект
   
```
cd ваш-проект
```

3. Скопируйте env файл и заполните его значениями
  ``` 
cp .env.example .env
```


4.Запуск через баш скрипт
```
./run.sh
```

или
```
./mvnw clean package
```
```
java -jar target/keycloak-migrate-0.0.1.jar
```


