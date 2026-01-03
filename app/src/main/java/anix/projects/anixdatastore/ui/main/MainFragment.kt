package anix.projects.anixdatastore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import anix.projects.anixdatastore.R
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.ui.common.TagPicker
import anix.projects.anixdatastore.ui.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var container: LinearLayout
    private lateinit var btnFilter: Button

    override fun onCreateView(
        inflater: LayoutInflater, containerParent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, containerParent, false)
        container = root.findViewById(R.id.main_results_container)
        btnFilter = root.findViewById(R.id.btn_add_filter)

        btnFilter.setOnClickListener {
            TagPicker.showCategoryThenTags(requireContext(), AnixRepository()) { ids ->
                viewModel.loadByTags(ids)
            }
        }

        viewModel.stars.observe(viewLifecycleOwner, Observer { stars ->
            renderResults(stars, viewModel.links.value ?: emptyList())
        })
        viewModel.links.observe(viewLifecycleOwner, Observer { links ->
            renderResults(viewModel.stars.value ?: emptyList(), links)
        })

        viewModel.loadAll()
        return root
    }

    private fun renderResults(stars: List<anix.projects.anixdatastore.data.entities.Star>, links: List<anix.projects.anixdatastore.data.entities.Link>) {
        container.removeAllViews()

        val headerStars = TextView(requireContext())
        headerStars.text = "Stars"
        container.addView(headerStars)
        for (s in stars) {
            val t = TextView(requireContext())
            t.text = "★ ${s.name} — ${s.language}"
            container.addView(t)
        }

        val headerLinks = TextView(requireContext())
        headerLinks.text = "Links"
        container.addView(headerLinks)
        for (l in links) {
            val t = TextView(requireContext())
            t.text = "▶ ${l.title} — ${l.actorNames}"
            container.addView(t)
        }
    }
}
