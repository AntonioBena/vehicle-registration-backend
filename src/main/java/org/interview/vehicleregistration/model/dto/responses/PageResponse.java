package org.interview.vehicleregistration.model.dto.responses;

import lombok.*;

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
}