package pl.dawidsznajder.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageResponseDTO<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;
}
