package com.moingay.cricketsuper.di

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.moingay.cricketsuper.data.model.response.Bowler
import com.moingay.cricketsuper.data.model.response.Category
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.CurrentPartnership
import com.moingay.cricketsuper.data.model.response.DatumAttributes
import com.moingay.cricketsuper.data.model.response.DidNotBat
import com.moingay.cricketsuper.data.model.response.Equations
import com.moingay.cricketsuper.data.model.response.ExtraRuns
import com.moingay.cricketsuper.data.model.response.Fielder
import com.moingay.cricketsuper.data.model.response.Inning
import com.moingay.cricketsuper.data.model.response.InningBatsman
import com.moingay.cricketsuper.data.model.response.LastWicket
import com.moingay.cricketsuper.data.model.response.LiveInning
import com.moingay.cricketsuper.data.model.response.ManOfTheMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.Player
import com.moingay.cricketsuper.data.model.response.PostMedia
import com.moingay.cricketsuper.data.model.response.Powerplay
import com.moingay.cricketsuper.data.model.response.PrimaryTeam
import com.moingay.cricketsuper.data.model.response.Review
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.data.model.response.Team
import com.moingay.cricketsuper.data.model.response.Toss
import com.moingay.cricketsuper.data.model.response.Venue
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveInningDeserializer : JsonDeserializer<LiveInning> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LiveInning? {
        if (json == null) return null
        val jsonObject = json.asJsonObject
        val gson = Gson()

        return LiveInning(
            iid = jsonObject.get("iid")?.asLongOrNull(),
            number = jsonObject.get("number")?.asLongOrNull(),
            name = jsonObject.get("name")?.asStringOrNull(),
            shortName = jsonObject.get("short_name")?.asStringOrNull(),
            status = jsonObject.get("status")?.asLongOrNull(),
            isSuperOver = jsonObject.get("issuperover")?.asStringOrNull(),
            result = jsonObject.get("result")?.asLongOrNull(),
            battingTeamId = jsonObject.get("batting_team_id")?.asLongOrNull(),
            fieldingTeamId = jsonObject.get("fielding_team_id")?.asLongOrNull(),
            scores = jsonObject.get("scores")?.asStringOrNull(),
            scoresFull = jsonObject.get("scores_full")?.asStringOrNull(),
            fielder = jsonObject.get("fielder").asObjectOrNull<List<Fielder>>(gson),
            powerPlay = jsonObject.get("powerplay").asObjectOrNull<Powerplay>(gson),
            review = jsonObject.get("review").asObjectOrNull<Review>(gson),
            lastWicket = jsonObject.get("last_wicket").asObjectOrNull<LastWicket>(gson),
            extraRuns = jsonObject.get("extra_runs").asObjectOrNull<ExtraRuns>(gson),
            equations = jsonObject.get("equations").asObjectOrNull<Equations>(gson),
            currentPartnership = jsonObject.get("current_partnership")
                .asObjectOrNull<CurrentPartnership>(gson),
            didNotBat = jsonObject.get("did_not_bat").asObjectOrNull<List<DidNotBat>>(gson),
            maxOver = jsonObject.get("max_over")?.asStringOrNull(),
            target = jsonObject.get("target")?.asStringOrNull(),
            recentScores = jsonObject.get("recent_scores")?.asStringOrNull(),
            lastFiveOvers = jsonObject.get("last_five_overs")?.asStringOrNull(),
            lastTenOvers = jsonObject.get("last_ten_overs")?.asString
        )
    }
}


private fun JsonElement?.asLongOrNull(): Long? {
    return try {
        this?.asLong
    } catch (e: Exception) {
        null
    }
}

