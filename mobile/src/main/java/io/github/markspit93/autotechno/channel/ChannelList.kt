package io.github.markspit93.autotechno.channel

import io.github.markspit93.autotechno.R

object ChannelList {

    private const val CHANNEL_STYLE_CHILLOUT = "Chillout"
    private const val CHANNEL_STYLE_EDM = "EDM"
    private const val CHANNEL_STYLE_HOUSE = "House"
    private const val CHANNEL_STYLE_TECHNO = "Techno"
    private const val CHANNEL_STYLE_TRANCE = "Trance"

    fun getStyles() = listOf(
            CHANNEL_STYLE_CHILLOUT,
            CHANNEL_STYLE_EDM,
            CHANNEL_STYLE_HOUSE,
            CHANNEL_STYLE_TECHNO,
            CHANNEL_STYLE_TRANCE
    )

    fun getChannelsForStyle(category: String): List<Channel> =
            when (category) {
                CHANNEL_STYLE_CHILLOUT -> chilloutChannels
                CHANNEL_STYLE_EDM -> edmChannels
                CHANNEL_STYLE_HOUSE -> houseChannels
                CHANNEL_STYLE_TECHNO -> technoChannels
                CHANNEL_STYLE_TRANCE -> tranceChannels
                else -> emptyList()
            }

    private val chilloutChannels = listOf(
            Channel("ambient", "Ambient", R.drawable.ambient),
            Channel("chillout", "Chillout", R.drawable.chillout)
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

    private val houseChannels = listOf(
            Channel("house", "House", R.drawable.house)
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
