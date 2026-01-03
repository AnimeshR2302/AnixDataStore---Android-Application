package anix.projects.anixdatastore.ui.common

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import anix.projects.anixdatastore.data.AnixRepository

object TagPicker {
    fun showCategoryThenTags(
        context: Context,
        repo: AnixRepository,
        onTagsSelected: (List<Long>) -> Unit
    ) {
        Thread {
            try {
                val categories = repo.getAllCategories()
                val names = categories.map { it.name }.toTypedArray()
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    if (names.isEmpty()) {
                        AlertDialog.Builder(context)
                            .setTitle("No tag categories")
                            .setMessage("No tag categories found. Please create tag categories and tags first.")
                            .setPositiveButton("OK", null)
                            .show()
                        return@post
                    }
                    AlertDialog.Builder(context)
                        .setTitle("Pick category")
                        .setItems(names) { _, which ->
                            val categoryId = categories[which].id
                            showTagsForCategory(context, repo, categoryId, onTagsSelected)
                        }
                        .show()
                }
            } catch (e: Exception) {
                // ignore
            }
        }.start()
    }

    private fun showTagsForCategory(
        context: Context,
        repo: AnixRepository,
        categoryId: Long,
        onTagsSelected: (List<Long>) -> Unit
    ) {
        Thread {
            val tags = repo.getTagsForCategory(categoryId)
            val names = tags.map { it.name }.toTypedArray()
            val checked = BooleanArray(names.size)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                if (names.isEmpty()) {
                    AlertDialog.Builder(context)
                        .setTitle("No tags")
                        .setMessage("No tags found in this category.")
                        .setPositiveButton("OK", null)
                        .show()
                    return@post
                }
                AlertDialog.Builder(context)
                    .setTitle("Select tags (AND)")
                    .setMultiChoiceItems(names, checked) { _, _, _ -> }
                    .setPositiveButton("Apply") { _, _ ->
                        val selected = tags.filterIndexed { index, _ -> checked[index] }.map { it.id }
                        onTagsSelected(selected)
                    }
                    .show()
            }
        }.start()
    }
}
