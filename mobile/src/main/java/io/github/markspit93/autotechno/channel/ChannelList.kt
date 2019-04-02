package io.github.markspit93.autotechno.channel

import io.github.markspit93.autotechno.R

object ChannelList {

    private const val CHANNEL_STYLE_AMBIENT = "Ambient"
    private const val CHANNEL_STYLE_BASS = "Bass"
    private const val CHANNEL_STYLE_CHILLOUT = "Chillout"
    private const val CHANNEL_STYLE_DANCE = "Dance"
    private const val CHANNEL_STYLE_DEEP = "Deep"
    private const val CHANNEL_STYLE_EDM = "EDM"
    private const val CHANNEL_STYLE_HOUSE = "House"
    private const val CHANNEL_STYLE_LOUNGE = "Lounge"
    private const val CHANNEL_STYLE_TECHNO = "Techno"
    private const val CHANNEL_STYLE_TRANCE = "Trance"

    fun getStyles() = listOf(
            CHANNEL_STYLE_AMBIENT,
            CHANNEL_STYLE_BASS,
            CHANNEL_STYLE_CHILLOUT,
            CHANNEL_STYLE_EDM,
            CHANNEL_STYLE_DANCE,
            CHANNEL_STYLE_DEEP,
            CHANNEL_STYLE_HOUSE,
            CHANNEL_STYLE_LOUNGE,
            CHANNEL_STYLE_TECHNO,
            CHANNEL_STYLE_TRANCE
    )

    fun getChannelsForStyle(category: String): List<Channel> =
            when (category) {
                CHANNEL_STYLE_AMBIENT -> ambientChannels
                CHANNEL_STYLE_BASS -> bassChannels
                CHANNEL_STYLE_CHILLOUT -> chilloutChannels
                CHANNEL_STYLE_EDM -> edmChannels
                CHANNEL_STYLE_HOUSE -> houseChannels
                CHANNEL_STYLE_DANCE -> danceChannels
                CHANNEL_STYLE_DEEP -> deepChannels
                CHANNEL_STYLE_LOUNGE -> loungeChannels
                CHANNEL_STYLE_TECHNO -> technoChannels
                CHANNEL_STYLE_TRANCE -> tranceChannels
                else -> emptyList()
            }

    private val ambientChannels = listOf(
            Channel("ambient", "Ambient", R.drawable.ambient),
            Channel("chillout", "Chillout", R.drawable.chillout),
            Channel("chilloutdreams", "Chillout Dreams", R.drawable.chilloutdreams),
            Channel("electronicpioneers", "Electronic Pioneers", R.drawable.electronicpioneers),
            Channel("futuregarage", "Future Garage", R.drawable.futuregarage),
            Channel("spacemusic", "Space Dreams", R.drawable.spacedreams),
            Channel("psybient", "Psybient", R.drawable.psybient),
            Channel("psychill", "Psychill", R.drawable.psychill)
    )

    private val bassChannels = listOf(
            Channel("atmosphericbreaks", "Atmospheric Breaks", R.drawable.atmposphericbreaks),
            Channel("bassline", "Bassline", R.drawable.bassline),
            Channel("bassnjackinhouse", "Bass & Jackin' House", R.drawable.bassnjackinhouse),
            Channel("bigbeat", "Big Beat", R.drawable.bigbeat),
            Channel("breaks", "Breaks", R.drawable.breaks),
            Channel("chillstep", "Chillstep", R.drawable.chillstep),
            Channel("clubdubstep", "Club Dubstep", R.drawable.clubdubstep),
            Channel("darkdnb", "Dark DNB", R.drawable.darkdnb),
            Channel("drumandbass", "Drum and Bass", R.drawable.drumandbass),
            Channel("drumstep", "Drumstep", R.drawable.drumstep),
            Channel("dub", "Dub", R.drawable.dub),
            Channel("dubstep", "Dubstep", R.drawable.dubstep),
            Channel("edm", "EDM Hits", R.drawable.edmhits),
            Channel("futurebass", "Future Bass", R.drawable.futurebass),
            Channel("futuregarage", "Future Garage", R.drawable.futuregarage),
            Channel("glitchhop", "Glitch Hop", R.drawable.glitchhop),
            Channel("jungle", "Jungle", R.drawable.jungle),
            Channel("liquiddnb", "Liquid DNB", R.drawable.liquiddnb),
            Channel("liquiddubstep", "Liquid Dubstep", R.drawable.liquiddubstep),
            Channel("liquidtrap", "Liquid Trap", R.drawable.liquidtrap),
            Channel("mainstage", "Mainstage", R.drawable.mainstage),
            Channel("trap", "Trap", R.drawable.trap)
    )

