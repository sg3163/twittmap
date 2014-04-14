<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>

<style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
      #panel {
        position: absolute;
        top: 5px;
        left: 50%;
        margin-left: -180px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
    </style>
    
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=visualization"></script>
    <script>
    
//Adding 500 Data Points
var map, pointarray, heatmap;

//var newTweetDataArray = [];

var taxiData = [
 /* new google.maps.LatLng(40.79656659, -82.5696176),
  new google.maps.LatLng(14.57626802, 121.03924356),
  new google.maps.LatLng(51.4886764, -0.2972888),
  new google.maps.LatLng(29.03876478, -81.32242964),
  new google.maps.LatLng(33.71474294, -85.83324721)*/
];



function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function changeGradient() {
  var gradient = [
    'rgba(0, 255, 255, 0)',
    'rgba(0, 255, 255, 1)',
    'rgba(0, 191, 255, 1)',
    'rgba(0, 127, 255, 1)',
    'rgba(0, 63, 255, 1)',
    'rgba(0, 0, 255, 1)',
    'rgba(0, 0, 223, 1)',
    'rgba(0, 0, 191, 1)',
    'rgba(0, 0, 159, 1)',
    'rgba(0, 0, 127, 1)',
    'rgba(63, 0, 91, 1)',
    'rgba(127, 0, 63, 1)',
    'rgba(191, 0, 31, 1)',
    'rgba(255, 0, 0, 1)'
  ]
  heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
}

function changeRadius() {
  heatmap.set('radius', heatmap.get('radius') ? null : 20);
}

function changeOpacity() {
  heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
}


function testTheValues() {
	alert("woah");
}

function getTweetData(keyword) {
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  taxiData = [];
		  var obj = eval ("(" + xmlhttp.responseText + ")");
		  
		  for (var i=0;i<obj.length;i++)
		  {
			  var locArr = obj[i].properties.geolocation.split(",");
			  taxiData.push(new google.maps.LatLng(locArr[0], locArr[1]));
		  }
		  
		  var mapOptions = {
				    zoom: 3,
				    center: new google.maps.LatLng(37.774546, -122.433523),
				    mapTypeId: google.maps.MapTypeId.ROADMAP
				  };
			
			map = new google.maps.Map(document.getElementById('map-canvas'),
				      mapOptions);
				      
				      var pointArray = new google.maps.MVCArray(taxiData);

				  heatmap = new google.maps.visualization.HeatmapLayer({
				    data: pointArray,
				    opacity:1
				  });

				  heatmap.setMap(map);
	    }
	  }
	xmlhttp.open("GET","/getdata/" + keyword ,true);
	xmlhttp.send();
	
}

</script>
<%
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
%>
</head>

<body>
 
<div height="100">
<table>
	<tr>
		<td><h3>Select Key word here: </h3></td>
		<td>
		<select onchange="getTweetData(this.value)">
		<%
		    Query query = new Query("keywords");
			query.addSort("name");
		    List<Entity> keys = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		    for(Entity e: keys) {
		%>
			  <option value="<%=e.getProperty("name") %>"><%=e.getProperty("name") %></option>
		<% } %>
			</select>
		</td>
	</tr>
</table>


</div>
<div id="panel">
      <button onclick="toggleHeatmap()">Toggle Heatmap</button>
      <button onclick="changeGradient()">Change gradient</button>
      <button onclick="changeRadius()">Change radius</button>
      <button onclick="changeOpacity()">Change opacity</button>
    </div>
    <div id="map-canvas"></div>
    
<%
    Query query1 = new Query("cold");
    List<Entity> tweets = datastore.prepare(query1).asList(FetchOptions.Builder.withDefaults());
    if (tweets.isEmpty()) {
%>
<p>no tweet</p>
<%
}
else {
	pageContext.setAttribute("size", tweets.size());
  	for (Entity tweet : tweets) {
%>
<script type="text/javascript">
	taxiData.push(new google.maps.LatLng(<%=tweet.getProperty("geolocation") %>))
</script>
<%
}
}
%>
<script type="text/javascript">
  
  function initialize() {
  var mapOptions = {
    zoom: 3,
    center: new google.maps.LatLng(37.774546, -122.433523),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
      
      var pointArray = new google.maps.MVCArray(taxiData);

  heatmap = new google.maps.visualization.HeatmapLayer({
    data: pointArray
  });

  heatmap.setMap(map);

}
  
  google.maps.event.addDomListener(window, 'load', initialize);
  
  </script>

</body>
</html>