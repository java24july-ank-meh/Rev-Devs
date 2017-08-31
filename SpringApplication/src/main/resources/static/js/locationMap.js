var locationLat = 38.8338816;
var locationLng = -104.8213634;
var locationId = 13;
var hotspots = [];
$.getJSON('http://localhost:8080/locations/'+locationId+'/hotspots', function(json) {
	hotspots = json;
});

// Modal Code
var modal = document.getElementsByClassName('locationModal')[0];
var span = document.getElementsByClassName("locationClose")[0];
// When the user clicks on <span> (x), close the modal

// Types checkboxes monitoring
var type = "";
var el;
var tops;

function updateTags(e) {
	clearMarkers();
	if (this.checked) {
		type = this.value;
	} else {

	}
	if (this.value === "hotspots")
		mapHotspots();
	else
		search();
	map.setCenter(new google.maps.LatLng(locationLat, locationLng));
}

var map, infoWindow;
var markers = [];
var hostnameRegexp = new RegExp('^https?://.+?/');
var service;

function initMap() {
	span.onclick = function() {
		modal.style.display = "none";
	}
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
	el = document.getElementById('locationTypeBoxes');
	tops = el.getElementsByTagName('input');
	for (var i = 0, len = tops.length; i < len; i++) {
		if (tops[i].type === 'radio') {
			tops[i].onclick = updateTags;
		}
	}
	map = new google.maps.Map(document.getElementById('locationMap'), {
		zoom : 13,
		center : {
			lat : locationLat,
			lng : locationLng
		},
		mapTypeControl : false,
		panControl : false,
		zoomControl : false,
		streetViewControl : false
	});

	infowindow = new google.maps.InfoWindow();
	service = new google.maps.places.PlacesService(map);
	search();
}
function search() {
	service.nearbySearch({
		location : {
			lat : locationLat,
			lng : locationLng
		},
		radius : 10000,
		type : type
	}, callback);
}

function callback(results, status) {
	clearResults();
	if (status === google.maps.places.PlacesServiceStatus.OK) {
		for (var i = 0; i < results.length; i++) {
			createMarker(results[i]);
			addToTable(results[i], i);
		}
	}
}

function createMarker(place) {
	var icon = {
		url : place.icon,
		size : new google.maps.Size(71, 71),
		origin : new google.maps.Point(0, 0),
		scaledSize : new google.maps.Size(25, 25)
	};
	var marker = new google.maps.Marker({
		map : map,
		icon : icon,
		position : place.geometry.location
	});
	markers.push(marker);
	google.maps.event.addListener(marker, 'mouseover', function() {
		infowindow.setContent(place.name);
		infowindow.open(map, this);
	});
	var request = {
		reference : place.reference
	};
	service.getDetails(request, function(details, status) {
		if (status === google.maps.places.PlacesServiceStatus.OK) {
			google.maps.event.addListener(marker,'click',function() {
				modal.style.display = "block";
				document.getElementById('locationModalContent').innerHTML = setInfowindow(details);
			});
		} else {
			google.maps.event.addListener(marker,'click',function() {
				modal.style.display = "block";
				document.getElementById('locationModalContent').innerHTML = setInfowindowDefault(place);
			});
		}
	});
}

function setInfowindow(details) {
	var html = '<div style="text-align:center"><h3>' + details.name
			+ '</h3></div>';
	if (details.photos != undefined)
		html += '<image id="locationModalContent" class="locationImage" src="'
				+ details.photos[0].getUrl({
					"maxWidth" : 500,
					"maxHeight" : 500
				}) + '"><div class="locationMiddle">';
	html += '<div class="locationModalText">';
	if (details.formatted_address != undefined)
		html += '<strong>Address:</strong> ' + details.formatted_address;
	if (details.formatted_phone_number != undefined)
		html += '<br><strong>Phone:</strong> ' + details.formatted_phone_number;
	if (details.website != undefined)
		html += '<br><strong>Website:</strong> <a target="_blank" href="'
				+ hostnameRegexp.exec(details.website) + '">' + details.website
				+ '<\a>';
	if (details.opening_hours != undefined) {
		html += '<br><strong>Hours:</strong><br>'
				+ details.opening_hours.weekday_text[6] + '<br>'
				+ details.opening_hours.weekday_text[0] + '<br>'
				+ details.opening_hours.weekday_text[1] + '<br>'
				+ details.opening_hours.weekday_text[2] + '<br>'
				+ details.opening_hours.weekday_text[3] + '<br>'
				+ details.opening_hours.weekday_text[4] + '<br>'
				+ details.opening_hours.weekday_text[5]
	}
	if (details.photos != undefined)
		html += '</div>';
	html += '</div>';
	return html;
}
function setInfowindowDefault(place) {
	var html = '<div style="text-align: center">';
	if (place.name != undefined)
		html += '<h3>' + place.name + '</h3><br>';
	if (place.vicinity != undefined)
		html += '<strong>Address:</strong> ' + place.vicinity;
	if (place.photos != undefined)
		html += '<br><br><image src="' + place.photos[0].getUrl({
			"maxWidth" : 500,
			"maxHeight" : 400
		}) + '"><br>';
	html += '</div>';
	return html;
}

function addToTable(result, i) {
	var results = document.getElementById('locationResults');
	var markerIcon = result.icon;
	if (markerIcon == undefined)
		markerIcon = "http://maps.google.com/mapfiles/ms/icons/red.png";
	var tr = document.createElement('tr');
	tr.style.backgroundColor = (i % 2 === 0 ? '#F0F0F0' : '#FFFFFF');
	tr.style.border = "inset";
	tr.onclick = function() {
		map.setCenter(markers[i].position);
		google.maps.event.trigger(markers[i], 'click');
		google.maps.event.trigger(markers[i], 'mouseover');
	};
	var iconTd = document.createElement('td');
	var nameTd = document.createElement('td');
	iconTd.style.paddingRight = "5px";
	var icon = document.createElement('img');
	icon.src = markerIcon;
	icon.setAttribute('class', 'placeIcon');
	icon.setAttribute('className', 'placeIcon');
	var name = document.createTextNode(result.name);
	if (result.name == undefined)
		name = document.createTextNode(result.location.city);
	iconTd.appendChild(icon);
	nameTd.appendChild(name);
	tr.appendChild(iconTd);
	tr.appendChild(nameTd);
	results.appendChild(tr);
}

function clearResults() {
	var results = document.getElementById('locationResults');
	while (results.childNodes[0]) {
		results.removeChild(results.childNodes[0]);
	}
}

function clearMarkers() {
	for (var i = 0; i < markers.length; i++) {
		if (markers[i]) {
			markers[i].setMap(null);
		}
	}
	markers = [];
}

function mapHotspots() {
	clearResults();
	for (var i = 0; i < hotspots.length; i++) {
		var marker = new google.maps.Marker({
			map : map,
			city : hotspots[i].location.city,
			position : new google.maps.LatLng(hotspots[i].lattitude,
					hotspots[i].longitude)
		});
		markers.push(marker);
		google.maps.event.addListener(marker, 'mouseover', function() {
			infowindow.setContent(this.city);
			infowindow.open(map, this);
		});
		addToTable(hotspots[i], i);
	}
}