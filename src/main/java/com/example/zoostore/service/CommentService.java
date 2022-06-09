package com.example.zoostore.service;

import com.example.exception.domain.BadRequestException;
import com.example.zoostore.dto.request.CreateCommentDtoRequest;
import com.example.zoostore.model.Comment;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CommentRepository;
import com.example.zoostore.utils.model.CommentUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;


    public List<Comment> getAllCommentsByProductId(Long id) {
        return commentRepository.getAllByProductId(id);
    }

    public Comment addCommentToProductId(CreateCommentDtoRequest request) {
        Comment comment = CommentUtils.commentDtoRequestToComment(request);
        if (request.getId() == null) {
            throw new BadRequestException(String.format("Product with id: %d is not found!", request.getId()));
        }
        comment.setProduct(productService.getProductById(request.getId()));
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.getById(id);
    }

}
