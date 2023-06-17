package com.example.mainproject;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {
    private List<Post> posts;

    public BoardManager() {
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void updatePost(int postId, String newTitle, String newContent) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                post.setTitle(newTitle);
                post.setContent(newContent);
                break;
            }
        }
    }

    public void deletePost(int postId) {
        for (Post post : posts) {
            if (post.getId() == postId) {
                posts.remove(post);
                break;
            }
        }
    }

    public List<Post> getAllPosts() {
        return posts;
    }
}
