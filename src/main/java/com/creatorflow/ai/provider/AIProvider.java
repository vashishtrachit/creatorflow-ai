package com.creatorflow.ai.provider;

import com.creatorflow.content.dto.ContentPackageResponse;

public interface AIProvider {

    ContentPackageResponse generateContent(String topic);

}