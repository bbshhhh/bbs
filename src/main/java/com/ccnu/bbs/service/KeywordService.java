package com.ccnu.bbs.service;

import java.util.List;
import java.util.Map;

public interface KeywordService {

    List<String> articleKeywords(String articleId);

    List<Map<String, Object>> userKeywords(String userId);
}
