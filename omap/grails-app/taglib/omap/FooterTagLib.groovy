package omap

class FooterTagLib {
    static defaultEncodeAs = [taglib: 'html']

    def thisYear = {
        out << new Date().format("yyyy")
    }

    def copyright = { attr, body ->
        out << "from " + attr.startYear + " till "
        out << thisYear() + " " + body() + " walk the Eath"
    }
}
