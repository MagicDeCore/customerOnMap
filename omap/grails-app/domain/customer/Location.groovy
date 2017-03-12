package customer

class Location {

    String street

    double latitude
    double longitude
    String customer

    String toString() { "$street" }

    static belongsTo = [customer: Customer]

    static constraints = {
        street blank: true
        latitude min: -90d, max: 90d, nullable: true
        longitude min: -90d, max: 90d, nullable: true
        customer blank: true, nullable: true, display: false
    }
}
