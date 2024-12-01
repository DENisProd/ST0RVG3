# К0РП0РVТИВН0E XРVHИЛИЩE DVHHЫX
## Стек технологий:
- *Языки программирования:* Java (Spring, Spring Gateway), Python
- *Системы хранения:* PostgreSQL, Redis, MinIO
- *Оркестровка потоков операций обработки данных:* Apache Airflow
- *BI система:* Apache Superset
- *Система авторизации:* Keycloak
- *Брокер-сообщений:* Apache Kafka
- *Антивирусное ПО для файлов:* ClamAV

## Куда заходить?
1. https://warehouse.darksecrets.ru - Frontend для загрузки файлов (в активной разработке)
2. https://bi.darksecrets.ru - Apache Superset.
    Login: admin
    Password: password
3. https://minio.darksecrets.ru - MinIO
    Login: root
    Password: password
4. https://airflow.darksecrets.ru - Apache Airflow
    Login: airflow
    Password: airflow
5. https://auth.darksecrets.ru - Keycloak
    Login:
    Password:

## Как развернуть?
Пока что не красиво и изящно...

```bash
    docker compose -f ./airflow/docker-compose.yml build
    docker compose -f ./airflow/docker-compose.yml up -d

    docker compose -f ./superset/docker-compose.yml build
    docker compose -f ./superset/docker-compose.yml up -d

    docker compose -f ./backend/docker-compose.yml up -d
```
## Архитектура
![image](https://github.com/user-attachments/assets/688ab44b-51ea-40c1-9224-12317c9fb560)


## Примеры работы
