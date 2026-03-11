package com.revature.Revplay.service;

import com.revature.Revplay.dto.ArtistAnalyticsDTO;

public interface ArtistAnalyticsService {
    ArtistAnalyticsDTO getAnalytics(Long artistId);
}
