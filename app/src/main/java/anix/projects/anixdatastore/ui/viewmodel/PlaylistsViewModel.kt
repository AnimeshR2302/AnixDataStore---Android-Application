package anix.projects.anixdatastore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Playlist
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val repo: AnixRepository = AnixRepository()) : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>(emptyList())
    val playlists: LiveData<List<Playlist>> = _playlists

    fun loadByDate() {
        viewModelScope.launch { _playlists.postValue(repo.getPlaylistsByDate()) }
    }

    fun loadByFavorites() {
        viewModelScope.launch { _playlists.postValue(repo.getPlaylistsByFavorites()) }
    }
}