private fun JsonElement?.asStringOrNull(): String? {
    return try {
        this?.asString
    } catch (e: Exception) {
        null
    }
}
private fun JsonElement?.asBooleanOrNull(): Boolean? {
    return try {
        this?.asBoolean
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> JsonElement?.asObjectOrNull(gson: Gson): T? {
    return try {
        if (this != null) {
            gson.fromJson(this, object : TypeToken<T>() {}.type)
        } else {
            null
        }
    } catch (e: JsonSyntaxException) {
        null
    }
}

class InningDeserializer : JsonDeserializer<Inning> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Inning? {
        if (json == null) return null

        val jsonObject = json.asJsonObject

        val gson = Gson()

        return Inning(
            iid = jsonObject.get("iid")?.asLongOrNull(),
            number = jsonObject.get("number")?.asLongOrNull(),
            name = jsonObject.get("name")?.asStringOrNull(),
            shortName = jsonObject.get("short_name")?.asStringOrNull(),
            status = jsonObject.get("status")?.asLongOrNull(),
            isSuperOver = jsonObject.get("issuperover")?.asStringOrNull(),
            result = jsonObject.get("result")?.asLongOrNull(),
            battingTeamId = jsonObject.get("batting_team_id")?.asLongOrNull(),
            fieldingTeamId = jsonObject.get("fielding_team_id")?.asLongOrNull(),
            scores = jsonObject.get("scores")?.asStringOrNull(),
            scoresFull = jsonObject.get("scores_full")?.asStringOrNull(),
            batsmen = jsonObject.get("batsmen").asObjectOrNull<List<InningBatsman>>(gson),
            bowlers = jsonObject.get("bowlers").asObjectOrNull<List<Bowler>>(gson),
            fielder = jsonObject.get("fielder").asObjectOrNull<List<Fielder>>(Gson()),
            powerPlay = jsonObject.get("powerplay").asObjectOrNull<Powerplay>(Gson()),
            review = jsonObject.get("review").asObjectOrNull<Review>(Gson()),
            fowS = jsonObject.get("fows").asObjectOrNull<List<LastWicket>>(Gson()),
            lastWicket = jsonObject.get("last_wicket").asObjectOrNull<LastWicket>(gson),
            extraRuns = jsonObject.get("extra_runs").asObjectOrNull<ExtraRuns>(gson),
            equations = jsonObject.get("equations").asObjectOrNull<Equations>(gson),
            currentPartnership = jsonObject.get("current_partnership")
                .asObjectOrNull<CurrentPartnership>(gson),
            didNotBat = jsonObject.get("did_not_bat").asObjectOrNull<List<DidNotBat>>(gson),
            maxOver = jsonObject.get("max_over")?.asStringOrNull(),
            target = jsonObject.get("target")?.asString
        )
    }
}

class PlayerDeserializer : JsonDeserializer<Player> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Player? {
        if (json == null) return null

        val jsonObject = json.asJsonObject
        val gson = Gson()

        return Player(
            pid = jsonObject.get("pid")?.asLongOrNull(),
            title = jsonObject.get("title")?.asStringOrNull(),
            shortName = jsonObject.get("short_name")?.asStringOrNull(),
            firstName = jsonObject.get("first_name")?.asStringOrNull(),
            lastName = jsonObject.get("last_name")?.asStringOrNull(),
            middleName = jsonObject.get("middle_name")?.asStringOrNull(),
            birthdate = jsonObject.get("birthdate")?.asStringOrNull(),
            birthplace = jsonObject.get("birthplace")?.asStringOrNull(),
            country = jsonObject.get("country")?.asStringOrNull(),
            primaryTeam = jsonObject.get("primary_team").asObjectOrNull<PrimaryTeam>(gson),
            logoUrl = jsonObject.get("logo_url")?.asStringOrNull(),
            playingRole = jsonObject.get("playing_role")?.asStringOrNull(),
            battingStyle = jsonObject.get("batting_style")?.asStringOrNull(),
            bowlingStyle = jsonObject.get("bowling_style")?.asStringOrNull(),
            fieldingPosition = jsonObject.get("fielding_position")?.asStringOrNull(),
            recentMatch = jsonObject.get("recent_match")?.asLongOrNull(),
            recentAppearance = jsonObject.get("recent_appearance")?.asLongOrNull(),
            fantasyPlayerRating = jsonObject.get("fantasy_player_rating")?.asDouble,
            altName = jsonObject.get("alt_name")?.asStringOrNull(),
            facebookProfile = jsonObject.get("facebook_profile")?.asStringOrNull(),
            twitterProfile = jsonObject.get("twitter_profile")?.asStringOrNull(),
            instagramProfile = jsonObject.get("instagram_profile")?.asStringOrNull(),
            debutData = jsonObject.get("debut_data")?.asStringOrNull(),
            thumbUrl = jsonObject.get("thumb_url")?.asStringOrNull(),
            nationality = jsonObject.get("nationality")?.asStringOrNull(),
            role = jsonObject.get("role")?.asStringOrNull(),
            roleStr = jsonObject.get("role_str")?.asString
        )
    }
}


