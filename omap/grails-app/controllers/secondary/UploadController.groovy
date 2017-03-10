package secondary

import customer.Customer
import customer.Location

import grails.transaction.Transactional
import groovy.sql.Sql


import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class UploadController {

    def geocoderService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static int added
    static int edited


    def index(Integer max) {
        added = 0
        edited = 0
        params.max = Math.min(max ?: 10, 100)
        respond Upload.list(params), model: [uploadCount: Upload.count()]
    }

    def show(Upload upload) {
        respond upload
    }

    def create() {
        respond new Upload(params)
    }

    @Transactional
    def save(Upload upload) {
        if (upload == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (upload.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond upload.errors, view: 'create'
            return
        }

        upload.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'upload.label', default: 'Upload'), upload.id])
                redirect upload
            }
            '*' { respond upload, [status: CREATED] }
        }
    }

    def edit(Upload upload) {
        respond upload
    }

    @Transactional
    def update(Upload upload) {
        if (upload == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (upload.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond upload.errors, view: 'edit'
            return
        }

        upload.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'upload.label', default: 'Upload'), upload.id])
                redirect upload
            }
            '*' { respond upload, [status: OK] }
        }
    }

    @Transactional
    def delete(Upload upload) {

        if (upload == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        upload.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'upload.label', default: 'Upload'), upload.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'upload.label', default: 'Upload'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }


    def upload = {

        added = 0
        edited = 0

        def f = request.getFile('filecsv')
        if (f.empty) {
            flash.message = 'file cannot be empty'
            render(view: 'index')
            return
        }

        f.transferTo(new File('p:\\Documents\\JAVA_GO_go\\GrailsTry\\grails-app\\tmp\\1.csv'))

        flash.message = "Uploading customers data"
        redirect(controller: "customer", action: "index")


        File splited = new File('p:\\Documents\\JAVA_GO_go\\GrailsTry\\grails-app\\tmp\\1.csv')
        def sql = Sql.newInstance("jdbc:mysql://localhost:3306/omap",
                "grails", "server", "com.mysql.jdbc.Driver")
        def customer = sql.dataSet("Customer")

        String[] lines = splited.text.split('\n')
        List<String[]> rows = lines.collect { it.split(',') }
        rows.remove(0)

        println("All list: " + rows)

        rows.each {

            def currentCustomer = customer.Customer.findByEmail(it[1])

            if (currentCustomer) {

                println("UPDATING User: " + currentCustomer.name)
                currentCustomer.name = (it[0])
                currentCustomer.street = (it[2])
                currentCustomer.zip_code = (it[3])
                currentCustomer.save(flush: true, failOnError: true);

                findCoordinates(currentCustomer)
                edited++

            } else {

                println("CREATING User: " + it[0])
                Customer customerNew = new Customer()
                customerNew.name = it[0]
                customerNew.email = it[1]
                customerNew.street = it[2]
                customerNew.zip_code = it[3]
                customerNew.save(flush: true, failOnError: true);

                findCoordinates(customerNew)
                added++
            }
        }

        if (added != 0)
            flash.message = " ${added} new Users were creater"
        if (edited != 0)
            flash.message = " ${edited} Users were updated"

    }

    def findCoordinates(Customer customer) {
       if (customer != null){
           //def userLoca = Location.findByCustomer(customer.name)

            Location location = new Location()
            location.setStreet(customer.street)
            location.setCustomer(customer.name)
            println("-------------------------")
            println(location.street)
            println(location.id)
            println("-------------------------")
            location.save flush: true, failOnError: true
            Set<Location> locationSet = new HashSet<Location>()
            locationSet.add(location)
            customer.location = locationSet
            customer.save flush: true, failOnError: true
            geocoderService.fillInLatLng(location)
        }


    }
}