package dc.vilnius.vote

import dc.vilnius.vote.domain.SampleVote
import dc.vilnius.vote.domain.Vote
import dc.vilnius.vote.domain.VoteFacade
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Ignore
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class VoteControllerAcceptanceSpec extends Specification implements SampleVote {

    @Inject
    VoteFacade voteFacade

    @Inject
    @Client('/')
    RxHttpClient client

    def "should save a vote and return it"() {
        given:
        def vote = sampleSubmitVote()

        when:
        def result = client.toBlocking().retrieve(HttpRequest.POST('/votes', vote), Vote)

        then:
        with(result) {
            email == vote.email
            comment == vote.comment
            id
            createDate
        }
    }

    @Ignore
    def "Positive vote scenario"() {
        given: 'email awesome@email.com has a several votes: "you are awesome" and "keep going"'
        def firstVote = voteFacade.submit(youAreAwesome)
        def secondVote = voteFacade.submit(keepGoing)
        def expectedVotes = [firstVote, secondVote]

        when: 'I go to /votes'
        def result = client.toBlocking().retrieve(HttpRequest.GET('/votes'), List<Vote>)

        then: 'I see both votes'
        result.containsAll(expectedVotes)
    }
}
