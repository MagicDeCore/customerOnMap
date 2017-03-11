package customer

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CustomerController {

    def geocoderService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        if (params.name == null && params.email == null && params.street == null && params.zip == null) {
            params.max = Math.min(max ?: 10, 100)
//            Customer.list().each { cus ->
//            println(cus.name+","+cus.email+","+cus.street+","+cus.zip_code +'\n')
//            }
            respond Customer.list(params), model: [customerCount: Customer.count()]

        }else{

            params.max = Math.min(params.max ? params.int('max') : 10, 100)
            def customerList = Customer.createCriteria().list(params) {
                if (params.name) {
                    ilike("name", "%${params.name}%")
                }
                if (params.email) {
                    ilike("email", "%${params.email}%")
                }
                if (params.street) {
                    ilike("street", "%${params.street}%")
                }
                if (params.zip) {
                    ilike("zip_code", "%${params.zip}%")
                }
            }

            println(customerList)
            respond customerList, model: [customerInstanceCount: customerList.totalCount]
        }
    }

    def index123() {
        params.max = Math.min(params.max ? params.int('max') : 5, 100)

        def result = Customer.createCriteria().list (params) {
            if ( params.query ) {
                ilike("name", "%${params.query}%")
            }
        }
        println("seeeeeaaacrhing" + "%${params.query}%"  )
        respond ([customerInstanceList: result, customerInstanceTotal: result.totalCount])
    }

    def show(Customer customer) {
        respond customer
    }

    def create() {
        respond new Customer(params)
    }

    @Transactional
    def save(Customer customer) {
        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customer.errors, view:'create'
            return
        }

        if (customer.location == null){
            findCoordinates(customer)
        }

        customer.save flush:true

        if (customer.location == null){
            findCoordinates(customer)
            customer.save flush:true
        }


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect customer
            }
            '*' { respond customer, [status: CREATED] }
        }
    }

    def edit(Customer customer) {
        respond customer
    }

    @Transactional
    def update(Customer customer) {
        if (Customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customer.errors, view:'edit'
            return
        }

        findCoordinates(customer)
        customer.save flush:true



        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect customer
            }
            '*'{ respond customer, [status: OK] }
        }
    }

    @Transactional
    def delete(Customer customer) {

        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        def name = customer.name
        customer.delete flush:true
        if (Location.findByCustomer(name) != null) {
            Location.findByCustomer(name).delete(flush: true)
        }
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def findCoordinates(Customer customer){
        Location location = new Location()
        location.setStreet(customer.street)
        location.setCustomer(customer.name)
        println("-------------------------")
        println(location.street)
        println(location.id)
        println("-------------------------")
        location.save flush:true, failOnError: true
        Set<Location> locationSet = new HashSet<Location>()
        locationSet.add(location)
        customer.location = locationSet
        customer.save flush:true, failOnError: true
        geocoderService.fillInLatLng(location)
    }


}
