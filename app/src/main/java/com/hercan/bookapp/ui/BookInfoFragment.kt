package com.hercan.bookapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hercan.bookapp.R
import com.hercan.bookapp.databinding.FragmentBookInfoBinding
import com.hercan.bookapp.models.Author
import com.squareup.picasso.Picasso


class BookInfoFragment : Fragment() {
    private var _binding: FragmentBookInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookInfoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BookInfoViewModel::class.java)
        arguments?.let {
            val id = BookInfoFragmentArgs.fromBundle(it).id
            viewModel.getBooksWithId(id)
            viewModel.booksData.observe(viewLifecycleOwner) {
                binding.bookNameTextView.text = it.result.get(0).title.toString()
                binding.authorNameTextView.text = getAuthors(it.result.get(0).authors)
                binding.subjectsTextView.text =
                    "Subjects:" + getSubjects(it.result.get(0).subjects)
                binding.languagesTextView.text =
                    "Languages:" + getLanguage(it.result.get(0).languages)
                binding.authoryearTextView.text= getAuthorsBirthAndDeathYear(it.result.get(0).authors)
                if (it.result.get(0).formats?.imagejpeg != null) {
                    Picasso.get()
                        .load(it.result.get(0).formats?.imagejpeg)
                        .into(binding.bookImageView)
                } else {
                    binding.bookImageView.setImageResource(R.drawable.baseline_auto_stories_24)
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            loadingDataControl(it)
        })
    }

    fun loadingDataControl(boolean: Boolean) {
        if (boolean) {
            binding.progressBar.visibility = View.VISIBLE
            binding.bookinfoLinearLayout.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.bookinfoLinearLayout.visibility = View.VISIBLE
        }
    }
    fun getAuthorsBirthAndDeathYear(list: List<Author?>?): String{
        var string: String=""
        if (list?.size != 0) {
             list?.get(0)?.birthYear.toString().let{
                 string="Birth Year: "+it
             }
            list?.get(0)?.deathYear.toString().let{
                string+="\n"+"Death Year: "+it
            }
        } else {
           string="no authors information"
        }
        return string
    }
    fun getAuthors(list: List<Author?>?): String {
        if (list?.size != 0) {
            return list?.get(0)?.name.toString()
        } else {
            return "\n" +
                    "no author information"
        }
    }

    fun getSubjects(list: List<String?>?): String {
        if (list?.size != 0) {
            if (list != null) {
                var subjects = ""
                for (value in list) {
                    subjects += "\n" + value
                }
                return subjects
            } else {
                return "no subject information"
            }
        }else {
            return "no subject information"
        }
    }
        fun getLanguage(list: List<String?>?): String {
            var languages = ""
            if (list?.size != 0) {
                if (list != null) {
                    for (value in list) {
                        when (value) {
                            "en" -> languages += "\nEnglish"
                            "tr" -> languages += "\nTurkish"
                            "ar" -> languages += "\nArabic"
                            "fr" -> languages += "\nFrench"
                            "pt" -> languages += "\nPortuguese"
                            "es" -> languages += "\nSpanish"
                            "de" -> languages += "\nGerman"
                            "ru" -> languages += "\nRussian"
                            else -> {
                                if (languages.equals("")) {
                                    languages = "no registered language"
                                }
                            }
                        }
                    }
                    return languages
                }else {
                    return "no language information"
                }
            } else {
                return "no language information"
            }
        }
}