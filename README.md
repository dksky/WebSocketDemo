# WebSocketDemo
websocket使用的demo示例（简易聊天）
此demo是使用websocket构造的简易聊天程序。包含了websocket与spring集成的搭建与简单使用。

主要包含以下几个文件：
WebSocketConfig：websocket配置类，配置拦截器，路径映射及允许域等

WebSocketHandshakeInterceptor：websocket拦截器，用于过滤请求，添加相关变量参数等

UserWebSocketHandler：用户端webSocket通讯

ReceiveDataHandler：websocket接收数据处理类

testWebSocket.jsp：聊天页面，需要设置自己的id，聊天对象的id。
