package com.wakwak.techbook5sampleapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wakwak.techbook5sampleapp.R
import com.wakwak.techbook5sampleapp.view.fragments.GitHubUserFragment

class TopPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, GitHubUserFragment.newInstance())
                    .commit()
        }
    }
}
