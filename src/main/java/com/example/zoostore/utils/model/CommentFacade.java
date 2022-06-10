package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.CreateCommentDtoRequest;
import com.example.zoostore.model.Comment;

public class CommentFacade {
    public static Comment commentDtoRequestToComment(CreateCommentDtoRequest request) {
        return Comment.builder()
            .star(request.getStar())
            .text(request.getText())
            .build();
    }
}
