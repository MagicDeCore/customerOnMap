package customer

class Location {

    String street

    Location() {  }

    double latitude
    double longitude
    String customer

    String toString() { "$street" }

    static constraints = {
        street blank: false

        latitude min: -90d, max: 90d , nullable: true
        longitude( nullable: true)
        customer (blank: true, nullable: true, display: false)
    }
}
