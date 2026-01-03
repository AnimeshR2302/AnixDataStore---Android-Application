package anix.projects.anixdatastore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Link
import kotlinx.coroutines.launch

class LinksViewModel(private val repo: AnixRepository = AnixRepository()) : ViewModel() {
    private val _links = MutableLiveData<List<Link>>(emptyList())
    val links: LiveData<List<Link>> = _links

    fun loadLinksByTags(tagIds: List<Long>) {
        viewModelScope.launch {
            _links.postValue(repo.getLinksByTags(tagIds))
        }
    }

    fun loadAll() = loadLinksByTags(emptyList())
}
