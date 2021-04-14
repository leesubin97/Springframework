<%@page import="bit.com.a.dto.MyClass"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>


<!-- 1 컨트롤러에서 뷰로 짐풀기-->
<%
	MyClass cls = (MyClass)request.getAttribute("mycls");

%>

번호:<%=cls.getNumber() %>
이름:<%=cls.getName() %>

<br>
number:${mycls.number }<br>
name:${mycls.name }<br>
<br>

<!--2 뷰에서 컨트롤러로 이동할 경우  -->
<form action="move.do" method="post">
번호:<input type="text" name = "number" size="20">
이름:<input type="text" name = "name" size="20">
<input type="submit" value="이동">


</form>
<br><br>
<!--3 ajax id 전송 -->

<h3>3. ajax</h3>


아이디:<input type="text" id="checkid">
<br><br>
<button type="button" onclick="idcheck()">아이디체크</button>

<script type="text/javascript">

function idcheck() {
	$.ajax({
		
		url:"idcheck.do",
		type:"get",
		//data:"id="+$("#checkid").val(),
		
		//JSON 형식으로 데이터를 보낸다
		data:{id:$("#checkid").val()},
		
		success:function(data){
			//alert("success");
			alert(data);
			
		},
		error:function(){
			alert("error");
		}
	});
}




</script>



<br><br>
<!--4 
 json - > object(controller)
 map(controller) -> json(web)

-->
<h3>4. json</h3>

이름:<input type="text" id="name" value="송중기"><br>
전화:<input type="text" id="phone" value="010-456"><br>
이메일:<input type="text" id="email" value="vivi@na"><br>
생년월일:<input type="text" id="birth" value="1997/11/14"><br>

<button type="button" id="account">회원가입</button>


<script type="text/javascript">

$("#account").click(function(){
	
	alert("click");
	//JSON형식으로 데이터를 보낸다
	var human={
			name:$("#name").val(),
			phone:$("#phone").val(),
			email:$("#email").val(),
			birth:$("#birth").val()
			
	};
	
	
		$.ajax({
				
				url:"account.do",
				type:"post",
				dataType:"json",
				data:human,
				async:true,//비동기 처리이다
				
				success:function(data){
					//alert("success");
					alert(data.message);
					alert(data.name);
					alert(data);//그냥찍으면 오브젝트로 넘어온다
					
					alert(JSON.stringify(data)); //json-> String // string -> json : JSON.parse()
					
					
				},
				error:function(){
					alert("error");
				}
			});
	
	
	
});





</script>

<h3>5. data read(list)</h3>
<div id="datas">
</div>

<button type="button" onclick="read()">리스트출력</button>
<script type="text/javascript">
function read(){
	
	alert("read");
	$.ajax({
			
			url:"read.do",
			type:"post",
			async:true,//비동기 처리이다
			
			success:function(data){
				alert("success");
				
				
				var str = '';
				$.each(data, function(i, item){
					str += item.number;
					str += item.name + "<br>";
					
					
				});
				
				$("#datas").html(str);
				
				
			},
			error:function(){
				alert("error");
			}
		});
	
}

</script>

<h3>Hello.jsp</h3>

</body>
</html>