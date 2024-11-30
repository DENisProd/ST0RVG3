import pendulum
from airflow.decorators import dag, task
from airflow.hooks.base import BaseHook
import pandas as pd
import os
from sqlalchemy import create_engine, text
from sqlalchemy.exc import SQLAlchemyError
import logging

logger = logging.getLogger(__name__)

# Конфигурация подключения PostgreSQL через Airflow Connection
POSTGRES_CONN_ID = 'store_db'  # Убедитесь, что подключение настроено в Airflow

LOCAL_REPO_PATH = r'C:\Users\HP\Documents\GitHub\ST0RVG3\airflow\app\data'

CSV_FILES = {
    'Customers': 'Customers.csv',
    'CreditProducts': 'CreditProducts.csv',
    'CreditAgreements': 'CreditAgreements.csv',
    'TransactionTypes': 'TransactionTypes.csv',
    'CreditTransactions': 'CreditTransactions.csv'
}

CREATE_TABLES_SQL = """ 
CREATE TABLE IF NOT EXISTS Customers ( 
    CustomerID SERIAL PRIMARY KEY, 
    CustomerTypeID INTEGER NOT NULL CHECK (CustomerTypeID IN (1, 2)), 
    Name VARCHAR(255) NOT NULL, 
    DateOfBirth DATE, 
    RegistrationDate DATE, 
    TIN VARCHAR(50) NOT NULL UNIQUE, 
    ContactInfo VARCHAR(255), 
    CHECK ( 
        (CustomerTypeID = 1 AND DateOfBirth IS NOT NULL AND RegistrationDate IS NULL) OR 
        (CustomerTypeID = 2 AND RegistrationDate IS NOT NULL AND DateOfBirth IS NULL) 
    ) 
); 

CREATE TABLE IF NOT EXISTS CreditProducts ( 
    CreditProductID SERIAL PRIMARY KEY, 
    ProductName VARCHAR(255) NOT NULL, 
    InterestRate DECIMAL(5, 2) NOT NULL CHECK (InterestRate >= 0), 
    MaxLoanAmount NUMERIC(15, 2) NOT NULL CHECK (MaxLoanAmount > 0), 
    MinRepaymentTerm INTEGER NOT NULL CHECK (MinRepaymentTerm > 0), 
    CollateralRequired BOOLEAN NOT NULL DEFAULT FALSE 
); 

CREATE TABLE IF NOT EXISTS CreditAgreements ( 
    CreditAgreementID SERIAL PRIMARY KEY, 
    CustomerID INTEGER NOT NULL, 
    CreditProductID INTEGER NOT NULL, 
    AgreementDate DATE NOT NULL, 
    LoanAmount NUMERIC(15, 2) NOT NULL CHECK (LoanAmount > 0), 
    LoanTerm INTEGER NOT NULL CHECK (LoanTerm > 0), 
    InterestRate DECIMAL(5, 2) NOT NULL CHECK (InterestRate >= 0), 
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE, 
    FOREIGN KEY (CreditProductID) REFERENCES CreditProducts(CreditProductID) ON DELETE RESTRICT 
); 

CREATE TABLE IF NOT EXISTS TransactionTypes ( 
    TransactionTypeID SERIAL PRIMARY KEY, 
    TransactionTypeName VARCHAR(100) NOT NULL UNIQUE 
); 

CREATE TABLE IF NOT EXISTS CreditTransactions ( 
    TransactionID SERIAL PRIMARY KEY, 
    CustomerID INTEGER NOT NULL, 
    CreditAgreementID INTEGER NOT NULL, 
    TransactionDate DATE NOT NULL, 
    TransactionAmount NUMERIC(15, 2) NOT NULL, 
    TransactionTypeID INTEGER NOT NULL, 
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE CASCADE, 
    FOREIGN KEY (CreditAgreementID) REFERENCES CreditAgreements(CreditAgreementID) ON DELETE CASCADE, 
    FOREIGN KEY (TransactionTypeID) REFERENCES TransactionTypes(TransactionTypeID) ON DELETE RESTRICT 
); 
""" 

