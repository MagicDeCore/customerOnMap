package omap

class LoginTagLib {
    static defaultEncodeAs = [taglib:'gsp']

        def springSecurityService

        def isLoged = { attrs, body ->
            def loggedInUser = springSecurityService.currentUser
            if(loggedInUser != null) {
                out << "Loged in as ${loggedInUser.username}"
                out << " /  ${link(action: "", controller: "logoff"){"Logout"}}"
            }else {
                out << """${link(action: "auth", controller: "login"){"Login"}}"""
            }
        }

}

