<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>TwitMap_v2</title>
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map { height: 90%;  }
      #panel {width: 100%%;}
 
    </style>
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="./static/markerclusterer.js" type="text/javascript"></script>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?libraries=sensor=false">
    </script> 

    <script>
    var map;
	var markers = [];
	var current_keyword;
  
    function initialize() {
    var Options = {
        zoom: 4,
        center: new google.maps.LatLng(40.7127, -74.0059)
      };
    
    map = new google.maps.Map(document.getElementById('map'), Options);
    }

    google.maps.event.addDomListener(window, 'load', initialize);
  
    function TWonClick() {
      if($("#keyword").val() == 0){
        return false;
      }
      current_keyword = $("#keyword").val();
      var lat;
      var lng;
	  var item = document.getElementById("loading");
	  var markerCluster = new MarkerClusterer(map, markers); 
	  item.style.display="block"
      $.getJSON("Twts", {keyword:$('#keyword').val()}, function(data) {
    	  if(data.success && data.loc.length > 0){
<<<<<<< HEAD
    		  alert("got data");
=======
>>>>>>> 38953e265f0ccd05acb52cf1a4699a30865c6ca4
    		  removeMarkers();
		  markerCluster.clearMarkers();
          var bounds = new google.maps.LatLngBounds ();
			  for(var i = 0; i < data.loc.length; i++){
				var coords = data.loc[i];
				lat = coords.lat;
				lng = coords.lng;
				if (lng == undefined) {
					lng = 0.0;
				}
				if (lat == undefined) {
					lat = 0.0;
				}
				if (lat == 0.0 && lng == 0.0) {
					continue;
				}
				var location = new google.maps.LatLng(lat, lng);
				var marker = new google.maps.Marker({
				  position : location,
				  map : map
				});
				markers.push(marker);
				bounds.extend(location);
			}
			markerCluster = new MarkerClusterer(map, markers, {
	              minZoom: 2,
	              maxZoom: 6,
            });
        }

      });
	  item.style.display="none";
      return false; // prevents the page from refreshing before JSON is read from server response
  }
  
    function removeMarkers() {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(null);
		}
		markers = [];
  }

  
  function deleteMarkers() {
      clearMarkers();
      markers = [];
    }
   function clearMarkers() {
       setAllMap(null);
    }
   function setAllMap(map) {
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
      }
    }
   
   function clearClusters(e) {
       e.preventDefault();
       e.stopPropagation();
       markerCluster.clearMarkers();
     }

  </script>
  </head>
 
  <body>
  <div id="messages"></div>
  <script type="text/javascript">
  if ("WebSocket" in window){
<<<<<<< HEAD
	var wsUri = "ws://localhost:8080/TwittMap/receiveSNS";
=======
	var wsUri = "ws://assignment-2.elasticbeanstalk.com:8080/receiveSNS";
>>>>>>> 38953e265f0ccd05acb52cf1a4699a30865c6ca4
    var webSocket = new WebSocket(wsUri);
    webSocket.onerror = function(event) {
      onError(event);
    };
    
    webSocket.onclose = function(event) {
    	onClose(event);
    }

    webSocket.onopen = function(event) {
      onOpen(event);
    };

    webSocket.onmessage = function(event) {
      onMessage(event);
    };
    
  } else {
	  alert("Browser not supported for real-time updates");
  }
    
    function onClose(event) {
    	var code = event.code;
    	var reason = event.reason;
    	var wasClean = event.wasClean;
    	alert(code + "; " + reason + "; " + wasClean);
    }

    function onMessage(event) {
      //document.getElementById('messages').innerHTML 
      //  += '<br />' + event.data;
        
      alert(event.data);
      
        obj = JSON && JSON.parse(event) || $.parseJSON(json);
        
        lat = obj.lat;
		lng = obj.lng;
		if (lng == undefined) {
			lng = 0.0;
		}
		if (lat == undefined) {
			lat = 0.0;
		}
		if (lat == 0.0 && lng == 0.0) {
			return;
		}
		if (current_keyword != obj.keyword) {
			return;
		}
		var location = new google.maps.LatLng(lat, lng);
		var marker = new google.maps.Marker({
		  position : location,
		  map : map
		});
		markers.push(marker);
		bounds.extend(location); 
    }

    function onOpen(event) {
      alert("connected...");
      webSocket.send('opening socket');
    }

    function onError(event) {
      alert("error: " + event.data);
    }
  </script>
     <div id="panel">
      <p>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        Choose  a keyword:
        <form name="Form1" action='Twts' method='get' target="map">
        <select class="selectpicker" id="keyword">
          <option value="isis">Isis</option>
          <option value="NFL">NFL</option>
          <option value="ebola">Ebola</option>
          <option value="Interstellar">Interstellar</option>
          <option value="Thanksgiving">Thanksgiving</option>
          <option value="halloween">Halloween</option>
          <option value="winter">Winter</option>
          <option value="NYC">NYC</option>
          <option value="obama">Obama</option>
        </select>
        <button type="submit" class="btn" id="go" onclick="return TWonClick();">Plot!</button>
      </form>
      &nbsp;<div id="loading" style="display:none; ">Loading...</div>
      </p>
    </div> 
 <div id="map">
  <input type="hidden" name="markers" value="someValue">
 </div>
 
 </body>

</html>