package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.StatServiceRepository;
import ru.practicum.model.Stat;
import ru.practicum.model.StatUniqueOrNot;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatServiceImpl  implements StatService {

    private final StatServiceRepository serviceRepository;

    @Transactional
    @Override
    public Stat postStat(Stat stat) {
        log.info("create new stat");
        return serviceRepository.save(stat);
    }


    @Override
    public List<StatUniqueOrNot> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
            List<StatUniqueOrNot> list;
        if (uris.isEmpty()) {
            if (unique) {
                list = serviceRepository.findAllByUriAndIp(start, end);
                log.info("get unique list without uris");
            } else {
                list = serviceRepository.findAllByTimestampBetween(start, end);
                log.info("get not unique list without uris");
            }
        } else {
            if (unique) {
                list = serviceRepository.findAllByUriAndIpAndUris(start, end, uris);
                log.info("get unique list with uris");
            } else {
                list = serviceRepository.findAllByUriAndIpAndUrisNotUnique(start, end, uris);
                log.info("get not unique list with uris");
            }
        }
        return list;
    }
}
