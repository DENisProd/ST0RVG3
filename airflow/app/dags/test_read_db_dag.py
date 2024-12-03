from airflow import DAG
from airflow.operators.python import PythonOperator
from airflow.hooks.postgres_hook import PostgresHook
from datetime import datetime
from airflow.utils.log.logging_mixin import LoggingMixin

logger = LoggingMixin().log

# Функция для чтения данных из таблицы
def read_from_customer_table():
    # Используем подключение с ID `my_postgres_db`
    postgres_hook = PostgresHook(postgres_conn_id="superset_db")
    conn = postgres_hook.get_conn()
    cursor = conn.cursor()

    # Выполняем SQL-запрос с ограничением
    cursor.execute("SELECT * FROM customer LIMIT 20;")
    rows = cursor.fetchall()

    # Выводим данные в логи
    for row in rows:
        logger.info(f"Row: {row}")

# Создаем DAG
with DAG(
    dag_id="read_customer_table",
    start_date=datetime(2023, 1, 1),
    schedule_interval=None,  # Запускается вручную
    catchup=False,
) as dag:
    read_task = PythonOperator(
        task_id="read_customer_data",
        python_callable=read_from_customer_table,
    )
