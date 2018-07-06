# rocketmq
rocketmq

Clone & Build
> git clone -b develop https://github.com/apache/rocketmq.git
> cd rocketmq
> mvn -Prelease-all -DskipTests clean install -U
> cd distribution/target/apache-rocketmq

Start Name Server


> nohup sh bin/mqnamesrv &
> tail -f ~/logs/rocketmqlogs/namesrv.log
The Name Server boot success...

Start Broker


> nohup sh bin/mqbroker -n localhost:9876 &
> tail -f ~/logs/rocketmqlogs/broker.log
The broker[%s, 172.30.30.233:10911] boot success...

windows下mqbroker启动失败：
    解决办法，删除C:\Users\"当前系统用户名"\store下的所有文件，就可以了
    .\mqbroker.cmd -n localhost:9876
