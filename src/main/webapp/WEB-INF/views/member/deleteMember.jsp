<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원 탈퇴</title>

<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: blue; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }
#topBanner {
       margin-top:10px;
       margin-bottom:10px;
       max-width: 500px;
       height: auto;
       display: block; margin: 0 auto;
}
   
.ConfirmDivision {
  width:900px;
  height:auto;
  padding: 20px, 20px;
  background-color:#FFFFFF;
  text-align:center;
  border:5px solid gray;
  border-radius: 30px;
}   

.after_30  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: red;
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}

.now_change  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: pink;
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}
</style>
</head>
<script>
	$(document).ready(function(){
		
		$("#ConfirmDivision").attr("action", "deleteMember").submit();
	})
	
</script>
<body>

	<center><br><br>
   	<div class="ConfirmDivision">
		<h1><p style="text-align: center;">사용자 탈퇴를 하시면 작성하셨던 <br>모든 게시물도 함께 삭제됩니다. <p>정말로 사용자 탈퇴 하시겠습니까?</p><br></h1>
		<input type="button" class="after_30" value="확인" onclick="location.href='deleteMemberProc'">
        <input type="button" class="now_change" value="취소" onclick="history.back();">
   	</div> 
   	
   	


    </center>
    	
    </div> 

</body>
</html>