    private val chilloutChannels = listOf(
            Channel("ambient", "Ambient", R.drawable.ambient),
            Channel("chillout", "Chillout", R.drawable.chillout),
            Channel("chilloutdreams", "Chillout Dreams", R.drawable.chilloutdreams),
            Channel("chillstep", "Chillstep", R.drawable.chillstep),
            Channel("chillhop", "Chillhop", R.drawable.chillhop),
            Channel("chillntropicalhouse", "Chill & Tropical House", R.drawable.chilltropicalhouse),
            Channel("deephouse", "Deep House", R.drawable.deephouse),
            Channel("deepnudisco", "Deep Nu-Disco", R.drawable.deepnudisco),
            Channel("downtempolounge", "Downtempo Lounge", R.drawable.downtempolounge),
            Channel("dubtechno", "Dub Techno", R.drawable.dubtechno),
            Channel("electronicpioneers", "Electronic Pioneers", R.drawable.electronicpioneers),
            Channel("futuregarage", "Future Garage", R.drawable.futuregarage),
            Channel("indiebeats", "Indie Beats", R.drawable.indiebeats),
            Channel("lofihiphop", "Lofi Hip-Hop", R.drawable.lofihophop),
            Channel("lounge", "Lounge", R.drawable.lounge),
            Channel("melodicprogressive", "Melodic Progressive", R.drawable.melodicprogressive),
            Channel("spacemusic", "Space Dreams", R.drawable.spacedreams),
            Channel("psybient", "Psybient", R.drawable.psybient),
            Channel("psychill", "Psychill", R.drawable.psychill),
            Channel("vocalchillout", "Vocal Chillout", R.drawable.vocalchillout)
    )

    private val edmChannels = listOf(
            Channel("bigroomhouse", "Big Room House", R.drawable.bigroomhouse),
            Channel("chillstep", "Chillstep", R.drawable.chillstep),
            Channel("chillntropicalhouse", "Chill & Tropical House", R.drawable.chilltropicalhouse),
            Channel("clubdubstep", "Club Dubstep", R.drawable.clubdubstep),
            Channel("club", "Club Sounds", R.drawable.clubsounds),
            Channel("edm", "EDM Hits", R.drawable.edmhits),
            Channel("electropop", "Electropop", R.drawable.electropop),
            Channel("electrohouse", "Electro House", R.drawable.electrohouse),
            Channel("epictrance", "Epic Trance", R.drawable.epictrance),
            Channel("futurebass", "Future Bass", R.drawable.futurebass),
            Channel("indiedance", "Indie Dance", R.drawable.indiedance),
            Channel("mainstage", "Mainstage", R.drawable.mainstage),
            Channel("trap", "Trap", R.drawable.trap),
            Channel("trance", "Trance", R.drawable.trance),
            Channel("umfradio", "UMF Radio", R.drawable.umfradio),
            Channel("vocaltrance", "Vocal Trance", R.drawable.vocaltrance)
    )

