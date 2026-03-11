package com.revature.Revplay.service;

import com.revature.Revplay.dto.GlobalSearchDTO;

public interface SearchService {
    GlobalSearchDTO search(String keyword);
}
