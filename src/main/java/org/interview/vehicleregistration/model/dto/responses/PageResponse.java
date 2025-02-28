package org.interview.vehicleregistration.model.dto.responses;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse <T>{
    private T content;
    private int index;
    private int size;
    private Long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;

    @Override
    public String toString() {
        return "PageResponse{" +
                "content=" + content +
                ", index=" + index +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", last=" + last +
                ", first=" + first +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PageResponse<?> that = (PageResponse<?>) o;
        return index == that.index && size == that.size && totalPages == that.totalPages && last == that.last && first == that.first && Objects.equals(content, that.content) && Objects.equals(totalElements, that.totalElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, index, size, totalElements, totalPages, last, first);
    }
}