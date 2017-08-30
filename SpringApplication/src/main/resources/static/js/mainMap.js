var locations = [];
$.getJSON('http://localhost:8080/locations/', function(json) {
	locations = json;
	initMap();
});
var autocomplete;
var map;
var image = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
var markers;
var currentMarker;
var markerCluster;
var openMarker;

// Initializes the map
function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center : {
			lat : 37.1,
			lng : -95.7
		},
		zoom : 4,
		mapTypeControl : false,
		panControl : false,
		zoomControl : false,
		streetViewControl : false,
		styles : [ { "elementType" : "geometry", "stylers" : [ { "color" : "#f5f5f5" } ] }, 
			{ "elementType" : "geometry.fill", "stylers" : [ { "color" : "#FF8400" } ] },
			{ "elementType" : "labels", "stylers" : [ { "visibility" : "simplified" } ] }, 
			{ "elementType" : "labels.icon", "stylers" : [ { "visibility" : "off" } ] }, 
			{ "elementType" : "labels.text", "stylers" : [ {	"color" : "#ffffff" } ] }, 
			{ "elementType" : "labels.text.fill", "stylers" : [ { "color" : "#ffffff"	}, 
				{ "lightness" : 100 }, {	"weight" : 1.5 } ] }, 
			{ "elementType" : "labels.text.stroke", "stylers" : [ { "color" : "#f5f5f5" }, 
				{ "lightness" : 100 } ]},
			{ "featureType" : "administrative.land_parcel", "elementType" : "labels.text.fill",
				"stylers" : [ {	"color" : "#bdbdbd" } ] },
			{ "featureType" : "poi", "elementType" : "geometry", "stylers" : 
				[ { "color" : "#FF8400" } ] }, 
			{ "featureType" : "poi", "elementType" : "labels.text.fill", "stylers" : [ {
				"color" : "#FF8400" } ] }, {	"featureType" : "poi.park", "elementType" : "geometry",
			"stylers" : [ { "color" : "#FF8400" } ] }, 
			{ "featureType" : "road", "elementType" : "geometry", "stylers" : [ {	"color" : "#FF8400" } ] }, 
			{ "featureType" : "road.arterial", "elementType" : "labels.text.fill",
			"stylers" : [ { "color" : "#757575" } ] }, 
			{ "featureType" : "road.highway", "elementType" : "geometry",	"stylers" : [ {
				"color" : "#FF8400" } ] }, 
			{ "featureType" : "road.highway", "elementType" : "labels.text.fill",
				"stylers" : [ { "color" : "#616161" } ] }, 
			{ "featureType" : "road.local", "elementType" : "labels.text.fill",
				"stylers" : [ { "color" : "#9e9e9e" } ] }, 
			{ "featureType" : "transit.line", "elementType" : "geometry",
				"stylers" : [ { "color" : "#e5e5e5" } ] }, 
			{ "featureType" : "transit.station", "elementType" : "geometry",
				"stylers" : [ { "color" : "#FF8400" } ] }, 
			{ "featureType" : "water", "elementType" : "geometry", "stylers" : [ {
				"color" : "#c9c9c9" } ] }, 
			{ "featureType" : "water", "elementType" : "labels.text.fill",
				"stylers" : [ { "color" : "#9e9e9e" } ]} ]
	});

	// Autocompletes cities in the search bar
	autocomplete = new google.maps.places.Autocomplete((document
			.getElementById('autocomplete')), {
		types : [ '(cities)' ]
	});
	places = new google.maps.places.PlacesService(map);
	autocomplete.addListener('place_changed', onPlaceChanged);
	
	// Places the markers on the map and sets listeners for clicks
	markers = locations.map(function(l, i) {
				if(l.employees == null)
					l.employees = 0;
				if(l.companies == null)
					l.companies = 0;
				var marker = new google.maps.Marker(
						{
							infowindow : new google.maps.InfoWindow({
								content : l.city + '<br>Employees: '
										+ l.employees + '<br>Companies: '
										+ l.companies
							}),
							clickwindow : new google.maps.InfoWindow(
									{
										content : "<h5>"
												+ l.city
												+ "</h5><button type=\"button\" style=\"width:200px\" class=\"btn btn-primary\""
												+ "onClick=\"viewLocation('"+l.city+"')\">View Location</button>"
												+ "</br>"
												+ "<button type=\"button\" style=\"width:200px\" class=\"btn btn-primary\""
												+ "onClick=\"setLocation('"+l.city+"')\""
												+ ">Set As Your Location</button>"
									}),
							position : new google.maps.LatLng(l.lattitude,
									l.longitude),
							icon : image
						});
				marker.addListener('mouseover', function() {
					marker.infowindow.open(map, marker);
				});
				marker.addListener('mouseout', function() {
					marker.infowindow.setMap(null);
				});
				marker.addListener('click', function() {
					openMarker = marker;
					marker.infowindow.setMap(null);
					marker.clickwindow.open(map, marker);
				});
				return marker;
			});

	// Allows clustering of the markers
	markerCluster = new MarkerClusterer(
			map,
			markers,
			{
				imagePath : 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'
			});

	// When the user selects a city, get the place details for the city and
	// zoom the map in on the city.
	function onPlaceChanged() {
		var place = autocomplete.getPlace();
		if (place.geometry) {
			map.panTo(place.geometry.location);
			if (currentMarker != undefined) {
				currentMarker.clickwindow.setMap(null);
				currentMarker.setMap(null);
			}
			if (!placeExists(place.formatted_address)) {
				currentMarker = new google.maps.Marker(
						{
							infowindow : new google.maps.InfoWindow(
									{
										content : place.formatted_address
												+ '<p>This location has not been established '
												+ 'yet. If you would like to add '
												+ place.formatted_address
												+ ' to our listing, you can do so by clicking the marker and pressing "Add Location".</p>',
										maxWidth : 300
									}),
							clickwindow : new google.maps.InfoWindow(
									{
										content : "<button id=\"newLocation\" type=\"button\" class=\"btn btn-primary\""
												+ "onClick=\"createLocation()\">Add Location</button>"
									}),
							id : null,
							map : map,
							position : place.geometry.location,
							city : place.formatted_address,
							icon : 'https://mt.googleapis.com/vt/icon/name=icons/onion/161-grn-pushpin.png'
						});
				currentMarker.addListener('mouseover', function() {
					currentMarker.infowindow.open(map, currentMarker);
				});
				currentMarker.addListener('mouseout', function() {
					currentMarker.infowindow.setMap(null);
				});
				currentMarker.addListener('click', function() {
					currentMarker.infowindow.setMap(null);
					currentMarker.clickwindow.open(map, currentMarker);
				});
			}
			map.setZoom(8);
		} else {
			document.getElementById('autocomplete').placeholder = 'Enter a city';
		}
	}
	// Returns true if the searched location is already established
	function placeExists(address) {
		for (l in locations) {
			if (locations[l].city === address) {
				return true;
			}
		}
		return false;
	}
}
var setLocationCity;
function setLocation(city, lat, lng){
	openMarker.clickwindow.setMap(null);
	openMarker.employees += 1;
	setLocationCity = city;
	angular.element(document.querySelector('#setLocationButton')).click();
}

