<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="bootstrap.css">

<style type="text/css">
a.merge:hover {font-size:150%;}
a.split:hover {font-size:150%;}
a.extract:hover {font-size:150%;}
a.remove:hover {font-size:150%;}
a.imagetopdf:hover {font-size:150%;}

</style>

</head>
<body>
<h1>
<a class="merge" href="merge">merge your pdf</a>
<br>
<a class="split" href="split">split your pdf</a>
<br>
<a class="extract" href="extract">extract data of your pdf</a>
<br>
<a class="remove" href="remove">remove a page from pdf</a>
<br>
<a class="imagetopdf" href="imagetopdf">imagetopdf</a>
<br>
</h1>
<br><br><br><br><br><br><br>

<c:if test="${email==null}"><a class="login" href="loginpage">login</a></c:if>
<c:if test="${email==null}"><a class="signup" href="signup">signup</a></c:if>
${id}
<a class="profile" href="profile">${email}</a>

<c:if test="${email!=null}"><a class="logout" href="logoutpage">logout</a></c:if>

<br><br><br><br><br><br><br><br><br>














<div>
<h5>enter your feedback</h5>
<form action="feedback">
<label for="fname">First name:</label>
<input type="text" name="name" id="fname"><br>
<label for="femail">  your email:</label>
<input type="text" name="email" id="femail"><br>
<h4>comment</h4>
<textarea rows = "5" cols = "40" name = "feedback">  </textarea>
<br>
<input type="submit">
<p>Click on the submit button to submit the feedback.</p>
</form>
</div>




<div class="spinner-grow" role="status">
  <span class="sr-only">Loading...</span>
</div>
 <c:forEach items="${xyz}" var="obj">
<table class="table ">
  
  <tbody>
    <tr>
      <th scope="row">${obj.name}         says    "${obj.feedback}"</th>
    </tr> 
  </tbody>
</table>
</c:forEach>


<div class="spinner-grow" role="status">
  <span class="sr-only">Loading...</span>
</div>




</body>
</html>