default_args = {
    "owner": "airflow", 
    'retries': 1, 
    'retry_delay': pendulum.duration(minutes=5), 
} 

@dag( 
    default_args=default_args, 
    dag_id='create_tables_and_load_csv', 
    schedule_interval=None,  # Запуск вручную 
    start_date=pendulum.datetime(2024, 1, 1, tz="UTC"), 
    catchup=False, 
    tags=['postgres', 'csv', 'setup'], 
    render_template_as_native_obj=True, 
) 
def create_tables_and_load_csv(): 

    @task()
    def create_tables(): 
        """ 
        Создаёт таблицы в базе данных PostgreSQL. 
        """ 
        connection = BaseHook.get_connection(POSTGRES_CONN_ID) 
        # Заменяем 'postgres://' на 'postgresql+psycopg2://'
        conn_uri = connection.get_uri()
        if conn_uri.startswith('postgres://'):
            conn_uri = conn_uri.replace('postgres://', 'postgresql://', 1)
            print(conn_uri)
        elif not conn_uri.startswith('postgresql'):
            conn_uri = 'postgresql://' + conn_uri
            print(conn_uri)

        engine = create_engine(conn_uri)

        try: 
            with engine.connect() as conn: 
                conn.execute(text(CREATE_TABLES_SQL)) 
                logger.info("Таблицы успешно созданы или уже существуют.")
        except SQLAlchemyError as e: 
            logger.error(f"Ошибка при создании таблиц: {e}") 
            raise 

    @task() 
    def read_csv_files(): 
        """ 
        Читает CSV-файлы из локального репозитория и возвращает их в виде словаря pandas DataFrame. 
        """ 
        data_frames = {} 
        for table, filename in CSV_FILES.items(): 
            file_path = os.path.join(LOCAL_REPO_PATH, filename) 
            if os.path.exists(file_path): 
                try: 
                    df = pd.read_csv(file_path) 
                    data_frames[table] = df 
                    logger.info(f"Файл {filename} успешно прочитан для таблицы {table}.") 
                except pd.errors.ParserError as e: 
                    logger.error(f"Ошибка при чтении файла {filename}: {e}") 
                    raise Exception(f"Ошибка при чтении файла {filename}: {e}") 
            else: 
                logger.error(f"Файл {filename} не найден по пути {file_path}.") 
                raise FileNotFoundError(f"Файл {filename} не найден по пути {file_path}.") 
        return data_frames 

    @task() 
    def insert_data_into_postgres(data_frames: dict): 
        """ 
        Вставляет данные из pandas DataFrame в соответствующие таблицы. 
        """ 
        connection = BaseHook.get_connection(POSTGRES_CONN_ID) 
        # Заменяем 'postgres://' на 'postgresql+psycopg2://'
        conn_uri = connection.get_uri()
        if conn_uri.startswith('postgres://'):
            conn_uri = conn_uri.replace('postgres://', 'postgresql://', 1)
            print(conn_uri)
        elif not conn_uri.startswith('postgresql'):
            conn_uri = 'postgresql://' + conn_uri
            print(conn_uri)

        engine = create_engine(conn_uri)

        for table, df in data_frames.items(): 
            try: 
                df.to_sql(table, engine, if_exists='append', index=False) 
                logger.info(f"Данные из {table} успешно вставлены.") 
            except SQLAlchemyError as e: 
                logger.error(f"Ошибка при вставке данных в таблицу {table}: {e}") 
                raise  

    create_tables_task = create_tables()
    read_csv_task = read_csv_files() 
    insert_data_task = insert_data_into_postgres(read_csv_task) 

    create_tables_task >> read_csv_task >> insert_data_task 

dag = create_tables_and_load_csv()
