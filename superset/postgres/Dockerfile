# Используем официальный образ PostgreSQL
FROM postgres:16

ENV POSTGRES_USER=superset
ENV POSTGRES_PASSWORD=password
ENV POSTGRES_DB=superset

# Копируем SQL-скрипт в контейнер
COPY init.sql /docker-entrypoint-initdb.d/