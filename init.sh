docker compose -f ./backend/docker-compose.yml up -d

docker compose -f ./superset/docker-compose.yml build
docker compose -f ./superset/docker-compose.yml up -d

docker compose -f ./airflow/docker-compose.yml build
docker compose -f ./airflow/docker-compose.yml up -d

docker network connect airflow_default postgres_store
docker network connect airflow_default superset_postgres

# docker network create airflow_net

# docker network connect airflow_net airflow-airflow-webserver-1
# docker network connect airflow_net postgres_store

