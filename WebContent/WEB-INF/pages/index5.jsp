<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>convert your image files to pdf</h1>

<form method='post' action='imagetopdf' enctype='multipart/form-data'>
 
 <input type="file" name="file[]" id="file" multiple>
 <input type='submit' name='submit' value='Upload'>

</form>
</body>
</html>