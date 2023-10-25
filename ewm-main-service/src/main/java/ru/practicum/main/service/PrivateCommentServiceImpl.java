package ru.practicum.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.dao.CommentMainServiceRepository;
import ru.practicum.main.dao.EventMainServiceRepository;
import ru.practicum.main.dao.UserMainServiceRepository;
import ru.practicum.main.dto.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.model.Comment;
import ru.practicum.main.model.CommentCount;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentMainServiceRepository repository;

    private final UserMainServiceRepository userMainServiceRepository;

    private final EventMainServiceRepository eventMainServiceRepository;

    @Transactional
    @Override
    public Comment createComment(long userId, long eventId, Comment comment) {
        User author = userMainServiceRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("You are not registered"));

        Event event = eventMainServiceRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("You cannot comment on an event that has not yet been published");
        }

        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreateTime(LocalDateTime.now().withNano(0));
        log.info("create new comment");
        return repository.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(long userId, long comId) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Comment with id" + comId + " not found"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("You can't delete this comment because you didn't write it");
        }
        log.info("delete comment");
        repository.deleteById(comId);
    }

    @Transactional
    @Override
    public Comment patchComment(long userId, long comId, Comment newComment) {
        Comment comment = repository.findById(comId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + comId + " not found"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("You can't delete this comment because you didn't write it");
        }

        if (comment.getCreateTime().isBefore(LocalDateTime.now().minusHours(1))) {
            throw new ConflictException("Time to comment is out");
        }

        comment.setText(newComment.getText());
        comment.setPatchTime(LocalDateTime.now().withNano(0));
        log.info("patch comment");
        return comment;
    }


    @Override
    public Map<Long, Long> getCommentCount(Collection<Event> list) {
        List<Long> listEventId = list.stream().map(a -> a.getId()).collect(Collectors.toList());
        List<CommentCount> countList = repository.findAllCommentCount(listEventId);
        Map<Long, Long> map = countList.stream().collect(Collectors.toMap(CommentCount::getEventId, CommentCount::getCommentCount));
        return map;
    }
}
