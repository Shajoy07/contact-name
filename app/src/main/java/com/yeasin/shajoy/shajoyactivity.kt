// Student Number: 301302740

package com.yeasin.shajoy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class ShajoyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyContactsTheme {
                ContactsScreen()
            }
        }
    }
}

@Composable
fun ContactsScreen(viewModel: ContactsViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contactType by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar(snackbarMessage)
            showSnackbar = false
        }
    }
   
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Radio buttons for contact type
            Row {
                RadioButton(
                    selected = contactType == "Friend",
                    onClick = { contactType = "Friend" }
                )
                Text("Friend")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = contactType == "Family",
                    onClick = { contactType = "Family" }
                )
                Text("Family")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = contactType == "Work",
                    onClick = { contactType = "Work" }
                )
                Text("Work")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val contact = Contact(name, phone, email, contactType)
                    viewModel.addContact(contact)
                    snackbarMessage = "Contact added: $name"
                    showSnackbar = true
                    name = ""
                    phone = ""
                    email = ""
                    contactType = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Contact")
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(viewModel.contacts) { contact ->
                    Text("${contact.name} (${contact.type})")
                    Text(contact.phone)
                    Text(contact.email)
                    Divider()
                }
            }
        }
    }
}