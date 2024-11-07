# GithubInsight

本项目是一个基于Github API的数据分析项目，主要用于分析Github上的开发者信息，并根据多方面的数据，通过大语言模型对其作出综合评价。

## 项目架构

本项目项目采用前后端分离的架构，前端采用compose multiplatform， 后端采用ktor，数据库采用mongodb。
通过Github API获取开发者信息，并通过大语言模型对其作出综合评价，处理过的信息会被缓存进mongodb数据库，以提高查询速度。

### multiplatform

前端通过compose multiplatform实现了桌面端和安卓端的统一体验和代码复用。
通过commonMain中的共同代码，可以实现对UI和逻辑的统一管理，同时通过kotlin multiplatform的特性，可以实现特定平台的特化处理。
由于compose multiplatform对其他平台的支持还不够完善，目前只支持了安卓端和桌面端。

### Kotlin协程

后端采用了ktor框架，通过ktor提供的kotlin协程支持，实现了高并发、非阻塞的数据处理和API调用。
同时，mongodb作为优秀的nosql数据库，可以提供高效的分布式数据存储和查询服务，提供对Kotlin协程的原生支持，以支持后端的高并发处理。

### 非反射的序列化和反序列化

通过编译器插件，实现了对数据的非反射序列化和反序列化，提高了序列化和反序列化的性能。

### 通用的GPT API接口和Agent构建

通过langchain4kt库（作者为本项目队员之一Stream）的支持，得以忽略底层API差异，搭建高度通用化的LLM应用。

### 分布式部署、API负载平衡和故障转移，实现高可用性
不论是GPT的调用还是Github API的调用，都支持在后端配置文件中进行配置，以便于后期的维护和扩展。
在配置文件中配置多个GPT API或Github token，可以实现负载均衡和故障转移，避免单一key或token的访问上限影响整体服务可用性。
后端采用了无状态的设计，可以通过多个实例的分布式部署提升接口的承载能力。

## 使用说明

使用gradle构建backend-server项目，并且在jar的工作目录下放置配置文件config.yml，配置文件的格式如下：

```yaml
mongodb:
  connection_string: "mongodb://localhost:27017" # mongodb连接地址
chatApi:
  - type: "baidu_qianfan"
    api_key: "your_api_key" # 百度API的API Key
    secret_key: "your_secret_key" # 百度API的Secret Key
    model: "ernie-4.0-8k-latest" # 百度API的模型
  - type: "google_gemini"
    api_key: "your_api_key" # 谷歌API的API Key
    model: "gemini-1.5-flash" # 谷歌API的模型
githubApi:
  - token: "your_token" # Github token
```

后端运行在8081端口，因为时间原因来不及打包做成docker镜像了。

前端构建后会产生两个target，分别是desktop和android，分别对应桌面端和安卓端。

输入要查找的Github用户名，点击submit按钮，即可查看该用户的信息。