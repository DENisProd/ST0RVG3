from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime
from airflow.hooks.base_hook import BaseHook

def test_db_connection():
    conn = BaseHook.get_connection("store_db")  # ID подключения
    print(f"Host: {conn.host}, Schema: {conn.schema}, Login: {conn.login}")

with DAG(
    dag_id='test_db_connection',
    start_date=datetime(2023, 1, 1),
    schedule_interval=None,
    catchup=False,
) as dag:
    task = PythonOperator(
        task_id='test_connection',
        python_callable=test_db_connection,
    )