    private val danceChannels = listOf(
            Channel("00sclubhits", "00s Club Hits", R.drawable.clubhits),
            Channel("bassnjackinhouse", "Bass & Jackin' House", R.drawable.bassnjackinhouse),
            Channel("bigroomhouse", "Big Room House", R.drawable.bigroomhouse),
            Channel("chillntropicalhouse", "Chill & Tropical House", R.drawable.chilltropicalhouse),
            Channel("classiceurodance", "Classic Eurodance", R.drawable.classiceurodance),
            Channel("classiceurodisco", "Classic Eurodisco", R.drawable.classiceurodisco),
            Channel("classicvocaltrance", "Classic Vocal Trance", R.drawable.classicvocaltrance),
            Channel("club", "Club Sounds", R.drawable.clubsounds),
            Channel("discohouse", "Disco House", R.drawable.discohouse),
            Channel("djmixes", "DJ Mixes", R.drawable.djmixes),
            Channel("edm", "EDM Hits", R.drawable.edmhits),
            Channel("electrohouse", "Electro House", R.drawable.electrohouse),
            Channel("electropop", "Electropop", R.drawable.electropop),
            Channel("epictrance", "Epic Trance", R.drawable.epictrance),
            Channel("eurodance", "Eurodance", R.drawable.eurodance),
            Channel("handsup", "Hands Up", R.drawable.handsup),
            Channel("harddance", "Hard Dance", R.drawable.harddance),
            Channel("indiedance", "Indie Dance", R.drawable.indiedance),
            Channel("mainstage", "Mainstage", R.drawable.mainstage),
            Channel("nightcore", "Nightcore", R.drawable.nightcore),
            Channel("nudisco", "Nu Disco", R.drawable.nudisco),
            Channel("russianclubhits", "Russian Club Hits", R.drawable.russianclubhits),
            Channel("trance", "Trance", R.drawable.trance),
            Channel("vocaltrance", "Vocal Trance", R.drawable.vocaltrance)
    )

    private val deepChannels = listOf(
            Channel("deephouse", "Deep House", R.drawable.deephouse),
            Channel("deepnudisco", "Deep Nu-Disco", R.drawable.deepnudisco),
            Channel("deeptech", "Deep Tech", R.drawable.deeptech),
            Channel("dub", "Dub", R.drawable.dub),
            Channel("dubtechno", "Dub Techno", R.drawable.dubtechno)
    )

    private val houseChannels = listOf(
            Channel("bassline", "Bassline", R.drawable.bassline),
            Channel("bassnjackinhouse", "Bass & Jackin' House", R.drawable.bassnjackinhouse),
            Channel("bigroomhouse", "Big Room House", R.drawable.bigroomhouse),
            Channel("00sclubhits", "00s Club Hits", R.drawable.clubhits),
            Channel("chillntropicalhouse", "Chill & Tropical House", R.drawable.chilltropicalhouse),
            Channel("deephouse", "Deep House", R.drawable.deephouse),
            Channel("deepnudisco", "Deep Nu-Disco", R.drawable.deepnudisco),
            Channel("deeptech", "Deep Tech", R.drawable.deeptech),
            Channel("detroithousentechno", "Detroit House & Techno", R.drawable.detroit_house_techno),
            Channel("discohouse", "Disco House", R.drawable.discohouse),
            Channel("djmixes", "DJ Mixes", R.drawable.djmixes),
            Channel("downtempolounge", "Downtempo Lounge", R.drawable.downtempolounge),
            Channel("edm", "EDM Hits", R.drawable.edmhits),
            Channel("electrohouse", "Electro House", R.drawable.electrohouse),
            Channel("electropop", "Electropop", R.drawable.electropop),
            Channel("electroswing", "Electroswing", R.drawable.electroswing),
            Channel("funkyhouse", "Funky House", R.drawable.funkyhouse),
            Channel("house", "House", R.drawable.house),
            Channel("jazzhouse", "Jazz House", R.drawable.jazzhouse),
            Channel("latinhouse", "Latin House", R.drawable.latinhouse),
            Channel("mainstage", "Mainstage", R.drawable.mainstage),
            Channel("melodicprogressive", "Melodic Progressive", R.drawable.melodicprogressive),
            Channel("nudisco", "Nu Disco", R.drawable.nudisco),
            Channel("oldschoolacid", "Oldschool Acid", R.drawable.oldschoolacid),
            Channel("oldschoolhouse", "Oldschool House", R.drawable.oldschoolhouse),
            Channel("progressive", "Progressive", R.drawable.progressive),
            Channel("soulfulhouse", "Soulful House", R.drawable.soulfulhouse),
            Channel("techhouse", "Tech House", R.drawable.tech_house),
            Channel("tribalhouse", "Tribal House", R.drawable.tribalhouse),
            Channel("vocalhouse", "Vocal House", R.drawable.vocalhouse)
    )

