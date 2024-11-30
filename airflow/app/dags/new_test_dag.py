def test_postgres_connection():    POSTGRES_CONN_ID = 'superset_db'
    postgres_hook = PostgresHook(postgres_conn_id=POSTGRES_CONN_ID)    
    try:
        connection = postgres_hook.get_conn()        cursor = connection.cursor()
        cursor.execute("SELECT 1;")        result = cursor.fetchone()
        print("Подключение успешно:", result)    except Exception as e:
        print("Ошибка подключения:", e)        
test_postgres_connection()
