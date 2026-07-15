package com.creatorflow.image.provider;

import java.nio.file.Path;

public interface ImageProvider {

    byte[] generateImage(String prompt) throws Exception;

}