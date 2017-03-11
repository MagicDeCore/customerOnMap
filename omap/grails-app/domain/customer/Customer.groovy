package customer

class Customer {

    static mapping = {
        version false
    }

    String name
    String email
    String street
    String zip_code


    static hasMany = [location: Location]

    static constraints = {

        name nullable: true
        email (unique: true, nullable: true)
        street nullable: true
        zip_code nullable: true
        location (blank: true, nullable: true, display: false)

    }
}
