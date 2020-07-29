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
<h1>file uploaded on server</h1>
<form action="extractpdf" method ="post" >

<input type="hidden" name="filepath" value=${filepath}>
<button type="submit" class="btn btn-danger">extract your PDF</button>
${id}
${email}
</form>

</body>
</html>