var viewLocationCity;
function viewLocation(city, lat, lng){
	viewLocationCity = city;
	angular.element(document.querySelector('#viewLocationButton')).click();
}

// Creates a new location in the database
function createLocation() {
	var Location = {
		city : currentMarker.city,
		lattitude : currentMarker.position.lat().toFixed(8),
		longitude : currentMarker.position.lng().toFixed(8)
	};
	$.ajax({
		url : "http://localhost:8080/locations",
		type : "POST",
		data : Location,
		dataType : "json"
	});
	currentMarker.clickwindow.setMap(null);
	currentMarker.setMap(null);
	Location.employees = 0;
	Location.companies = 0;
	var marker = new google.maps.Marker({
		infowindow : new google.maps.InfoWindow({
			content : Location.city + '<br>Employees: ' + Location.employees
					+ '<br>Companies: ' + Location.companies + '<br>Latitude: '
					+ Location.lattitude + '<br>Longitude: '
					+ Location.longitude
		}),
		clickwindow : new google.maps.InfoWindow({
			content : "<button type=\"button\" class=\"btn btn-primary\""
					+ "onClick=\"viewLocation()\">View Location</button>"
					+ "<button type=\"button\" class=\"btn btn-primary\""
					+ "onClick=\"setLocation()\">Set As Your Location</button>"
		}),
		position : new google.maps.LatLng(Location.lattitude,
				Location.longitude),
		icon : image
	});
	marker.addListener('mouseover', function() {
		marker.infowindow.open(map, marker);
	});
	marker.addListener('mouseout', function() {
		marker.infowindow.setMap(null);
	});
	marker.addListener('click', function() {
		marker.infowindow.setMap(null);
		marker.clickwindow.open(map, marker);
	});
	markers.push(marker);
	locations.push(Location);
	markerCluster.addMarker(marker, false);
}