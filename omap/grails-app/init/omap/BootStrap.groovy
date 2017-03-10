package omap

import auth.Role
import auth.User
import auth.UserRole

class BootStrap {

    def init = { servletContext ->

        def admin = new User(username: 'admin', password: "admin").save(flush: true)
        def user = new User(username: 'user', password: "user").save(flush: true)

        def adminRole = new Role(authority: "ROLE_ADMIN").save(flush: true)
        def userRole = new Role(authority: "ROLE_USER").save(flush: true)

        new UserRole(user: admin, role: adminRole).save(flush: true)
        new UserRole(user: user, role: userRole).save(flush: true)
    }

    def destroy = {
    }
}
