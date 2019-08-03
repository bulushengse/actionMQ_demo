<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form action="mqTest/sendMsgTest" method="post">
        <input type="submit" value="mq发送queue消息" />
    </form>
    <br>
    <form action="mqTest/sendMsgToMyTopic" method="post">
        <input type="submit" value="mq发送topic消息" />
    </form>
    <br>
    <form action="mqTest/receiveMsgTest" method="post">
        <input type="submit" value="mq手动打开接收消息" />
    </form>
    
</body>
</html>
