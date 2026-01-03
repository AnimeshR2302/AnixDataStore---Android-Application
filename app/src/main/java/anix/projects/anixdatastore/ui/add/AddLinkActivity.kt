package anix.projects.anixdatastore.ui.add

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import anix.projects.anixdatastore.R
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Link
import anix.projects.anixdatastore.ui.common.TagPicker

class AddLinkActivity : AppCompatActivity() {
    private val repo = AnixRepository()
    private lateinit var etTitle: EditText
    private lateinit var etActors: EditText
    private lateinit var etLanguage: EditText
    private lateinit var btnPickGenres: Button
    private lateinit var btnSave: Button
    private var selectedGenreIds: List<Long> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_link)
        etTitle = findViewById(R.id.et_link_title)
        etActors = findViewById(R.id.et_link_actors)
        etLanguage = findViewById(R.id.et_link_language)
        btnPickGenres = findViewById(R.id.btn_pick_link_genres)
        btnSave = findViewById(R.id.btn_save_link)

        btnPickGenres.setOnClickListener {
            TagPicker.showCategoryThenTags(this, repo) { ids ->
                selectedGenreIds = ids
                runOnUiThread { btnPickGenres.text = "Genres (${ids.size})" }
            }
        }

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val actors = etActors.text.toString().trim()
            val language = etLanguage.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (actors.isEmpty()) {
                Toast.makeText(this, "Actor name(s) required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (language.isEmpty()) {
                Toast.makeText(this, "Language is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedGenreIds.isEmpty()) {
                Toast.makeText(this, "At least one Genre tag is required. Create genre tags first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Thread {
                val linkId = repo.insertLink(Link(title = title, actorNames = actors, language = language))
                for (tid in selectedGenreIds) repo.linkLinkToTag(linkId, tid)
                runOnUiThread {
                    Toast.makeText(this, "Link saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}
