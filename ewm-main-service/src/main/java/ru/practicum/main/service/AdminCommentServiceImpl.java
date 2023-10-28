package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.dao.CommentMainServiceRepository;
import ru.practicum.main.dao.UserMainServiceRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.Comment;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    @Override
    public void adminDeleteComment(long comId) {
        boolean answer = repository.existsById(comId);
        if (!answer) {
            throw new NotFoundException("Comment with id=" + comId + " not found");
        }
        log.info("admin delete comment");
        repository.deleteById(comId);
    }

    @Override
    public List<Comment> adminCommentSearch(String text) {
        List<Comment> list = repository.findAllByText(text);
        log.info("admin get comment search ");
        return list;
    }

    @Override
    public List<Comment> adminUserComment(long userId) {
        if (!userMainServiceRepository.existsById(userId)) {
            throw new NotFoundException("User with id=" + userId + " not found");
        }
        List<Comment> list = repository.findAllByAuthorId(userId);
        log.info("admin get comment by user id ");
        return list;
    }


}
