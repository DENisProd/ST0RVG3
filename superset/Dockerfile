FROM apache/superset:latest

# Скопировать файл requirements-local.txt
COPY requirements-local.txt /app/requirements-local.txt

# Установить зависимости
RUN pip install --no-cache-dir -r /app/requirements-local.txt
