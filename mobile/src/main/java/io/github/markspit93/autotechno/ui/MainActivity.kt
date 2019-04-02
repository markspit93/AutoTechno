package io.github.markspit93.autotechno.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import io.github.markspit93.autotechno.PREF_LISTENER_KEY
import io.github.markspit93.autotechno.R
import it.czerwinski.android.delegates.sharedpreferences.stringSharedPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextWatcher {

    private var listenerKey by stringSharedPreference(PREF_LISTENER_KEY, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textInputListenerKey.editText!!.apply {
            setText(listenerKey)
            addTextChangedListener(this@MainActivity)
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        if (editable != null) {
            listenerKey = editable.toString()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not implemented
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Not implemented
    }
}
