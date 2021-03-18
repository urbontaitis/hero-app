package dc.vilnius.vote.domain

import dc.vilnius.vote.dto.SubmitVote

import java.time.LocalDateTime

trait SampleVote {

    private static final Map SAMPLE_VOTE = [
            "id": UUID.fromString("1971966c-43b5-4857-bf04-6cee375d62d6"),
            "createDate"  : LocalDateTime.parse("2021-01-18T01:18:00"),
            "email": "awesome@email.com",
            "comment"     : "You rock! Keep going!"
    ]

    SubmitVote youAreAwesome = sampleSubmitVote(comment: "You are awesome!")
    SubmitVote keepGoing = sampleSubmitVote(comment: "Keep going!")

    Vote sampleVote(Map<String, Object> args = [:]) {
        args = SAMPLE_VOTE + args
        Vote entity = new Vote()
        entity.setId(args.id as UUID)
        entity.setCreateDate(args.createDate as LocalDateTime)
        entity.setEmail(args.email as String)
        entity.setComment(args.comment as String)
        entity
    }

    SubmitVote sampleSubmitVote(Map<String, Object> args = [:]) {
        args = SAMPLE_VOTE + args
        new SubmitVote(args.email as String, args.comment as String)
    }
}
