package anix.projects.anixdatastore.ui.add

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import anix.projects.anixdatastore.R
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Playlist

class AddPlaylistActivity : AppCompatActivity() {
    private val repo = AnixRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_playlist)

        val btnCreate = findViewById<Button>(R.id.btn_create_playlist)
        val btnAddLink = findViewById<Button>(R.id.btn_add_link_to_playlist)

        btnCreate.setOnClickListener { showCreatePlaylistDialog() }
        btnAddLink.setOnClickListener { showAddLinkToPlaylistFlow() }
    }

    private fun showCreatePlaylistDialog() {
        val et = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Playlist title")
            .setView(et)
            .setPositiveButton("Create") { _, _ ->
                val title = et.text.toString().trim()
                if (title.isEmpty()) {
                    Toast.makeText(this, "Title required", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                Thread {
                    repo.insertPlaylist(Playlist(title = title))
                    runOnUiThread {
                        Toast.makeText(this, "Playlist created", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAddLinkToPlaylistFlow() {
        Thread {
            val playlists = repo.getPlaylistsByDate()
            val links = repo.getAllLinks()
            runOnUiThread {
                if (playlists.isEmpty()) {
                    Toast.makeText(this, "No playlists exist. Create one first.", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }
                val playlistNames = playlists.map { it.title }.toTypedArray()
                AlertDialog.Builder(this)
                    .setTitle("Choose playlist")
                    .setItems(playlistNames) { _, which ->
                        val chosen = playlists[which]
                        showLinkSelectionDialog(chosen.id, links)
                    }
                    .show()
            }
        }.start()
    }

    private fun showLinkSelectionDialog(playlistId: Long, links: List<anix.projects.anixdatastore.data.entities.Link>) {
        val linkTitles = links.map { it.title }.toTypedArray()
        val checked = BooleanArray(linkTitles.size)
        AlertDialog.Builder(this)
            .setTitle("Select links to add")
            .setMultiChoiceItems(linkTitles, checked) { _, _, _ -> }
            .setPositiveButton("Add") { _, _ ->
                Thread {
                    for (i in checked.indices) {
                        if (checked[i]) repo.addLinkToPlaylist(playlistId, links[i].id)
                    }
                    runOnUiThread {
                        Toast.makeText(this, "Links added to playlist", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
