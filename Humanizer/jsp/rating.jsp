<head>
	<meta http-equiv="content-type" content="text/html" />
	<meta name="author" content="victo" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
	<title>Rating</title>

	<script type="text/javascript">		
	function setStar(value)
	{
		var rating = document.getElementById("rating");
		rating.value = value;
		//alert(vale);
		var my_rating = document.getElementById("navigation").getElementsByTagName("li")[0];	
		//alert (my_rating.innerHTML);
		my_rating.style.width = value * 25 + "px";
			
	}		  

</script>		  
</head>

<body>
<div class="container">
	<%
		String url = (String)request.getAttribute("url");
		String task = (String)request.getAttribute("task");
		String keyword = (String)request.getAttribute("keyword");		
		String note = (String)request.getAttribute("note");		
		String old_rating = (String)request.getAttribute("relevance");
		int px = Integer.parseInt((String)request.getAttribute("relevance"));
		if (px == 0){
			px = 3*25;
		}else{
			px = px*25;
		}
	%>
    <iframe marginwidth="0" marginheight="0" frameborder="0" width="1000px" height="1500px"
        bottommargin="0" rightmargin="0" leftmargin="0" topmargin="0" nosize scrolling="no" 
	
    src="<%=url %>"></iframe>
	
</div>
<div class="rating-bottom">
	<form action="/rate" method="post">
    <div class="bottomBody">
		<div > <a style="font-size: 9pt;color: #ffffaa;float: right;text-decoration:none;" href="list_detail?task=<%=task%>&keyword=<%=keyword%>"> <i>Back to list</i> </a> </div>
		<input type="hidden" id="url" name="url" value="<%=url %>" />
		<input type="hidden" id="task" name="task" value="<%=task %>" />
		<input type="hidden" id="query" name="query" value="<%=keyword %>" />
        <ul  id="navigation" class='star-rating'>
        	<li id="my-rating" class='current-rating' style='width:<%=px %>px;'>  Currently 3/5 Stars.</li>
        	<li onClick="setStar(1);"><a href='#' title='1 star out of 5' class='one-star'>1</a></li>
        	<li onClick="setStar(2);"><a href='#' title='2 stars out of 5' class='two-stars'>2</a></li>
        	<li onClick="setStar(3);"><a href='#' title='3 stars out of 5' class='three-stars'>3</a></li>
        	<li onClick="setStar(4);"><a href='#' title='4 stars out of 5' class='four-stars'>4</a></li>
        	<li onClick="setStar(5);"><a href='#' title='5 stars out of 5' class='five-stars'>5</a></li>
        </ul>
		<%
		if (!old_rating.equals("0")){
		%>
		<p style="height: 25px;padding-top: 12px;bottom: 10px;font-size: 14pt;color: #ffffaa;"> &nbsp;&nbsp;&nbsp;Already rated (<%=old_rating %>): <i><%=note %></i></p>
		<%
		}else{
		%>
		
		<input type="hidden" id="rating" name="rating" value="3" />
		<input type="text" id="note" name="note" value="" style="float: left;margin-top: 11px;margin-left: 20px;"/>
        <input type="submit"  class="btnSubmit" value=" "/>
		<%
		}
		%>
		
	<!--input type="button"  class="btnSubmit" value=">>"/-->
		<!--div class="clear"></div-->
    </div>
	</form>
</div>


</body>
</html>