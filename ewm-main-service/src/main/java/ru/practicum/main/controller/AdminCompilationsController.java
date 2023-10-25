package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.Marker;
import ru.practicum.main.dto.CompilationsDto;
import ru.practicum.main.dto.NewCompilationDto;
import ru.practicum.main.mapper.CompilationMapper;
import ru.practicum.main.model.CompilationShort;
import ru.practicum.main.service.AdminCompilationsService;

@RestController
@Validated
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final AdminCompilationsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationsDto postCompilations(@RequestBody @Validated(Marker.Create.class) NewCompilationDto newCompilationDto) {
        CompilationShort compilations = service.createCompilation(CompilationMapper.toNewCompilation(newCompilationDto));
        return CompilationMapper.toCompilationsDto(compilations);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationsDto patchCompilation(@PathVariable long compId, @RequestBody @Validated(Marker.Update.class) NewCompilationDto newCompilationDto) {
        CompilationShort compilationShort = service.patchCompilation(compId, CompilationMapper.toNewCompilation(newCompilationDto));
        return CompilationMapper.toCompilationsDto(compilationShort);
    }
}
