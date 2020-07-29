<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="bootstrap.css">
</head>
<body>
<h1>download your new removed pdf</h1>
${file}
<form action="downloadnewremoved" method="post">
<input type="hidden" name="filePath" value=${file} >
<input type="submit">
</form>
</body>
</html>