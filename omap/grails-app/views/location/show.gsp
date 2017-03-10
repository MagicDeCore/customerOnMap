<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <script>
        function initMap() {
            var marker = {lat: ${this.location.latitude}, lng: ${this.location.longitude} };
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: marker

            });
            var marker = new google.maps.Marker({
                position: marker,
                map: map
            });

            var contentMark;

            var infowindow = new google.maps.InfoWindow({
                content: "${location.customer}"
            });

            marker.addListener('click', function() {
                infowindow.open(map, marker);
            });
        }

    </script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBmMimnH9xgUGXmEsjzHq3Bcm67-qYcs3I&callback=initMap">
    </script>

    %{--<a href="#show-location" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>--}%
    </div>

    <div id="show-location" class="content scaffold-show" role="main">
        <h1><g:message code="Customer: ${location.customer} address: ${location.street}" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

    </div>
    <div id="map" class="content scaffold-show" style="position:absolute;left:13px;top:220px; width: 98%;height: 400px"></div>
    <table style="margin: auto; border-top: none!important; margin-bottom: 420px"></table>


    </body>
</html>
