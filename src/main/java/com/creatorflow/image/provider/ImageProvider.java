package com.creatorflow.image.provider;

import java.nio.file.Path;

public interface ImageProvider {

    Path generateImage(String prompt) throws Exception;

}