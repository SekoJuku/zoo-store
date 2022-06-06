package com.example.zoostore.controller;

import com.example.zoostore.dto.request.CreateCommentDtoRequest;
import com.example.zoostore.model.Comment;
import com.example.zoostore.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public List<Comment> getAllCommentsByProductId(@PathVariable Long id) {
        return commentService.getAllCommentsByProductId(id);
    }

    @PostMapping("")
    public Comment addCommentToProductId(@RequestBody CreateCommentDtoRequest request) {
        return commentService.addCommentToProductId(request);
    }
}
