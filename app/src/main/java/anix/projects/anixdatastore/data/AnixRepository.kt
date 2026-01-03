package anix.projects.anixdatastore.data

import anix.projects.anixdatastore.AnixApp
import anix.projects.anixdatastore.data.entities.*

class AnixRepository(private val db: AnixDatabase = AnixApp.database) {
    private val tagDao = db.tagDao()
    private val starDao = db.starDao()
    private val linkDao = db.linkDao()
    private val playlistDao = db.playlistDao()

    suspend fun insertCategory(category: TagCategory) = tagDao.insertCategory(category)
    suspend fun insertTag(tag: Tag) = tagDao.insertTag(tag)
    suspend fun getAllCategories() = tagDao.getAllCategories()
    suspend fun getTagsForCategory(categoryId: Long) = tagDao.getTagsForCategory(categoryId)

    suspend fun insertStar(star: Star): Long = starDao.insertStar(star)
    suspend fun linkStarToTag(starId: Long, tagId: Long) =
        starDao.insertStarTagCrossRef(StarTagCrossRef(starId, tagId))
    suspend fun getStarsByTags(tagIds: List<Long>) =
        if (tagIds.isEmpty()) starDao.getAllStars() else starDao.getStarsByTagIntersection(tagIds, tagIds.size)

    suspend fun insertLink(link: Link): Long = linkDao.insertLink(link)
    suspend fun linkLinkToTag(linkId: Long, tagId: Long) =
        linkDao.insertLinkTagCrossRef(LinkTagCrossRef(linkId, tagId))
    suspend fun getLinksByTags(tagIds: List<Long>) =
        if (tagIds.isEmpty()) linkDao.getAllLinks() else linkDao.getLinksByTagIntersection(tagIds, tagIds.size)
    suspend fun getAllLinks() = linkDao.getAllLinks()

    suspend fun insertPlaylist(playlist: Playlist): Long = playlistDao.insertPlaylist(playlist)
    suspend fun addLinkToPlaylist(playlistId: Long, linkId: Long, position: Int = 0) =
        playlistDao.insertPlaylistLinkCrossRef(PlaylistLinkCrossRef(playlistId, linkId, position))
    suspend fun getPlaylistsByDate() = playlistDao.getPlaylistsSortedByDate()
    suspend fun getPlaylistsByFavorites() = playlistDao.getPlaylistsSortedByFavorites()
}
