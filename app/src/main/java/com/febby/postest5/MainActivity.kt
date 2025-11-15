package com.febby.postest5

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.febby.postest5.databinding.ActivityMainBinding
import com.febby.postest5.databinding.DialogAddPostBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var feedAdapter: FeedAdapter

    private val stories = mutableListOf<Story>()
    private val posts = mutableListOf<Post>()

    // 🔹 Default avatars untuk profile image
    private val defaultAvatars = listOf(
        R.drawable.masha,
        R.drawable.dora,
        R.drawable.snoopy,
        R.drawable.sincan,
        R.drawable.bears
    )

    // 🔹 Mapping username → profile image agar konsisten
    private val userProfileMap = mutableMapOf<String, Int>()

    private fun pickRandomAvatar(): Int = defaultAvatars.random()

    // 🔹 Launcher untuk pilih gambar postingan
    private var tempImageUri: Uri? = null
    private lateinit var dialogBinding: DialogAddPostBinding
    // Launcher pilih gambar (di level class)
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            tempImageUri = uri
            // Cek kalau dialogBinding sudah siap
            if (::dialogBinding.isInitialized) {
                dialogBinding.ivPreview.setImageURI(uri)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // data awal
        userProfileMap["Febby"] = pickRandomAvatar()
        userProfileMap["Eka"] = pickRandomAvatar()
        userProfileMap["Widya"] = pickRandomAvatar()
        userProfileMap["Putri"] = pickRandomAvatar()
        userProfileMap["Icha"] = pickRandomAvatar()
        userProfileMap["Wahyu"] = pickRandomAvatar()
        userProfileMap["Naya"] = pickRandomAvatar()
        userProfileMap["Pika"] = pickRandomAvatar()

        stories.addAll(
            listOf(
                Story("Febby", userProfileMap["Febby"]!!),
                Story("Eka", userProfileMap["Eka"]!!),
                Story("Widya", userProfileMap["Widya"]!!),
                Story("Putri", userProfileMap["Putri"]!!),
                Story("Icha", userProfileMap["Icha"]!!),
                Story("Wahyu", userProfileMap["Wahyu"]!!),
                Story("Naya", userProfileMap["Naya"]!!),
                Story("Pika", userProfileMap["Pika"]!!)
            )
        )

        posts.addAll(
            listOf(
                Post("Febby", userProfileMap["Febby"]!!, postImageRes = R.drawable.kucing, caption = "Aku bukan malas, cuma menghargai hak kasur untuk ditempati lebih lama."),
                Post("Eka", userProfileMap["Eka"]!!, postImageRes = R.drawable.anjing, caption = "Aku sih nggak ngambek, cuma butuh waktu... buat mikir kenapa sinyal Wi-Fi bisa lebih cepat move on daripada aku."),
                Post("Widya", userProfileMap["Widya"]!!, postImageRes = R.drawable.pinguin, caption = "Dompetku tuh kayak bawang — setiap dibuka, bikin nangis."),
                Post("Putri", userProfileMap["Putri"]!!, postImageRes = R.drawable.tikus, caption = "Katanya cinta itu buta, tapi kenapa aku masih liat dia sama orang lain?"),
                Post("Icha", userProfileMap["Icha"]!!, postImageRes = R.drawable.sincan, caption = "Dietku lancar, tapi sayang... ada nasi padang lewat."),
                Post("Wahyu", userProfileMap["Wahyu"]!!, postImageRes = R.drawable.sapi, caption = "Aku tuh suka banget menunda, bahkan sukses aja aku tunda dulu."),
                Post("Naya", userProfileMap["Naya"]!!, postImageRes = R.drawable.monyet, caption = "Kalau otak butuh istirahat, berarti aku udah overwork sejak SD."),
            )
        )

        // setup RecyclerView
        storyAdapter = StoryAdapter(stories)
        feedAdapter = FeedAdapter(posts)

        binding.rvStories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStories.adapter = storyAdapter

        binding.rvFeed.layoutManager = LinearLayoutManager(this)
        binding.rvFeed.adapter = feedAdapter

        // tombol tambah postingan
        binding.fabAdd.setOnClickListener {
            showAddPostDialog()
        }
    }

    private fun showAddPostDialog() {
        dialogBinding = DialogAddPostBinding.inflate(layoutInflater)
        tempImageUri = null // reset setiap dialog dibuka
        dialogBinding.ivPreview.setImageDrawable(null) // reset preview

        val dialog = AlertDialog.Builder(this)
            .setTitle("Tambah Postingan")
            .setView(dialogBinding.root)
            .setPositiveButton("Tambah") { d, _ ->
                val username = dialogBinding.etUsername.text.toString().trim()
                val caption = dialogBinding.etCaption.text.toString().trim()

                if (username.isNotEmpty() && caption.isNotEmpty()) {
                    // pakai mapping untuk konsisten
                    val profileImage = userProfileMap.getOrPut(username) { pickRandomAvatar() }

                    val newPost = Post(
                        username,
                        profileImage,
                        postImageRes = R.drawable.ic_launcher_background,
                        postImageUri = tempImageUri,
                        caption
                    )

                    posts.add(0, newPost)
                    feedAdapter.notifyItemInserted(0)

                    // tambahkan ke story kalau belum ada
                    if (stories.none { it.name == username }) {
                        stories.add(Story(username, profileImage))
                        storyAdapter.notifyItemInserted(stories.size - 1)
                    }
                }
                d.dismiss()
            }
            .setNegativeButton("Batal") { d, _ -> d.dismiss() }
            .create()

        dialog.show()

        dialogBinding.btnSelectImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
    }
}

