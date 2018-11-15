package ir.behzadnikbin.oauth2example.dto.ListPageRequestDto;

import org.springframework.data.domain.Sort;

public class ListPageRequestDto {
    public Integer page = 1;
    public Integer pageSize = 10;
    public String order="id";
    public Sort.Direction direction = Sort.Direction.ASC;
}
