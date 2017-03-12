package customer

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CustomerController {

    def locationService

    def added = 0
    def edited = 0

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        if (added != 0)
            flash.message = " ${added} new Users were creater"
        if (edited != 0)
            flash.message = " ${edited} Users were updated"
        if (added != 0 && edited != 0)
            flash.message = "  ${added} new Users were creater, ${edited} Users were updated"

        added = 0
        edited = 0

        if (params.name == null && params.email == null && params.street == null && params.zip == null) {
            params.max = Math.min(max ?: 10, 100)
            respond Customer.list(params), model: [customerCount: Customer.count()]

        } else {
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
            respond customerList, model: [customerInstanceCount: customerList.totalCount]
        }
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
            respond customer.errors, view: 'create'
            return
        }

        customer.save(flush: true)
        if (customer.location == null) {
            locationService.setUpLocationForCustomer(customer)
            customer.save(flush: true)
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
        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (customer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond customer.errors, view: 'edit'
            return
        }
        customer.save flush: true
        locationService.setUpLocationForCustomer(customer)
        customer.save flush: true


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect customer
            }
            '*' { respond customer, [status: OK] }
        }
    }

    @Transactional
    def delete(Customer customer) {

        if (customer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        customer.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'customer.label', default: 'Customer'), customer.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'customer.label', default: 'Customer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
    def upload = {

        added = 0
        edited = 0
        flash.message = "Uploading customers data"
        redirect(controller: "customer", action: "index")

        def f = request.getFile('filecsv')
        if (f.empty) {
            flash.message = 'file cannot be empty'
            render(view: 'index')
            return
        }

        def webRootDir = servletContext.getRealPath("/")
        def tmpDir = new File(webRootDir, "/tmp/")
        tmpDir.mkdirs()
        f.transferTo(new File(tmpDir, "lastUpload"))

        File splited = new File("${tmpDir}/lastUpload")
        String[] lines = splited.text.split('\n')
        List<String[]> rows = lines.collect { it.split(',') }
        rows.remove(0)
        rows.each {

            def currentCustomer = customer.Customer.findByEmail(it[1])
            if (currentCustomer) {

                currentCustomer.name = (it[0])
                currentCustomer.street = (it[2])
                currentCustomer.zip_code = (it[3])
                currentCustomer.save(flush: true, failOnError: true);
                locationService.setUpLocationForCustomer(currentCustomer)
                edited++

            } else {

                Customer customerNew = new Customer()
                customerNew.name = it[0]
                customerNew.email = it[1]
                customerNew.street = it[2]
                customerNew.zip_code = it[3]
                customerNew.save(flush: true, failOnError: true);
                locationService.setUpLocationForCustomer(customerNew)
                added++
            }
        }
    }

}
