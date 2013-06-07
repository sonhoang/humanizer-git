<jsp:directive.page contentType="text/html;charset=utf-8"
	language="java" isELIgnored="false" />
<%@ page import="java.io.*"%>
<%@page language="java" import="java.util.*"%>
<%@taglib uri="/WEB-INF/tlds/tag_library" prefix="t"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<title>Keyword listing</title>

</head>

<body>
	<div class="container">

		<!--<div class="row" id="topbar">
    
  </div> -->
		<div class="keyword">
			Details for task <b><%=request.getAttribute("task_name") %></b>:
		</div>
		<div class="list-key">

			<table>
				<tr>
					<th class="for_table">No</th>
					<th class="for_table">Title</th>
					<th class="for_table">URL</th>
					<th class="for_table">Snippet</th>
					<th class="for_table">Position</th>
					<%
			List raters= (List)request.getAttribute("raters");
			Iterator iRaters;
			for (iRaters=raters.iterator(); iRaters.hasNext();) {	
				String rater = (String) iRaters.next();
				%>
					<th class="for_table"><%=rater %></th>
					<%
			}

		%>

					<!--th class="for_table">Rating Details</th-->
				</tr>

				<%Iterator itr;%>
				<% List data= (List)request.getAttribute("data");
	  int index = 0;
	  String task = (String)request.getAttribute("task");
      for (itr=data.iterator(); itr.hasNext();) {
        List lst = (List) itr.next();        
        Iterator itr2;
        for (itr2=lst.iterator(); itr2.hasNext();) {
		  index += 1;
		  String _id = (String) itr2.next();
		  String _rev = (String) itr2.next();
		  String url = (String) itr2.next();
          String title = (String) itr2.next();          
		  String snippet = (String) itr2.next();
		  String position = (String) itr2.next();		 
		  List data2 = (List) itr2.next();
      %>
				<tr>
					<td class="for_table"><%=index %></td>
					<td class="for_table"><%=title %></td>
					<td class="for_table"><%=url %></td>
					<td class="for_table"><%=snippet %></td>
					<td class="for_table"><%=position %></td>

					<%
		 Iterator itr3;
		 for (itr3=data2.iterator(); itr3.hasNext();) {
			List lst3 = (List) itr3.next();        
			Iterator itr4;
			for (itr4=lst3.iterator(); itr4.hasNext();) {		 
			  
				String user = (String) itr4.next();
				String rating = (String) itr4.next();
				String note = (String) itr4.next();
				String time = (String) itr4.next();   
				if (!rating.equals("")){
			  %>
					<td class="for_table"><b>Rating:</b> <b><%=rating %></b> <br>
						<b>Time:</b> <i><%=time %></i> <br> <b>Note:</b> <i> <%=note %></i>
						<br></td>
					<%
				}else{
			  %>
					<td class="for_table">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<%				
				}
			}
	    }    		
		
		%>


				</tr>
				<%
          }
        %>
				<%}%>
			</table>


		</div>
		<t:pagination listname="items" />
		<div class="sb_foot clearfix">
			<ul class="sw_footL clearfix">
				<li><span>&copy; 2013 Company</span> |</li>
				<li><a href="#">Privacy and Cookies</a> |</li>
				<li><a href="#">Legal</a> |</li>
				<li><a target="_blank" id="sb_help" href="#">Help</a> |</li>
				<li><a h="ID=FD,65.1" id="sb_feedback" href="#">Feedback</a></li>
				<li><a href="/logout" id="sb_feedback" href="#">Logout</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!--div class="rating-bottom">
  <div class="bottomBody">
    <ul class='star-rating'>
      <li class='current-rating' style='width:76px;'> Currently 3.5/5 Stars.</li>
      <li><a href='#' title='1 star out of 5' class='one-star'>1</a></li>
      <li><a href='#' title='2 stars out of 5' class='two-stars'>2</a></li>
      <li><a href='#' title='3 stars out of 5' class='three-stars'>3</a></li>
      <li><a href='#' title='4 stars out of 5' class='four-stars'>4</a></li>
      <li><a href='#' title='5 stars out of 5' class='five-stars'>5</a></li>
    </ul>
    <input type="submit" class="btnSubmit" value=" "/>
  </div>
</div-->

	<script src="js/pagination.js" type="text/javascript"></script>
	<script type="text/javascript">
		$('.pagination').pagination({});
	</script>
</body>
</html>
