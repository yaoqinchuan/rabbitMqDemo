# rabbitMqDemo
### 1、环境准备

docker run -d --hostname my-rabbit --name rabbit -p 15672:15672  -p 5672:5672 -v /opt/rabbitmq/data:/var/lib/rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=123456 rabbitmq 

随后docker exec -it 容器id /bin/bash

运行：rabbitmq-plugins enable rabbitmq_management

echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf 

退出容器后

docker restart rabbitmq

### 2、启动工程验证

