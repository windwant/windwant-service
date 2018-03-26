# consul-test

服务发现和配置

What is Consul?

Consul has multiple components, but as a whole, it is a tool for discovering and configuring services in your infrastructure. It provides several key features:

Service Discovery: Clients of Consul can provide a service, such as api or mysql, and other clients can use Consul to discover providers of a given service. Using either DNS or HTTP, applications can easily find the services they depend upon.

Health Checking: Consul clients can provide any number of health checks, either associated with a given service ("is the webserver returning 200 OK"), or with the local node ("is memory utilization below 90%"). This information can be used by an operator to monitor cluster health, and it is used by the service discovery components to route traffic away from unhealthy hosts.

KV Store: Applications can make use of Consul's hierarchical key/value store for any number of purposes, including dynamic configuration, feature flagging, coordination, leader election, and more. The simple HTTP API makes it easy to use.

Multi Datacenter: Consul supports multiple datacenters out of the box. This means users of Consul do not have to worry about building additional layers of abstraction to grow to multiple regions.

Consul is designed to be friendly to both the DevOps community and application developers, making it perfect for modern, elastic infrastructures.

» Basic Architecture of Consul

Consul is a distributed, highly available system. This section will cover the basics, purposely omitting some unnecessary detail, so you can get a quick understanding of how Consul works. For more detail, please refer to the in-depth architecture overview.

Every node that provides services to Consul runs a Consul agent. Running an agent is not required for discovering other services or getting/setting key/value data. The agent is responsible for health checking the services on the node as well as the node itself.

The agents talk to one or more Consul servers. The Consul servers are where data is stored and replicated. The servers themselves elect a leader. While Consul can function with one server, 3 to 5 is recommended to avoid failure scenarios leading to data loss. A cluster of Consul servers is recommended for each datacenter.

Components of your infrastructure that need to discover other services or nodes can query any of the Consul servers or any of the Consul agents. The agents forward queries to the servers automatically.

Each datacenter runs a cluster of Consul servers. When a cross-datacenter service discovery or configuration request is made, the local Consul servers forward the request to the remote datacenter and return the result.

Service discovery and configuration made easy. Distributed, highly available, and datacenter-aware.


服务发现和配置，分布式，高可用性，数据中心。
服务注册 - 服务端注册相应的的主机、端口号及其它身份验证信息，协议，版本号，以及运行环境等详细资料。
服务发现 - 客户端应用通过向注册中心查询，获取可用服务列表，相应服务详细信息。
基本服务格式：
 1 {
 2   "service":{
 3     "id": "myservice",
 4     "name": "myservice",
 5     "address": "servicehost",
 6     "port": serviceport,
 7     "tags": ["tag"],
 8     "checks": [
 9         {
10             "http": "http://host.port/health",
11             "interval": "5s"
12         }
13     ]
14   }
15 }
 

健康检查（checks）：
script check：consul主动去检查服务的健康状况

1 {
2   "check": {
3     "id": "scheck",
4     "name": "scheck",
5     "script": "/*.py", //必须
6     "interval": "10s", //必须
7     "timeout": "1s"
8   }
9 }
ttl check：服务主动向consul报告自己的健康状况

1 {
2   "check": {
3     "id": "scheck",
4     "name": "scheck",
5     "notes": "scheck",
6     "ttl": "30s"
7   }
8 }
http check:

1 {
2   "check": {
3     "id": "scheck",
4     "name": "scheck",
5     "http": "http://host:port/health",
6     "interval": "10s",
7     "timeout": "1s"
8   }
9 }
tcp check：

1 {
2   "check": {
3     "id": "scheck",
4     "name": "scheck",
5     "tcp": "host:22",
6     "interval": "10s",
7     "timeout": "1s"
8   }
9 }

服务注册：
配置文件静态注册：
    /etc/consul.d/myserver.json
    添加如上服务配置
    重启consul，并将配置文件的路径给consul（指定参数：-config-dir /etc/consul.d）
HTTP API接口来动态注册：
    /v1/agent/service/register http put方法注册
    url -X PUT -d '{"id": "myserver","name": "myserver","address": "serverhost","port": serverport,"tags": ["tag"],"checks": [{"http": "http://healthhost:port/health","interval": "5s"}]}' http://host:8500/v1/agent/service/register
    
maven dependency：
1 <dependency>
2     <groupId>com.orbitz.consul</groupId>
3     <artifactId>consul-client</artifactId>
4     <version>xxx</version>
5 </dependency>
1 <dependency>
2     <groupId>com.ecwid.consul</groupId>
3     <artifactId>consul-api</artifactId>
4     <version>xxx</version>
5 </dependency>
 
