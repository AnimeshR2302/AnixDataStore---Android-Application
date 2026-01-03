package anix.projects.anixdatastore.ui.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import anix.projects.anixdatastore.R
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Tag
import anix.projects.anixdatastore.data.entities.TagCategory
import kotlinx.coroutines.launch

class TagManagementFragment : Fragment() {
    private val repo = AnixRepository()
    private lateinit var etCategory: EditText
    private lateinit var btnCreateCategory: Button
    private lateinit var lvCategories: ListView
    private lateinit var etTagName: EditText
    private lateinit var btnCreateTag: Button

    private var categories = listOf<TagCategory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tag_management, container, false)
        etCategory = root.findViewById(R.id.et_new_category)
        btnCreateCategory = root.findViewById(R.id.btn_create_category)
        lvCategories = root.findViewById(R.id.lv_categories)
        etTagName = root.findViewById(R.id.et_new_tag_name)
        btnCreateTag = root.findViewById(R.id.btn_create_tag)

        btnCreateCategory.setOnClickListener { createCategory() }
        btnCreateTag.setOnClickListener { createTag() }

        refreshCategories()
        return root
    }

    private fun refreshCategories() {
        lifecycleScope.launch {
            categories = repo.getAllCategories()
            val names = categories.map { it.name }
            lvCategories.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
        }
    }

    private fun createCategory() {
        val name = etCategory.text.toString().trim()
        if (name.isEmpty()) return
        lifecycleScope.launch {
            repo.insertCategory(TagCategory(name = name))
            etCategory.post { etCategory.text.clear() }
            refreshCategories()
        }
    }

    private fun createTag() {
        val name = etTagName.text.toString().trim()
        val pos = lvCategories.checkedItemPosition
        if (name.isEmpty() || pos < 0 || pos >= categories.size) return
        val categoryId = categories[pos].id
        lifecycleScope.launch {
            repo.insertTag(Tag(name = name, categoryId = categoryId))
            etTagName.post { etTagName.text.clear() }
        }
    }
}
