<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My WebSocket</title>
  </head>
   
  <body>
    Welcome<br/>
            当前用户id：<input type="text" id="userId" ></br>
            聊天好友id：<input type="text" id="toUserId" ></br></br>
            消息：
    <textarea id="text" type="text" rows="4"></textarea></br>
    <button onclick="connect()">Connect</button>    
    <button onclick="send()">Send</button>    
    <button onclick="closeWebSocket()">Close</button>
    <div id="message">
    </div>
  </body>
  <script src="static/jquery-1.9.1.min.js"></script>
  <script type="text/javascript">
      var websocket = null;
      var userId = "1";
      
      function connect() {
    	  userId = $("#userId").val();
    	  if(userId == "") {
    		  alert("当前用户id不能为空！");
    		  return;
    	  }
    	  
    	  //判断当前浏览器是否支持WebSocket
          if('WebSocket' in window){
        	  var url = "ws://localhost/f/interface/websocket/userApp?userId=" + userId;
              websocket = new WebSocket(url);
          }
          else{
              alert('Not support websocket');
          }
           
          //连接发生错误的回调方法
          websocket.onerror = function(){
              setMessageInnerHTML("error");
          };
           
          //连接成功建立的回调方法
          websocket.onopen = function(event){
              setMessageInnerHTML("open");
          };
           
          //接收到消息的回调方法
          websocket.onmessage = function(){
        	  var obj = JSON.parse(event.data);
        	  var fromUserId = obj.data.fromUserId;
        	  var message = obj.data.message;
              setMessageInnerHTML(fromUserId + "：" + message);
          };
           
          //连接关闭的回调方法
          websocket.onclose = function(){
              setMessageInnerHTML("close");
          };
           
          //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
          window.onbeforeunload = function(){
              websocket.close();
          };
      }
      
      //将消息显示在网页上
      function setMessageInnerHTML(innerHTML){
          document.getElementById('message').innerHTML += innerHTML + '<br/>';
      }
       
      //关闭连接
      function closeWebSocket(){
          websocket.close();
      }
       
      //发送消息
      function send(){
          var message = document.getElementById('text').value;
          var toUserId = $("#toUserId").val();
          if(toUserId == "") {
        	  alert("聊天好友id不能为空");
        	  return;
          }
          var json = {
		      		method: "chat",
		      		userId: userId,
		      		type: "userApp",
		      		data: {
		      			toUserId: toUserId,
		      			message: message
		      		}
		      };
		  if(websocket == null && websocket.readyState == 1) {
			  alert("请先连接服务器");
			  return;
		  }
          websocket.send(JSON.stringify(json));
          setMessageInnerHTML(userId + "->" + toUserId + "：" + message);
      }
  </script>
</html>