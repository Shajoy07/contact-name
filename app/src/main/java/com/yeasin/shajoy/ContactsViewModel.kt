package com.yeasin.shajoy

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class ContactsViewModel : ViewModel() {
    private val _contacts = mutableStateListOf<Contact>()
    val contacts: List<Contact> = _contacts

    fun addContact(contact: Contact) {
        _contacts.add(contact)
    }
}

data class Contact(
    val name: String,
    val phone: String,
    val email: String,
    val type: String
)