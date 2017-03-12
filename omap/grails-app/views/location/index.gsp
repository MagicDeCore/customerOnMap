<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}"/>
    <asset:javascript src="jquery-2.2.0.min.js"></asset:javascript>
    <g:javascript library="jquery"></g:javascript>
    <title><g:message code="All Customers" args="[entityName]"/></title>
</head>

<body>

<div id="list-location" class="content scaffold-list" role="main">
    <h1><g:message code="All founded customers from Database ${customer.Location.count}" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
</div>

<div id="map" class="content scaffold-show"
     style="position:absolute;left:13px;top:220px; width: 98%;height: 400px"></div>
<table style="margin: auto; border-top: none!important; margin-bottom: 420px"></table>

<script>

    function initMap() {
        var locas = []
        var titles = []
        var inc = 0

        <g:each in="${locationList}" var="list">
        locas.push({lat: ${list.latitude}, lng:${list.longitude}})
        titles.push(inc, "${list.customer}")
        inc = inc + 1
        </g:each>

        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 3,
            center: {lat: 54.024, lng: 30.887}
        });

        var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var markers = locas.map(function (location, i) {
            return new google.maps.Marker({
                position: location,
                label: labels[i % labels.length],
                title: titles[i % labels.length],
                infowindow: "asdfasdfasdf"
            });
        });

        var contentMark;
        var markerCluster = new MarkerClusterer(map, markers,
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
    }
</script>

<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBmMimnH9xgUGXmEsjzHq3Bcm67-qYcs3I&callback=initMap"></script>

</body>
</html>