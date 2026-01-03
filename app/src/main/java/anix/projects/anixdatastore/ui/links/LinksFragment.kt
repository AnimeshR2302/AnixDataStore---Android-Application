package anix.projects.anixdatastore.ui.links

import android.content.Intent
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
import anix.projects.anixdatastore.ui.add.AddLinkActivity
import anix.projects.anixdatastore.ui.common.TagPicker
import anix.projects.anixdatastore.ui.viewmodel.LinksViewModel

class LinksFragment : Fragment() {
    private val viewModel: LinksViewModel by viewModels()
    private lateinit var container: LinearLayout
    private lateinit var btnAddLink: Button

    override fun onCreateView(
        inflater: LayoutInflater, containerParent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_links, containerParent, false)
        container = root.findViewById(R.id.links_list_container)
        btnAddLink = root.findViewById(R.id.btn_add_link)
        btnAddLink.setOnClickListener { startActivity(Intent(requireContext(), AddLinkActivity::class.java)) }
        val btnFilter = root.findViewById<Button>(R.id.btn_filter_links)
        btnFilter.setOnClickListener {
            TagPicker.showCategoryThenTags(requireContext(), AnixRepository()) { ids ->
                viewModel.loadLinksByTags(ids)
            }
        }

        viewModel.links.observe(viewLifecycleOwner, Observer { links ->
            container.removeAllViews()
            for (l in links) {
                val t = TextView(requireContext())
                t.text = "▶ ${l.title} — ${l.actorNames}"
                container.addView(t)
            }
        })

        viewModel.loadAll()
        return root
    }
}
