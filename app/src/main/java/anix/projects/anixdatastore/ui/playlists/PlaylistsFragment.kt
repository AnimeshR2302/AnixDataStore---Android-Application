package anix.projects.anixdatastore.ui.playlists

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
import anix.projects.anixdatastore.ui.add.AddPlaylistActivity
import anix.projects.anixdatastore.ui.viewmodel.PlaylistsViewModel

class PlaylistsFragment : Fragment() {
    private val viewModel: PlaylistsViewModel by viewModels()
    private lateinit var container: LinearLayout
    private lateinit var btnAddPlaylist: Button

    override fun onCreateView(
        inflater: LayoutInflater, containerParent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_playlists, containerParent, false)
        container = root.findViewById(R.id.playlists_list_container)
        btnAddPlaylist = root.findViewById(R.id.btn_add_playlist)
        btnAddPlaylist.setOnClickListener { startActivity(Intent(requireContext(), AddPlaylistActivity::class.java)) }

        viewModel.playlists.observe(viewLifecycleOwner, Observer { playlists ->
            container.removeAllViews()
            for (p in playlists) {
                val t = TextView(requireContext())
                t.text = "${p.title}"
                container.addView(t)
            }
        })

        viewModel.loadByDate()
        return root
    }
}
