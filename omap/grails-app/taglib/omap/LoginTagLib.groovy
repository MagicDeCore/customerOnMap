package omap

class LoginTagLib {
    static defaultEncodeAs = [taglib:'gsp']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]



        def springSecurityService

        def isLoged = { attrs, body ->
            def loggedInUser = springSecurityService.currentUser
            def owner = attrs?.owner
            println(owner?.id)
            println(loggedInUser)

            if(loggedInUser != null) {
                out << "Loged in as ${loggedInUser.username}"
                out << " /  ${link(action: "", controller: "logoff"){"Logout"}}"
            }else {
                out << """${link(action: "auth", controller: "login"){"Login"}}"""
            }
        }

}






//        def user = springSecurityService.getCurrentUser();
//        println(user)
////
//        if ((user) && (user?.getUsername() != 'anonymousUser')){
//            out << "Loged in as ${session.user.login}"
//            out << " /  ${link(action: "logout", controller: "login"){"Logout"}}"
//        }else {
//            out << """${link(action: "auth", controller: "login"){"Login"}}"""
//        }


