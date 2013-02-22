<%@ page import="java.io.*" %> 
<%@page language="java" import="java.util.*" %>

<head>
  <meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
  <link href="css/style.css" rel="stylesheet" type="text/css" />
  <title>Keyword details</title>
</head>

<body>
<div class="container">
  
  <!--<div class="row" id="topbar">
    
  </div> -->
  <div class="keyword">Keyword <b><%=request.getAttribute("keyword") %> </b></div>
  <div class="list-detail clearfix">
    <ol class="search">
    <%Iterator itr;%>
      <% List data= (List)request.getAttribute("data");
      int index = 0;
      String keyword = (String) request.getAttribute("keyword");      
      String task = (String) request.getAttribute("task");            
      for (itr=data.iterator(); itr.hasNext(); )
      {
        String title = "";
        String desc = "";        
        if (index == 0) {
          title = keyword + ", 1st rank";
          desc = "Top ranking from search engine";
        } else if (index == 1) {
          title = keyword + ", 2nd rank";
          desc = "Top 2 ranking from search engine";
        } else if (index == 2) {
          title = keyword + ", 3rd rank";
          desc = "Top 3 ranking from search engine";
        } else{
          title = keyword + ", high rank";
          desc = "High ranking from search engine";
        }
        
        String link = (String) itr.next();
        link = link.substring(1);
        link = link.substring(0, link.length() - 1).trim();
        String link_show = link;
        link = "/rating?url=" + link + "&keyword=" + keyword + "&task=" + task;
    %>
      <li class="item">
        <div class="contentItem">
          <h3 class="titleItem"><a href="<%=link %>"><%=title %></a></h3>
          <div class="des">
            <div class="linkItem">
              <a href="<%=link %>"><%=link_show %></a>
            </div>
            <span class="st"><%=desc %></span>
          </div>
        </div>
      </li>
    <%
      index += 1;
      }
    %>
    </ol>
  </div>
  <div class="sb_foot clearfix">
    <ul class="sw_footL clearfix">
      <li><span>&copy; 2013 Company</span> | </li>
      <li><a href="#">Privacy and Cookies</a> | </li>
      <li><a href="#">Legal</a> | </li>
      <li><a target="_blank" id="sb_help" href="#">Help</a> | </li>
      <li><a href="ID=FD,65.1" id="sb_feedback" href="#">Feedback</a></li>
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


</body>
</html>
