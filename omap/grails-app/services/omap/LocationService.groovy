package omap

import customer.Customer
import customer.Location
import grails.transaction.Transactional

@Transactional
class LocationService {


    def geocoderService

    void setUpLocationForCustomer(Customer customer) {
        if (customer.street != null) {

            def oldLocation = Location.findByCustomer(customer.name)
            Location location = new Location()
            location.setStreet(customer.street)
            location.setCustomer(customer.name)
            location.save flush: true, failOnError: true
            Set<Location> locationSet = new HashSet<Location>()
            locationSet.add(location)
            customer.location = locationSet
            customer.save flush: true, failOnError: true

            if (oldLocation != null) {
                oldLocation.delete(flush: true)
            }
            geocoderService.fillInLatLng(location)
        }
    }
}

