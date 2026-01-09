package com.nexuzy.hypechats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nexuzy.hypechats.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: Int? = null,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    isCurrentUser: Boolean = false
) {
    val userProfile by viewModel.userProfile.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    
    LaunchedEffect(userId) {
        if (userId != null) {
            viewModel.loadUserProfile(userId)
        } else if (isCurrentUser) {
            viewModel.loadCurrentUserProfile()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (userProfile != null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()).background(MaterialTheme.colorScheme.background)
            ) {
                AsyncImage(
                    model = userProfile?.coverPicture,
                    contentDescription = "Cover",
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )
                
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Column {
                        AsyncImage(
                            model = userProfile?.profilePicture,
                            contentDescription = "Profile",
                            modifier = Modifier.size(100.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = "${userProfile?.firstName ?: ""} ${userProfile?.lastName ?: ""}".trim(), fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 12.dp))
                        Text(text = "@${userProfile?.username ?: ""}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                    }
                    
                    if (isCurrentUser) {
                        Button(onClick = { }, modifier = Modifier.align(Alignment.TopEnd).height(40.dp), shape = RoundedCornerShape(8.dp)) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Edit Profile")
                        }
                    }
                }
                
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatItem(label = "Posts", value = userProfile?.postsCount.toString())
                    StatItem(label = "Followers", value = userProfile?.followersCount.toString())
                    StatItem(label = "Following", value = userProfile?.followingCount.toString())
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = error ?: "User not found", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), modifier = Modifier.padding(top = 4.dp))
    }
}
