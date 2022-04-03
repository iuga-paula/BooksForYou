package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.books.Book
import com.android.example.booksforyou.books.BookViewModel
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.databinding.FragmentAddBookDialogBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddBookDialog : Fragment() {
    private lateinit var binding: FragmentAddBookDialogBinding
    private  var currentPhotoPath =  ""
    private val viewModel: BookViewModel by activityViewModels {
       BookViewModel.BookViewModelFactory(
            (activity?.application as BooksApplication).booksDatabase.databaseDao
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAddBookDialogBinding>(
            inflater,
            R.layout.fragment_add_book_dialog, container, false
        )

        binding.datePicker.maxDate = Date().time

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addBookDialog_to_yourBooks)
        }

        binding.saveButton.setOnClickListener {
            addNewBook(it)
        }

        binding.addCover.setOnClickListener {
            takePhoto()
        }

        binding.bookType.setOnCheckedChangeListener { group, checkedId ->
            Log.i("CheckedButton", checkedId.toString())
            if (resources.getResourceEntryName(checkedId) == "finished") {
                binding.finishedText.text = "When have you finished it?"
            } else {
                binding.finishedText.text = "When have you started it?"
            }
        }

        return binding.root
    }

    private fun addNewBook(view: View) {
        //get field and add book
        val author = binding.authorName.text.toString()
        val bookName = binding.bookName.text.toString()
        val bookTypeInt = binding.bookType.checkedRadioButtonId
        val bookTypeId = resources.getResourceEntryName(bookTypeInt)
        val noPages = binding.noPages.text.toString()
        val addBookText = binding.bookCoverText.text.toString()

        val date = binding.datePicker
        var month = date.month.toString()
        if (month.length == 1) {
            month = "0$month"
        }
        var day = date.dayOfMonth.toString()
        if (day.length == 1) {
            day = "0$day"
        }
        val bookDate = "$day/$month/${date.year}"


        var errorMsg = "Please fill the following inputs:\n"
        if (author.isBlank()) {
            errorMsg += "- author name\n"
        }
        if (bookName.isBlank()) {
            errorMsg += "- book name\n"
        }
        if (currentPhotoPath == "" || addBookText != "") {
            errorMsg += "- book cover\n"
        }
        if (noPages.isBlank()) {
            errorMsg += "- number of pages\n"
        }
        else if(noPages.toIntOrNull() == null) {
            errorMsg += "Enter a correct number of pages\n"

        }

        if (errorMsg !==  "Please fill the following inputs:\n") {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
        }
        else {

            val newBook = Book(name = bookName, authorName = author, date = bookDate,
                                photoLink = currentPhotoPath, type = bookTypeId, noPages = noPages)
            //view.findNavController().navigate(R.id.action_addBookDialog_to_yourBooks)

            val bundle = Bundle()
            bundle.putParcelable("newBook", newBook)

            viewModel.addNewBook(bookName, author, noPages, bookTypeId, bookDate, currentPhotoPath)
            view.findNavController().navigate(R.id.action_addBookDialog_to_yourBooks, bundle)
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            activity?.let {
                takePictureIntent.resolveActivity(it.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireActivity(),
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, 1)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setPic()

        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }

    }


    private fun setPic() {
        // Get the dimensions of the View
        val targetW: Int = binding.previewImg.layoutParams.width
        val targetH: Int = binding.previewImg.layoutParams.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inPurgeable = true
        }
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            binding.previewImg.visibility = View.VISIBLE
            binding.bookCoverText.text = ""
            binding.previewImg.setImageBitmap(bitmap)
        }
    }

}