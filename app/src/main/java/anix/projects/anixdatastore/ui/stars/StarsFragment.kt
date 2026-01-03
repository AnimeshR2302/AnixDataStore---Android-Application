package anix.projects.anixdatastore.ui.stars

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
import anix.projects.anixdatastore.ui.add.AddStarActivity
import anix.projects.anixdatastore.ui.common.TagPicker
import anix.projects.anixdatastore.ui.viewmodel.StarsViewModel

class StarsFragment : Fragment() {
    private val viewModel: StarsViewModel by viewModels()
    private lateinit var container: LinearLayout
    private lateinit var btnAddStar: Button

    override fun onCreateView(
        inflater: LayoutInflater, containerParent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_stars, containerParent, false)
        container = root.findViewById(R.id.stars_list_container)
        btnAddStar = root.findViewById(R.id.btn_add_star)
        btnAddStar.setOnClickListener { startActivity(Intent(requireContext(), AddStarActivity::class.java)) }
        val btnFilter = root.findViewById<Button>(R.id.btn_filter_stars)
        btnFilter.setOnClickListener {
            TagPicker.showCategoryThenTags(requireContext(), anix.projects.anixdatastore.data.AnixRepository()) { ids ->
                viewModel.loadStarsByTags(ids)
            }
        }

        viewModel.stars.observe(viewLifecycleOwner, Observer { stars ->
            container.removeAllViews()
            for (s in stars) {
                val t = TextView(requireContext())
                t.text = "★ ${s.name} — ${s.language}"
                container.addView(t)
            }
        })

        viewModel.loadAll()
        return root
    }
}