    private val loungeChannels = listOf(
            Channel("chillhop", "Chillhop", R.drawable.chillhop),
            Channel("chillntropicalhouse", "Chill & Tropical House", R.drawable.chilltropicalhouse),
            Channel("chillout", "Chillout", R.drawable.chillout),
            Channel("chilloutdreams", "Chillout Dreams", R.drawable.chilloutdreams),
            Channel("chillstep", "Chillstep", R.drawable.chillstep),
            Channel("deephouse", "Deep House", R.drawable.deephouse),
            Channel("deepnudisco", "Deep Nu-Disco", R.drawable.deepnudisco),
            Channel("downtempolounge", "Downtempo Lounge", R.drawable.downtempolounge),
            Channel("funkyhouse", "Funky House", R.drawable.funkyhouse),
            Channel("futurebass", "Future Bass", R.drawable.futurebass),
            Channel("futuregarage", "Future Garage", R.drawable.futuregarage),
            Channel("indiedance", "Indie Dance", R.drawable.indiedance),
            Channel("jazzhouse", "Jazz House", R.drawable.jazzhouse),
            Channel("liquiddnb", "Liquid DNB", R.drawable.liquiddnb),
            Channel("liquiddubstep", "Liquid Dubstep", R.drawable.liquiddubstep),
            Channel("liquidtrap", "Liquid Trap", R.drawable.liquidtrap),
            Channel("lofihiphop", "Lofi Hip-Hop", R.drawable.lofihophop),
            Channel("lounge", "Lounge", R.drawable.lounge),
            Channel("melodicprogressive", "Melodic Progressive", R.drawable.melodicprogressive),
            Channel("nudisco", "Nu Disco", R.drawable.nudisco),
            Channel("soulfulhouse", "Soulful House", R.drawable.soulfulhouse),
            Channel("vocallounge", "Vocal Lounge", R.drawable.vocallounge)
    )

    private val technoChannels = listOf(
            Channel("bassline", "Bassline", R.drawable.bassline),
            Channel("detroithousentechno", "Detroit House & Techno", R.drawable.detroit_house_techno),
            Channel("dubtechno", "Dub Techno", R.drawable.dubtechno),
            Channel("deeptech", "Deep Tech", R.drawable.deeptech),
            Channel("electronicpioneers", "Electronic Pioneers", R.drawable.electronicpioneers),
            Channel("hardtechno", "Hard Techno", R.drawable.hardtechno),
            Channel("techno", "Techno", R.drawable.techno),
            Channel("techhouse", "Tech House", R.drawable.tech_house),
            Channel("minimal", "Minimal", R.drawable.minimal),
            Channel("oldschoolacid", "Oldschool Acid", R.drawable.oldschoolacid),
            Channel("classicelectronica", "Oldschool Techno & Trance", R.drawable.oldschooltechno),
            Channel("undergroundtechno", "Underground Techno", R.drawable.underground_techno)
    )

    private val tranceChannels = listOf(
            Channel("classictrance", "Classic Trance", R.drawable.classictrance),
            Channel("classicvocaltrance", "Classic Vocal Trance", R.drawable.classicvocaltrance),
            Channel("darkpsytrance", "Dark Psy Trance", R.drawable.darkpsytrance),
            Channel("djmixes", "DJ Mixes", R.drawable.djmixes),
            Channel("epictrance", "Epic Trance", R.drawable.epictrance),
            Channel("goapsy", "Goa-Psy Trance", R.drawable.goapsytrance),
            Channel("handsup", "Hands Up", R.drawable.handsup),
            Channel("progressive", "Progressive", R.drawable.progressive),
            Channel("progressivepsy", "Progressive Psy", R.drawable.progressivepsy),
            Channel("trance", "Trance", R.drawable.trance),
            Channel("vocaltrance", "Vocal Trance", R.drawable.vocaltrance)
    )
}
