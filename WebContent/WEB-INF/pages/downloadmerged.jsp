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
${file}
<h1>download merged pdf</h1>
<form action="mergedownload" method="post">
	<input type="hidden" name="filepath" value=${file}>
	<button type="submit" class="btn btn-danger">DOWNLOAD YOUR MERGED PDF</button>
</form>
</body>
</html>