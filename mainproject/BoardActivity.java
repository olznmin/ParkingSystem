package com.example.mainproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private LinearLayout postListLayout;
    private BoardManager boardManager;
    private Map<Integer, PostView> postViewMap;

    private PostView currentPostView;
    private Button backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        addButton = findViewById(R.id.addButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        postListLayout = findViewById(R.id.postListLayout);
        boardManager = new BoardManager();
        postViewMap = new HashMap<>();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPostView != null) {
                    currentPostView.hideContent();
                    currentPostView = null;
                    backButton.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(BoardActivity.this, MainActivity2.class);
                    startActivity(intent);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                int id = generatePostId();
                Post post = new Post(id, title, content);
                boardManager.addPost(post);
                Toast.makeText(BoardActivity.this, "게시글이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                clearEditTextFields();
                refreshPostList();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPostView != null) {
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();
                    currentPostView.updatePostContent(title, content);
                    Toast.makeText(BoardActivity.this, "게시글이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    clearEditTextFields();
                    refreshPostList();
                } else {
                    Toast.makeText(BoardActivity.this, "수정할 게시글을 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPostView != null) {
                    boardManager.deletePost(currentPostView.getPost().getId());
                    Toast.makeText(BoardActivity.this, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    clearEditTextFields();
                    refreshPostList();
                    currentPostView = null;
                    backButton.setVisibility(View.VISIBLE);  // 수정된 부분
                } else {
                    Toast.makeText(BoardActivity.this, "삭제할 게시글을 선택하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshPostList();
    }

    private int generatePostId() {
        return (int) System.currentTimeMillis();
    }

    private void clearEditTextFields() {
        titleEditText.setText("");
        contentEditText.setText("");
    }

    private void refreshPostList() {
        postListLayout.removeAllViews();
        postViewMap.clear();

        List<Post> posts = boardManager.getAllPosts();
        for (Post post : posts) {
            PostView postView = new PostView(this, post);
            postView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPostContent(postView);
                }
            });
            postListLayout.addView(postView);
            postViewMap.put(post.getId(), postView);
        }
    }

    private void showPostContent(PostView postView) {
        if (currentPostView != null) {
            currentPostView.hideContent();
        }

        postView.showContent();
        currentPostView = postView;
        backButton.setVisibility(View.VISIBLE);

        // 선택한 게시글의 제목과 내용을 EditText에 표시
        Post post = postView.getPost();
        titleEditText.setText(post.getTitle());
        contentEditText.setText(post.getContent());
    }

    private class PostView extends LinearLayout {
        private TextView titleTextView;
        private TextView contentTextView;
        private Button viewButton;

        private Post post;
        private boolean isContentVisible;

        public PostView(Context context, Post post) {
            super(context);
            this.post = post;
            initializeViews(context);
            setPostContent();
            hideContent();
        }

        private void initializeViews(Context context) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.post_item, this, true);

            titleTextView = findViewById(R.id.titleTextView);
            contentTextView = findViewById(R.id.contentTextView);
            viewButton = findViewById(R.id.viewButton);

            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleContent();
                }
            });
        }

        private void setPostContent() {
            titleTextView.setText(post.getTitle());
            contentTextView.setText(post.getContent());
        }

        private void toggleContent() {
            if (isContentVisible) {
                hideContent();
            } else {
                showContent();
            }
        }

        public void showContent() {
            contentTextView.setVisibility(View.VISIBLE);
            viewButton.setText("Hide");
            isContentVisible = true;
        }

        public void hideContent() {
            contentTextView.setVisibility(View.GONE);
            viewButton.setText("View");
            isContentVisible = false;
        }

        public void updatePostContent(String title, String content) {
            post.setTitle(title);
            post.setContent(content);
            setPostContent();
        }

        public Post getPost() {
            return post;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentPostView != null) {
            currentPostView.hideContent();
            currentPostView = null;
            backButton.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
