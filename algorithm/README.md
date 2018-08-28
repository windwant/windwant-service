# algorithm-test

排序算法：

    1. 冒泡排序 BubbleSort 稳定
    2. 选择排序 SelectionSort 不稳定
    3. 插入排序 InsertionSort 稳定
    4. 快速排序 QuickSort 不稳定
    5. 归并排序 MergeSort 稳定
    6. 基数排序 RaidxSort 稳定
    7. 希尔排序 ShellSort 不稳定

一致性hash算法

加密算法 + 证书：
tomcat 配置ssl：

生成证书：

Keytool：

生成数字证书：自签名X509证书

PS F:\开发工具\apache-tomcat-9.0.11\conf> keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 36
000 -alias org.windwant.com -keystore windwant.store
输入密钥库口令:
再次输入新口令:
您的名字与姓氏是什么?
  [Unknown]:  org.windwant.com
您的组织单位名称是什么?
  [Unknown]:  ca
您的组织名称是什么?
  [Unknown]:  ca
您所在的城市或区域名称是什么?
  [Unknown]:  ca
您所在的省/市/自治区名称是什么?
  [Unknown]:  ca
该单位的双字母国家/地区代码是什么?
  [Unknown]:  ca
CN=org.windwant.com, OU=ca, O=ca, L=ca, ST=ca, C=ca是否正确?
  [否]:  y

输入 <org.windwant.com> 的密钥口令
        (如果和密钥库口令相同, 按回车):
再次输入新口令:
genkeypair：生成秘钥；keyalg：秘钥算法；keysize：秘钥长度；sigalg：数字签名算法；validity：有效期；alias：别名；keystore：存储位置

注意：标黑位置，证书授予者使用的域名或ip，如访问使用：https://org.windwant.com:8443

          org.windwant.com 需要在host文件里进行配置；127.0.0.1 org.windwant.com

导出：

PS F:\开发工具\apache-tomcat-9.0.11\conf> keytool -exportcert -alias org.windwant.com -keystore windwant.store -file win
dwant.cer -rfc
输入密钥库口令:
存储在文件 <windwant.cer> 中的证书

openssl:

创建随机数：

[root@zookeeper cert]# openssl rand -out private/.rand 1000

生成私钥：

[root@zookeeper cert]# openssl genrsa -aes256 -out private/ca.key.pem 2048
openssl使用PEM编码格式（privacy enbanced mail）存储私钥；genrsa：生成RSA私钥；aes256：使用aes（256位秘钥）对私钥加密

根证书签发申请：

[root@zookeeper cert]# openssl req -new -key private/ca.key.pem -out private/ca.csr
...
Country Name (2 letter code) [XX]:cn
State or Province Name (full name) []:bj
Locality Name (eg, city) [Default City]:bj
Organization Name (eg, company) [Default Company Ltd]:localhost
Organizational Unit Name (eg, section) []:localhost
Common Name (eg, your name or your server's hostname) []:localhost
Email Address []:localhost
...
req：产生证书签发申请命令；new：新请求；key：秘钥；

localhost：证书授予者使用的域名或ip

签发根证书：

[root@zookeeper cert]# openssl x509 -req -days 10000 -sha1 -extfile /etc/pki/tls/openssl.cnf -extensions v3_ca -signkey private/ca.key.pem -in private/ca.csr  -out certs/ca.cer
x509：签发x509格式证书命令；req：证书输入请求；days：有效期；sha1：证书摘要算法；extfile：配置文件；extensions：添加扩展 使用v3_ca扩展；signkey：自签名秘钥；in：签发申请文件；out：证书文件

生成keystore：

[root@zookeeper cert]# keytool -genkey -keystore ca.store

导入openssl生成的证书：

[root@zookeeper cert]# keytool -importcert -trustcacerts -alias localhost -file ca.cer -keystore ca.store


配置tomcat：

对于端口8443 添加如下配置：秘钥库路径，密码

    <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
               maxThreads="150" SSLEnabled="true" sslProtocal="TLS" keystoreFile="conf/windwant.store" keystorePass="123456">
        <!-- <SSLHostConfig>
            <Certificate certificateKeystoreFile="D:/tmp/cert/windwant.cer"
                         type="RSA" />
        </SSLHostConfig> -->
    </Connector>

浏览器导入证书：

注意：添加到受信任的根证书颁发机构


