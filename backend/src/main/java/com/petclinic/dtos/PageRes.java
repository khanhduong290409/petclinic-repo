package com.petclinic.dtos;

import java.util.List;

public record PageRes<T> (
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages

) {}

/*
* ProductCreateReq, ProductUpdateReq, LoginReq, RegisterReq…
DTO phần response
ProductRes, UserRes, OrderRes, ProductDetailRes…
DTO chung / generic
PageRes<T>, ApiResponse<T>, ErrorResponse, PaginationMeta…
Tất cả đều được tính là DTO, vì chúng phục vụ mục đích:
“Truyền dữ liệu giữa client ↔ server”.

👉 Không cần chia ra:*/