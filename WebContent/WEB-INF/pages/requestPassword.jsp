<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>please enter your pdf password</h1>
<form action="extractData" method ="post">
<input type="hidden" value=${filepath} name = "filePath">
<input type="password" name=password>
<input type="submit"> 
 

</form>
${error}
</body>
</html>