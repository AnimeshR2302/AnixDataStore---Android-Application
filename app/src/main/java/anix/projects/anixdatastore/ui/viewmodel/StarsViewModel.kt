package anix.projects.anixdatastore.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Star
import kotlinx.coroutines.launch

class StarsViewModel(private val repo: AnixRepository = AnixRepository()) : ViewModel() {
    private val _stars = MutableLiveData<List<Star>>(emptyList())
    val stars: LiveData<List<Star>> = _stars

    fun loadStarsByTags(tagIds: List<Long>) {
        viewModelScope.launch {
            _stars.postValue(repo.getStarsByTags(tagIds))
        }
    }

    fun loadAll() = loadStarsByTags(emptyList())
}
