docker compose -f ./backend/docker-compose.yml up -d

docker compose -f ./superset/docker-compose.yml build
docker compose -f ./superset/docker-compose.yml up -d

docker compose -f ./airflow/docker-compose.yml build
docker compose -f ./airflow/docker-compose.yml up -d

