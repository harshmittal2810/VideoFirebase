package com.harsh.videofirebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.firebase.database.*
import com.harsh.videofirebase.utils.SnapHelperOneByOne
import com.harsh.videofirebase.utils.Upload
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }


    private var mDatabase: DatabaseReference? = null
    private val mUploads = mutableListOf<Upload>()
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        errorText.text = ""
        errorText.isVisible = false

        val linearSnapHelper: LinearSnapHelper = SnapHelperOneByOne()
        linearSnapHelper.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mDatabase = FirebaseDatabase.getInstance().getReference("videos")

        mDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mUploads.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)
                    upload?.let {
                        mUploads.add(upload)
                    }
                }
                if (mUploads.isNotEmpty()) {
                    val mAdapter = MoviesAdapter(mUploads)
                    recyclerView.adapter = mAdapter
                    errorText.isVisible = false
                } else {
                    errorText.isVisible = true
                    errorText.text = "No Videos available"
                }
                progress_circle.visibility = View.INVISIBLE

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), databaseError.message, Toast.LENGTH_SHORT)
                    .show()
                progress_circle.visibility = View.INVISIBLE
            }
        })
    }
}