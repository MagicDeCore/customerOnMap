package omap

import customer.Location
import grails.transaction.Transactional

@Transactional
class GeocoderService {

    void fillInLatLng(Location loc) {
        String base = 'https://maps.googleapis.com/maps/api/geocode/xml?'
        String encoded = [loc.street].collect {
            URLEncoder.encode(it, 'UTF-8')
        }.join(',')
        String qs = "address=$encoded"
        def root = new XmlSlurper().parse("$base$qs")
        def result = root.status
        if (result.equals("OK")) {
            def location = root.result[0].geometry.location
            loc.latitude = location.lat.toDouble()
            loc.longitude = location.lng.toDouble()
        }
    }
}

