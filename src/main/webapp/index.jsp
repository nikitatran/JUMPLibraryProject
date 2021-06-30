<%@page import="java.sql.Connection"%>
<%@page import="com.cognixia.jump.connection.ConnectionManager"%>
<html>
<body>
<h2>Hello World!</h2>
<% Connection conn = ConnectionManager.getConnection(); 
	conn.close();
%>
</body>
</html>
