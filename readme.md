0 - dockerLab
1 - loadBalancer
2 - ELK
3 - metrics
4 - keycloak
5 - IDP

описание конфигов

# лаба 2 loadBalancer

nginx.conf:

{{range services}} {{$name := .Name}} {{$service := service .Name}}
список сервисов, обновляется когда nginx reload-ится

upstream {{$name}} { Описывает сервер или группу серверов. Серверы могут слушать на разных портах
генерится имя, адрес, порт
число неудачных попыток работы с сервером, которые должны произойти в течение времени, заданного параметром fail_timeout, чтобы сервер считался недоступным на период времени, также заданный параметром fail_timeout, 
таймаут в течение которого должно произойти заданное число неудачных попыток работы с сервером для того, чтобы сервер считался недоступным 
и вес - показывает который влияет на распределение клиентов на него

  server {} - блок сервера, различаются по портам (но у нас он 1) и слушают по имени сервера 
  listen 80 default_server;
  
  location / - сравнивается с адресом из запроса, у нас перенаправление к приложению app
  return 301 /app/;  
  
  
  location = /basic_status {
    stub_status;
} В данной конфигурации создаётся простая веб-страница с основной информацией о состоянии, которая может выглядеть следующим образом:

Active connections: 291 
server accepts handled requests
 16630948 16630948 31070465 
Reading: 6 Writing: 179 Waiting: 106 

{{range services}} {{$name := .Name}}
    location /{{$name}} {
        proxy_pass http://{{$name}};
        rewrite ^/{{$name}}/(.*)$ /$1 break;
    }
    
    здесь при подключении генерятся новые записи
    location - по имени приложения (у нас app)
    proxy_pass - перенаправления запросов на проксируемый сервер (ну на VM-2 короче) через консул
    rewrite - меняет адрес на (че написано)

# лаба 3 ELK

Elasticsearch.yml - имя узла (node) и адрес сети, ничего интересного

Logstash

pipelines.yml - настройка pipeline (там id=main и путь к конфигам (фильтрам) логстэша)

filters ( папка logstash-conf)

02-beats-input.conf - port => 5044 (входной порт логстэша), настройка SSL

10-syslog.conf - фильтр, добавляет поля когда принял и от кого приходящим syslog-ам

11-nginx.conf - тоже фильтр, grok разбивает строку на "имя поля" => "значение"

30-output.conf - указывается адрес хоста (там локалхост) и формат индекса, по которому потом через кибану ищем 
( в нашем случае там версия файлбита-дата)

клиент (filebeat) filebeat.yml

filebeat.inputs - указывается откуда брать логи ( у нас файлы от контейнеров, которые на .log кончаются)

processors - кто обрабатывает перед отправкой (добавляет метаданные докера и декодирует строки (поля message) в json объекты)

output.elasticsearch - адрес назначения (где ЕЛК) и индекс логов