class ScoreMatchResponseDeserializer : JsonDeserializer<ScoreMatchResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ScoreMatchResponse? {
        if (json == null) return null
        val jsonObject = json.asJsonObject
        val gson = Gson()

        return ScoreMatchResponse(
            matchId = jsonObject.get("match_id")?.asLongOrNull(),
            title = jsonObject.get("title")?.asStringOrNull(),
            shortTitle = jsonObject.get("short_title")?.asStringOrNull(),
            subtitle = jsonObject.get("subtitle")?.asStringOrNull(),
            matchNumber = jsonObject.get("match_number")?.asStringOrNull(),
            format = jsonObject.get("format")?.asLongOrNull(),
            formatStr = jsonObject.get("format_str")?.asStringOrNull(),
            status = jsonObject.get("status")?.asLongOrNull(),
            statusStr = context?.deserialize(jsonObject.get("status_str"), MatchStatus::class.java),
            statusNote = jsonObject.get("status_note")?.asStringOrNull(),
            verified = jsonObject.get("verified")?.asStringOrNull(),
            preSquad = jsonObject.get("pre_squad")?.asStringOrNull(),
            oddsAvailable = jsonObject.get("odds_available")?.asStringOrNull(),
            gameState = jsonObject.get("game_state")?.asLongOrNull(),
            gameStateStr = jsonObject.get("game_state_str")?.asStringOrNull(),
            competition = jsonObject.get("competition").asObjectOrNull<Competition>(gson),
            teamA = jsonObject.get("teama").asObjectOrNull<Team>(gson),
            teamB = jsonObject.get("teamb").asObjectOrNull<Team>(gson),
            dateStart = jsonObject.get("date_start")?.asStringOrNull(),
            dateEnd = jsonObject.get("date_end")?.asStringOrNull(),
            timestampStart = jsonObject.get("timestamp_start")?.asLongOrNull(),
            timestampEnd = jsonObject.get("timestamp_end")?.asLongOrNull(),
            dateStartIst = jsonObject.get("date_start_ist")?.asStringOrNull(),
            dateEndIst = jsonObject.get("date_end_ist")?.asStringOrNull(),
            venue = jsonObject.get("venue").asObjectOrNull<Venue>(gson),
            umpires = jsonObject.get("umpires")?.asStringOrNull(),
            referee = jsonObject.get("referee")?.asStringOrNull(),
            equation = jsonObject.get("equation")?.asStringOrNull(),
            live = jsonObject.get("live")?.asStringOrNull(),
            result = jsonObject.get("result")?.asStringOrNull(),
            resultType = jsonObject.get("result_type")?.asLongOrNull(),
            winMargin = jsonObject.get("win_margin")?.asStringOrNull(),
            winningTeamId = jsonObject.get("winning_team_id")?.asLongOrNull(),
            commentary = jsonObject.get("commentary")?.asLongOrNull(),
            wagon = jsonObject.get("wagon")?.asLongOrNull(),
            latestInningNumber = jsonObject.get("latest_inning_number")?.asLongOrNull(),
            preSquadTime = jsonObject.get("presquad_time")?.asStringOrNull(),
            verifyTime = jsonObject.get("verify_time")?.asStringOrNull(),
            matchDlsAffected = jsonObject.get("match_dls_affected")?.asStringOrNull(),
            liveInningNumber = jsonObject.get("live_inning_number")?.asStringOrNull(),
            day = jsonObject.get("day")?.asStringOrNull(),
            session = jsonObject.get("session")?.asStringOrNull(),
            toss = jsonObject.get("toss").asObjectOrNull<Toss>(gson),
            currentOver = jsonObject.get("current_over")?.asStringOrNull(),
            previousOver = jsonObject.get("previous_over")?.asStringOrNull(),
            manOfTheMatch = jsonObject.get("man_of_the_match").asObjectOrNull<ManOfTheMatch>(gson),
            manOfTheSeries = jsonObject.get("man_of_the_series")?.asStringOrNull(),
            isFollowon = jsonObject.get("is_followon")?.asLongOrNull(),
            teamBattingFirst = jsonObject.get("team_batting_first")?.asStringOrNull(),
            teamBattingSecond = jsonObject.get("team_batting_second")?.asStringOrNull(),
            lastFiveOvers = jsonObject.get("last_five_overs")?.asStringOrNull(),
            innings = jsonObject.get("innings").asObjectOrNull<List<Inning>>(gson),
            players = jsonObject.get("players").asObjectOrNull<List<Player>>(gson),
            preMatchOdds = jsonObject.get("pre_match_odds").asStringOrNull(),
            dayRemainingOver = jsonObject.get("day_remaining_over")?.asStringOrNull(),
            matchNotes = jsonObject.get("match_notes").asObjectOrNull<List<List<String>>>(gson)
        )
    }
}


class MatchDataTypeAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType
        if (rawType != MatchData::class.java) {
            return null
        }

        val parameterizedType = type.type as ParameterizedType
        val actualType = parameterizedType.actualTypeArguments[0]

        val delegate = gson.getDelegateAdapter(this, type)
        val elementAdapter = gson.getAdapter(JsonElement::class.java)

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter, value: T) {
                delegate.write(out, value)
            }

            override fun read(`in`: JsonReader): T {
                val jsonElement = elementAdapter.read(`in`)

                val jsonObject = jsonElement.asJsonObject
                val status = jsonObject.get("status")?.asStringOrNull()
                val eTag = jsonObject.get("etag")?.asStringOrNull()
                val modified = jsonObject.get("modified")?.asStringOrNull()
                val datetime = jsonObject.get("datetime")?.asStringOrNull()
                val apiVersion = jsonObject.get("api_version")?.asStringOrNull()
                val ch = jsonObject.get("ch")?.asLongOrNull()

                val response = jsonObject.get("response")?.let {
                    try {
                        val result: T? = gson.fromJson(it, actualType)
                        result
                    } catch (_: Exception) {
                        null
                    }
                }

                val matchData = MatchData(
                    status = status,
                    response = response,
                    eTag = eTag,
                    modified = modified,
                    datetime = datetime,
                    apiVersion = apiVersion,
                    ch = ch
                )

                return matchData as T
            }
        }
    }
}

class DatumAttributesDeserializer : JsonDeserializer<DatumAttributes> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DatumAttributes? {
        if (json == null) return null
        val jsonObject = json.asJsonObject
        val gson = Gson()
        val title = jsonObject.get("title")?.asStringOrNull()
        val slug = jsonObject.get("slug")?.asStringOrNull()
        val contentSort = jsonObject.get("content_sort")?.asStringOrNull()
        val contentFull = jsonObject.get("content_full")?.asStringOrNull()
        val status = jsonObject.get("status")?.asBooleanOrNull()
        val createdAt = jsonObject.get("createdAt")?.asStringOrNull()
        val updatedAt = jsonObject.get("updatedAt")?.asStringOrNull()
        val publishedAt = jsonObject.get("publishedAt")?.asStringOrNull()
        val seo = jsonObject.get("seo")?.asStringOrNull()

        val postMedia = jsonObject.get("post_media")?.asObjectOrNull<PostMedia>(gson)

        val category = jsonObject.get("Category")?.asObjectOrNull<Category>(gson)

        return DatumAttributes(
            title = title,
            slug = slug,
            contentSort = contentSort,
            contentFull = contentFull,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            publishedAt = publishedAt,
            seo = seo,
            postMedia = postMedia,
            category = category
        )
    }
}
