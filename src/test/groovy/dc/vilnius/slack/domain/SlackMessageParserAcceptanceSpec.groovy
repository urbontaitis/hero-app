package dc.vilnius.slack.domain

import spock.lang.Specification
import spock.lang.Unroll

class SlackMessageParserAcceptanceSpec extends Specification {

    CommandParser testObject

    def "setup"() {
        testObject = new CommandParser()
    }

    @Unroll
    def "Parses '#givenMessage' message into '#usernames' and '#message'"() {
        when:
        def result = testObject.parse(givenMessage)

        then:
        result.usernames() == usernames
        result.message() == message

        where:
        givenMessage                              | usernames                          | message
        "@username good work!"                    | ["@username"]                      | "good work!"
        "@name.surname @username you are awesome" | ["@name.surname", "@username"]     | "you are awesome"
        "@name, @surname, @username you rock 🤘"  | ["@name", "@surname", "@username"] | "you rock 🤘"
        "@name.z.surname lol 🤣"                  | ["@name.z.surname"]                | "lol 🤣"

    }

}
