package anix.projects.anixdatastore.ui.add

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import anix.projects.anixdatastore.R
import anix.projects.anixdatastore.data.AnixRepository
import anix.projects.anixdatastore.data.entities.Star
import anix.projects.anixdatastore.ui.common.TagPicker

class AddStarActivity : AppCompatActivity() {
    private val repo = AnixRepository()
    private lateinit var etName: EditText
    private lateinit var etHair: EditText
    private lateinit var etLanguage: EditText
    private lateinit var btnPickGenres: Button
    private lateinit var btnSave: Button
    private var selectedGenreIds: List<Long> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_star)
        etName = findViewById(R.id.et_star_name)
        etHair = findViewById(R.id.et_star_hair)
        etLanguage = findViewById(R.id.et_star_language)
        btnPickGenres = findViewById(R.id.btn_pick_genres)
        btnSave = findViewById(R.id.btn_save_star)

        btnPickGenres.setOnClickListener {
            TagPicker.showCategoryThenTags(this, repo) { ids ->
                selectedGenreIds = ids
                runOnUiThread { btnPickGenres.text = "Genres (${ids.size})" }
            }
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val hair = etHair.text.toString().trim()
            val language = etLanguage.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (hair.isEmpty()) {
                Toast.makeText(this, "Hair color is required", Toast.LENGTH_SHORT).show()
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
                val starId = repo.insertStar(Star(name = name, hairColor = hair, language = language))
                for (tid in selectedGenreIds) repo.linkStarToTag(starId, tid)
                runOnUiThread {
                    Toast.makeText(this, "Star saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }
    }
}
