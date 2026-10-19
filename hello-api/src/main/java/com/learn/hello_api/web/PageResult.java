package com.learn.hello_api.web;

import java.util.List;

public record PageResult<T>(long total, int page, int size, List<T> records) {
}
