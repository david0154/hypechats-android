package com.nexuzy.hypechats.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nexuzy.hypechats.data.model.Post
import com.nexuzy.hypechats.ui.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    onProfileClick: (Int) -> Unit,
    onPostClick: (Int) -> Unit
) {
    val posts by viewModel.posts.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        if (isLoading && posts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (posts.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("No posts yet", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(posts) { post ->
                    PostCard(
                        post = post,
                        onProfileClick = { onProfileClick(post.userId) },
                        onLike = { viewModel.likePost(post.postId) },
                        onUnlike = { viewModel.unlikePost(post.postId) },
                        onComment = { onPostClick(post.postId) }
                    )
                }
            }
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    onProfileClick: () -> Unit,
    onLike: () -> Unit,
    onUnlike: () -> Unit,
    onComment: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = post.userProfilePicture,
                    contentDescription = "Profile",
                    modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = post.username, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = post.createdAt, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                }
            }
            
            Text(text = post.postText, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(bottom = 12.dp))
            
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${post.likesCount} Likes", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                Text(text = "${post.commentsCount} Comments", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { if (post.isLiked) onUnlike() else onLike() }, modifier = Modifier.weight(1f).height(36.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = if (post.isLiked) Color.Red else Color.Gray)) {
                    Icon(if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Like", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Like", fontSize = 12.sp)
                }
                Button(onClick = { onComment() }, modifier = Modifier.weight(1f).height(36.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                    Icon(Icons.Default.Message, contentDescription = "Comment", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Comment", fontSize = 12.sp)
                }
                Button(onClick = { }, modifier = Modifier.weight(1f).height(36.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                    Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Share", fontSize = 12.sp)
                }
            }
        }
    }
}
