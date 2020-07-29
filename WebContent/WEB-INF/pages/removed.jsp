<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>enter the page no you want to remove</h1>
	${file}
<form action="downloadremoved" method="post">
	<input type="hidden" name="filepath" value="${file}" >
	<input type="text" name=ithpage>
	<input type="submit">
</form>
</body>
</html>