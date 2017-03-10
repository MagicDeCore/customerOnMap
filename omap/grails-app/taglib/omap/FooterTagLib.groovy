package omap

class FooterTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def thisYear = {
        out << new Date().format("yyyy")
    }

    def copyright = {attr, body ->
        out << "from " + attr.startYear + " till "
        out << thisYear() + " " + body() + " walk the Eath"
    }
}
