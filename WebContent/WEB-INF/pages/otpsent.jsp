<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>we have sent you otp please verify</h1>
<form action="otpvalidate" method="post">
<input type="hidden" name="mainotp" value=${content}> 
${content}
<input type="text" name="otp">
<input type="submit">
</form>

</body>
</html>